## Auth 구현
* jwt를 사용한 이중토큰(access, refresh) 구현.
* 회원가입: POST /api/auth/register
* request: id, password, nickname, name, studentID
* response: code, message
---
* 로그인: POST /api/auth/login
* request: id, password
* response: code, message, accessToken, expirationTime
* access, refresh 토큰 발급. db에 refresh 토큰 저장.
---
* 아이디찾기: POST /api/auth/find-id
* request: password, studentID
* response: code, message, id
---
* access 토큰 재발급: POST /api/auth/refresh-token
* response: code, message, newAccessToken
---
* 로그아웃: DELETE /api/auth/logout
* response: code, message
* 쿠키에서 refresh token 찾고 refresh 토큰으로 user id 추출. 이후 db에서 refresh token 삭제
### 메모
* DTO: 데이터 전송 객체. requestDTO, responseDTO 각각 따로 만듬. requestDTO는 없을 수도.
* Controller: api 요청받음. url 주소. 맨 위 계층. 컨트롤러에서 너무 많은 작업X.
* Service: 실제 로직 구현. db 저장하는 코드 포함.
* Repository: db에 접근. 맨 밑 계층. Jpa 사용.
* access 토큰: 10분 ~ 1시간 비교적 짧은 생명주기. 프론트에서 access 토큰 헤더에 담아서 request 보내야함
* refresh 토큰: access 토큰 만료시 재발급하는 용도. 1달 ~ 1년 긴 생명주기. 만료시 재로그인해야함. refresh 토큰은 HttpOnly로 쿠키에 저장
* 로그인시에 access, refresh 토큰 발급. 프론트에서 access 토큰 관리하고 저장. 
* 로그아웃시에 백엔드에서 쿠키, db에서 refresh 토큰 삭제. access 토큰은 프론트에서 삭제.
* [JwtProvider.java](https://github.com/qhdlehfdl/lossBoard/blob/ab50d410f5a19807c261ab3b5e50b4b317380152/src/main/java/com/example/demo/provider/JwtProvider.java): jwt토큰 발급. 토큰 유효한지 판단.
* [JwtAuthenticationFilter.java](https://github.com/qhdlehfdl/lossBoard/blob/ab50d410f5a19807c261ab3b5e50b4b317380152/src/main/java/com/example/demo/filter/JwtAuthenticationFilter.java): 프론트에서 헤더에 토큰 포함해서 요청-> parseBearerToken으로 토큰 추출. doFilterInternal에서 토큰 추출한걸로 user id 알아냄(@AuthenticationPrincipal로 사용가능). 이후 사용자 인증 절차

---
## 게시물, 댓글 구현
* 게시물 작성: POST /api/board/create
* request: title, content
* response: code, message
* 누가 작성했는지 어떻게 암? -> @AuthenticationPrincipal 통해 user id 가져옴
---
* 게시물 삭제: DELETE /api/board/delete/{boardID}
* response: code, message
* request 따로 없음 @PathVariable 통해 boardID 가져옴 -> db접근
---
* 게시물 수정: PATCH /api/board/modify/{boardID}
* request: title, content
* response: code, message
* 게시물 작성 requestDTO 똑같이 사용
--- 
* 댓글 작성: POST /api/{boardID}/comment/create
* request: content
* response: code, message
---
* 댓글 삭제: DELETE /api/{boardID}/comment/delete/{commentID}
* response: code, message
---
* 댓글 수정: POST /api/{boardID}/comment/modify/{commentID}
* request: content
* response: code, message
* 댓글 작성 requestDTO 사용
### 메모
* user id는 @AuthenticationPrincipal을 통해 가져올 수 있음. -> 필터를 통해 받음
* foreign key를 통해 게시물 삭제하면 그에 관한 댓글도 같이 삭제
---
## 채팅 구현
* 웹소켓+stomp 사용. stomp -> 채팅방별로 주소 할당 가능.
* 일반 http 요청이 있어야 응답가능. 웹소켓은 한번 연결되면 양방향 통신가능. 
* 로그인한 사용자가 채팅방 목록 누르면 subscribe하고 있는 채팅방 목록 db에서 가져와서 보여줌.
* ws 접속 전 토큰 검증 -> 성공 후 세션에 userID 저장. 이후 userID 추출해서 사용.
* subscribe시에 db에서 해당 채팅방에 있는 사용자인지 권한체크
* 채팅방 들어가면 채팅내역불러오고 ws접속 and /topic/chat/roomID(채팅방 메시지), /queue/errors(오류 메시지 받는 채널) subscribe 시작.
---
* 채팅방 만들기: POST/api/chat/board/{boardID}/create
* response: code, message, roomId
* 채팅걸기 누르면 프론트에서 createChatRoom 호출. 이미 채팅방이 있다면 해당 채팅방 id 리턴. 없으면 새로 만들고 채팅방 id 리턴.
---
* 채팅: @MessageMapping("/chat/{roomId}/send")
* request: content
* response: code, message, senderID, content, sendTime
* roomId로 채팅방에 참여하고 있는 사용자마다 subscribe하고 있는 주소 다름.
---
* 채팅내역 불러오기: GET /api/chat/room/{roomID}/history?cursorID=?&size=?
* response: code,message, chatMessageList
* 시간순으로 size개씩 리턴
