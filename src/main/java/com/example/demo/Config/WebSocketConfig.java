package com.example.demo.Config;

import com.example.demo.chat.repository.ChatRoomMemberRepository;
import com.example.demo.interceptor.JwtHandshakeInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.security.access.AccessDeniedException;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtHandshakeInterceptor jwtHandshakeInterceptor;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/ws") //프론트에서 여기로 연결
                .setAllowedOriginPatterns("*")
                .addInterceptors(jwtHandshakeInterceptor); //웹소켓 연결전 토큰 검사
//                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setUserDestinationPrefix("/user");
    }

    //subscribe시에 권한체크
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

                // subscribe일때 권한 체크
                if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
                    String dest = accessor.getDestination();
                    //에러처리 채널은 통과
                    if (dest != null && dest.startsWith("/user/queue/errors")) {
                        return message;
                    }
                    // 일반 채팅방은 권한 체크
                    if (dest != null && dest.startsWith("/topic/chat/")) {
                        Integer roomId = Integer.valueOf(dest.substring(dest.lastIndexOf('/') + 1));
                        String userId = ((Authentication) accessor.getUser()).getName();
                        boolean isMember = chatRoomMemberRepository
                                .existsByChatRoomIdAndUserId(roomId, userId);
                        if (!isMember) {
                            throw new AccessDeniedException("채팅방 접근 권한이 없습니다.");
                        }
                    }
                }

                return message;
            }
        });
    }
}
