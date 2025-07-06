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

    //웹소켓과 연결하기 전 jwt로 허가된 사용자만 웹소켓 연결. 웹소켓 연결 판단
    //웹소켓 관련 -> ServerHttpRequest 사용
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
