package kr.co.vibevillage.chatting.mapper;

import kr.co.vibevillage.chatting.model.ChatRoom;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChatRoomMapper {

    Long findRoomByUserIds(@Param("currentUserId") Long currentUserId, @Param("otherUserId") Long otherUserId);

    void createChatRoom(@Param("chatRoom") ChatRoom chatRoom);

    ChatRoom findRoomById(Long roomId);
    List<ChatRoom> getChatRoomList(int userNo);
    String getUserNickNameById(long userNo);

}
