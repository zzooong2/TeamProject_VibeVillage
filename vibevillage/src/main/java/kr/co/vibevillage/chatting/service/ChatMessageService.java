package kr.co.vibevillage.chatting.service;

import kr.co.vibevillage.chatting.mapper.ChatMessageMapper;
import kr.co.vibevillage.chatting.model.ChatMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatMessageService {

    private final ChatMessageMapper chatMessageMapper;

    public ChatMessageService(ChatMessageMapper chatMessageMapper) {
        this.chatMessageMapper = chatMessageMapper;
    }

    public void saveMessage(ChatMessage chatMessage) {
        chatMessage.setTimestamp(LocalDateTime.now());
        chatMessageMapper.saveChatMessage(chatMessage);
    }

    public List<ChatMessage> findMessagesByRoomId(Long roomId) {
        return chatMessageMapper.findMessagesByRoomId(roomId);
    }
}