package kr.co.vibevillage.chatting.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ChatRoom {
    private Long roomId;    // DB에서 NUMBER로 저장됨
    private String name;    // 채팅방 이름
    private Long creatorId; // 채팅방 생성자 ID
    private Long otherUserId;
    private String createrNickName;
    private String otherUserNickName;
}