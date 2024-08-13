package kr.co.vibevillage.chatting.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatMessage {
        private Long id;            // 메시지의 고유한 ID (DB에서 NUMBER 타입)
        private Long roomId;        // 메시지를 보낸 채팅방 ID (DB에서 NUMBER 타입)
        private Long senderId;      // 메시지를 보낸 사람의 ID (DB에서 NUMBER 타입)
        private String message;     // 메시지 내용 (DB에서 CLOB 타입으로 저장)
        private LocalDateTime timestamp; // 메시지를 보낸 시간
}