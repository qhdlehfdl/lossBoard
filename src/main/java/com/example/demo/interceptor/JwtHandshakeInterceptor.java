package com.example.demo.interceptor;

import com.example.demo.provider.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
@AllArgsConstructor
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtProvider jwtProvider;

    //ws 접속시에 jwt토큰으로 권한 체크 and 세선에 userId 저장
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws  Exception{

        // 요청이 Servlet 기반인지 확인
        if(request instanceof ServletServerHttpRequest servletRequest){
            HttpServletRequest servlet = servletRequest.getServletRequest();

            String authHeader = servlet.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer")) {
                String token = authHeader.substring(7);
                try {
                    String userId = jwtProvider.validateAccessToken(token);
                    if (userId != null) {
                        System.out.println("검증 성공 "+userId);
                        attributes.put("userId", userId);
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("실패");
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
