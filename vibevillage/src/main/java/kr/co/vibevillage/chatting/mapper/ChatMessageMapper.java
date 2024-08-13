package kr.co.vibevillage.chatting.mapper;

import kr.co.vibevillage.chatting.model.ChatMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMessageMapper {

    void saveChatMessage(ChatMessage chatMessage);

    List<ChatMessage> findMessagesByRoomId(Long roomId);
}
