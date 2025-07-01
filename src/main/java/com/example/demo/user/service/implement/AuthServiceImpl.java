package com.example.demo.user.service.implement;

import com.example.demo.common.ResponseDTO;
import com.example.demo.provider.JwtProvider;
import com.example.demo.user.dto.request.FindIdRequestDTO;
import com.example.demo.user.dto.request.LoginRequestDTO;
import com.example.demo.user.dto.response.*;
import com.example.demo.user.dto.request.RegisterRequestDTO;
import com.example.demo.user.entity.RefreshToken;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.RefreshTokenRepository;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.AuthService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;
    private final RedisTemplate<String, String> redisTemplate;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final long REFRESH_TOKEN_EXPIRATION = Duration.ofDays(30).getSeconds();
    @Override
    public ResponseEntity<? super RegisterResponseDTO> register(RegisterRequestDTO registerRequestDTO) {

        try{
            String id = registerRequestDTO.getId();
            boolean existedId = userRepository.existsById(id);
            if(existedId) return RegisterResponseDTO.duplicateID();

            String nickname = registerRequestDTO.getNickname();
            boolean existedNickname = userRepository.existsByNickname(nickname);
            if(existedNickname) return RegisterResponseDTO.duplicateNickname();

            String password = registerRequestDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            registerRequestDTO.setPassword(encodedPassword);

            String name = registerRequestDTO.getName();
            Integer studentId = registerRequestDTO.getStudentID();

            User user = new User(id,encodedPassword,nickname,name,studentId);

            userRepository.save(user);

        }catch (Exception exception){
            exception.printStackTrace();
            return ResponseDTO.databaseError();
        }

        return RegisterResponseDTO.success();
    }

    @Override
    public ResponseEntity<? super LoginResponseDTO> login(LoginRequestDTO loginRequestDTO) {

        String accessToken = null;
        String refreshToken = null;

        try{
            String id = loginRequestDTO.getId();
            Optional<User> opt = userRepository.findById(id);
            if(opt.isEmpty()) return LoginResponseDTO.loginFail();
            User user = opt.get();

            String password = loginRequestDTO.getPassword();
            String encodedPassword = user.getPassword();

            boolean isMatched = passwordEncoder.matches(password, encodedPassword);
            if(!isMatched) return LoginResponseDTO.loginFail();

            accessToken = jwtProvider.createAccessToken(id);
            refreshToken = jwtProvider.createRefreshToken(id);

//            redisTemplate.opsForValue().set(
//                    user.getId(),
//                    refreshToken,
//                    REFRESH_TOKEN_EXPIRATION,
//                    TimeUnit.SECONDS
//            );

            //로그인시에 refresh token 저장
            RefreshToken refreshTokenObj = new RefreshToken(user.getId(), refreshToken);
            refreshTokenRepository.save(refreshTokenObj);
        }catch(Exception exception){
            exception.printStackTrace();
            return ResponseDTO.databaseError();
        }

        return LoginResponseDTO.success(accessToken, refreshToken);
    }

    @Override
    public ResponseEntity<? super FindIdResponseDTO> findId(FindIdRequestDTO findIdRequestDTO){
        String id = null;

        try{

            Integer studentID = findIdRequestDTO.getStudentID();
            User user = userRepository.findByStudentID(studentID);
            if(user == null) return FindIdResponseDTO.findFail();

            String password = findIdRequestDTO.getPassword();
            String encodedPassword = user.getPassword();

            boolean isMatched = passwordEncoder.matches(password,encodedPassword);
            if(!isMatched) return FindIdResponseDTO.findFail();

            id= user.getId();
        }catch(Exception exception){
            exception.printStackTrace();
            return FindIdResponseDTO.databaseError();
        }

        return FindIdResponseDTO.success(id);
    }

    @Override
    public ResponseEntity<? super RefreshTokenResponseDTO> refreshToken(HttpServletRequest request, HttpServletResponse response){

        String refreshToken = null;

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("refreshToken")) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }
        if (refreshToken == null) {
            return RefreshTokenResponseDTO.invalidRefreshToken();
        }

        String userId;
        try{
            userId = jwtProvider.validateRefreshToken(refreshToken);
        } catch (ExpiredJwtException e){
            e.printStackTrace();
            return RefreshTokenResponseDTO.expiredRefreshToken();
        } catch (Exception e){
            e.printStackTrace();
            return RefreshTokenResponseDTO.invalidRefreshToken();
        }

        boolean isExisted = refreshTokenRepository.existsByUserIdAndRefreshToken(userId, refreshToken);
        if(!isExisted) return RefreshTokenResponseDTO.invalidRefreshToken();

        String newAccessToken = jwtProvider.createAccessToken(userId);


         String newRefreshToken = jwtProvider.createRefreshToken(userId);

        //refresh token rotation (Redis)
//        redisTemplate.opsForValue().set(
//                userId,
//                newRefreshToken,
//                REFRESH_TOKEN_EXPIRATION,
//                TimeUnit.SECONDS
//        );

        //refresh token rotation (DB)
         RefreshToken refreshTokenObj = new RefreshToken(userId, newRefreshToken);
         refreshTokenRepository.save(refreshTokenObj);

        return RefreshTokenResponseDTO.success(newAccessToken, newRefreshToken);
    }

    @Override
    @Transactional
    public ResponseEntity<? super LogoutResponseDTO> logout(HttpServletRequest request) {
        String refreshToken = null;

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("refreshToken")) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }
        if (refreshToken == null) {
            return LogoutResponseDTO.invalidRefreshToken();
        }

        String userId;
        try {
            userId = jwtProvider.validateRefreshToken(refreshToken);
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            return LogoutResponseDTO.expiredRefreshToken();
        } catch (Exception e) {
            e.printStackTrace();
            return LogoutResponseDTO.invalidRefreshToken();
        }

        refreshTokenRepository.deleteByUserIdAndRefreshToken(userId, refreshToken);

        return LogoutResponseDTO.success();
    }
}
