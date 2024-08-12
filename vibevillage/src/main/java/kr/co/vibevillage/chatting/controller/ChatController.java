package kr.co.vibevillage.chatting.controller;

import kr.co.vibevillage.chatting.model.ChatMessage;
import kr.co.vibevillage.chatting.model.ChatRoom;
import kr.co.vibevillage.chatting.service.ChatMessageService;
import kr.co.vibevillage.chatting.service.ChatRoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/chat")
public class ChatController {

    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;

    public ChatController(ChatRoomService chatRoomService, ChatMessageService chatMessageService) {
        this.chatRoomService = chatRoomService;
        this.chatMessageService = chatMessageService;
    }

    @PostMapping("/start")
    public ResponseEntity<Map<String, Long>> startChat(@RequestBody Map<String, Long> request) {
        Long currentUserId = request.get("currentUserId");
        Long boardOwnerId = request.get("boardOwnerId");
        System.out.println(boardOwnerId);
        Long roomId = chatRoomService.createOrFindRoom(currentUserId, boardOwnerId);

        Map<String, Long> response = new HashMap<>();
        response.put("roomId", roomId);
        return ResponseEntity.ok(response);
    }



    @GetMapping("/room/{roomId}")
    public String getChatRoomPage(@PathVariable Long roomId, Model model) {
        ChatRoom chatRoom = chatRoomService.findRoomById(roomId);
        if (chatRoom == null) {
            return "redirect:/error"; // 채팅방이 없을 경우 에러 페이지로 리다이렉트
        }
        model.addAttribute("roomId", roomId);
        model.addAttribute("chatRoom", chatRoom);
        return "chat/chatRoom"; // 채팅방 페이지로 이동
    }
    @MessageMapping("/chat.sendMessage/{roomId}")
    @SendTo("/topic/{roomId}")
    public ChatMessage sendMessage(@DestinationVariable String roomId, ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser/{roomId}")
    @SendTo("/topic/{roomId}")
    public ChatMessage addUser(@DestinationVariable String roomId, ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSenderId());
        return chatMessage;
    }
}




