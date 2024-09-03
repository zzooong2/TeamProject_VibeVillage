package kr.co.vibevillage.chatting.service;

import kr.co.vibevillage.chatting.mapper.ChatRoomMapper;
import kr.co.vibevillage.chatting.model.ChatRoom;
import kr.co.vibevillage.user.model.service.LoginServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final ChatRoomMapper chatRoomMapper;
    private LoginServiceImpl loginService;

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
    public List<ChatRoom> getChatRoomList(int userNo){
        List<ChatRoom> chatRoomList = chatRoomMapper.getChatRoomList(userNo);
        for(ChatRoom chatRoom : chatRoomList){
            long otherNo = chatRoom.getOtherUserId(); // 판매자
            long creatorId = chatRoom.getCreatorId(); // 구매자
            System.out.println(otherNo);
            System.out.println(creatorId);
            String otherNickName = chatRoomMapper.getUserNickNameById(otherNo);
            String creatorNickName = chatRoomMapper.getUserNickNameById(creatorId);
            chatRoom.setOtherUserNickName(otherNickName);
            chatRoom.setCreaterNickName(creatorNickName);
        }
        return chatRoomList;
    }

}
