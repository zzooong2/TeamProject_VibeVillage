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
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/chat")
public class ChatController {

        public final SimpMessageSendingOperations sendingOperations;
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;

    public ChatController(SimpMessageSendingOperations sendingOperations, ChatRoomService chatRoomService, ChatMessageService chatMessageService) {
        this.sendingOperations = sendingOperations;
        this.chatRoomService = chatRoomService;
        this.chatMessageService = chatMessageService;
    }
    // 채팅방 생성
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


    // 채팅방 입장
    @GetMapping("/room/{roomId}")
    public String getChatRoomPage(@PathVariable(value = "roomId") Long roomId, Model model) {
        ChatRoom chatRoom = chatRoomService.findRoomById(roomId);
        if (chatRoom == null) {
            return "redirect:/error"; // 채팅방이 없을 경우 에러 페이지로 리다이렉트
        }
        List<ChatMessage> chatMessage = chatMessageService.findMessagesByRoomId(roomId);
        System.out.println(chatRoom.getRoomId());
        model.addAttribute("roomId", roomId);
        model.addAttribute("chatRoom", chatRoom);
        model.addAttribute("chatMessage", chatMessage);
        return "chat/chatRoom"; // 채팅방 페이지로 이동
    }
    
    // 메세지 송신
    @MessageMapping("/chat.sendMessage/{roomId}")
    @SendTo("/sub/{roomId}")
    public ChatMessage sendMessage(@DestinationVariable String roomId, @Payload ChatMessage chatMessage) {
        System.out.println(roomId);
        System.out.println(chatMessage.getMessage());
        chatMessageService.saveMessage(chatMessage);
        return chatMessage;
    }
    
//    // 송신자 전송
//    @MessageMapping("/chat.addUser/{roomId}")
//    @SendTo("/sub/{roomId}")
//    public ChatMessage addUser(@DestinationVariable String roomId, ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
//        System.out.println(chatMessage);
//        headerAccessor.getSessionAttributes().put("username", chatMessage.getSenderId());
//        return chatMessage;
//    }
}




