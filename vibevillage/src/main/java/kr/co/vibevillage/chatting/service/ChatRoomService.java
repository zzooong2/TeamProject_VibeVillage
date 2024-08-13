package kr.co.vibevillage.chatting.service;

import kr.co.vibevillage.chatting.mapper.ChatRoomMapper;
import kr.co.vibevillage.chatting.model.ChatRoom;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChatRoomService {

    private final ChatRoomMapper chatRoomMapper;

    public ChatRoomService(ChatRoomMapper chatRoomMapper) {
        this.chatRoomMapper = chatRoomMapper;
    }

    public Long createOrFindRoom(Long currentUserId, Long boardOwnerId) {
        // 기존 채팅방이 존재하는지 확인
        Long existingRoomId = chatRoomMapper.findRoomByUserIds(currentUserId, boardOwnerId);
        if (existingRoomId != null) {
            return existingRoomId;
        }

        // 새 채팅방 생성
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setName("Chat between " + currentUserId + " and " + boardOwnerId);
        chatRoom.setCreatorId(currentUserId);
        chatRoom.setOtherUserId((boardOwnerId));
        chatRoomMapper.createChatRoom(chatRoom);

        return chatRoom.getRoomId();
    }
    public ChatRoom findRoomById(Long roomId) {
        return chatRoomMapper.findRoomById(roomId);
    }
}
