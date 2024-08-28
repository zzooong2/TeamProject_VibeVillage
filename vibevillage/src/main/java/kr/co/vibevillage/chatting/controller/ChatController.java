package kr.co.vibevillage.chatting.controller;

import kr.co.vibevillage.chatting.model.ChatMessage;
import kr.co.vibevillage.chatting.model.ChatRoom;
import kr.co.vibevillage.chatting.service.ChatMessageService;
import kr.co.vibevillage.chatting.service.ChatRoomService;
import kr.co.vibevillage.user.model.dto.UserDTO;
import kr.co.vibevillage.user.model.service.LoginService;
import kr.co.vibevillage.user.model.service.LoginServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatController {

    public final SimpMessageSendingOperations sendingOperations;
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final LoginServiceImpl loginService;

    // 채팅방 생성
    @PostMapping("/start")
    public ResponseEntity<Map<String, Long>> startChat(@RequestBody Map<String, Long> request) {
        UserDTO user = loginService.getLoginUserInfo();
        Long currentUserId = Long.valueOf(user.getUserNo());
        Long boardOwnerId = request.get("boardOwnerId");
        Long roomId = chatRoomService.createOrFindRoom(currentUserId, boardOwnerId);

        Map<String, Long> response = new HashMap<>();
        response.put("roomId", roomId);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/room_list")
    public String getChatRoomList(Model model) {
        UserDTO user = loginService.getLoginUserInfo();
        String currentUser = user.getUserNickName();
        List<ChatRoom> chatRooms =chatRoomService.getChatRoomList(user.getUserNo());
        model.addAttribute("chatRoomList", chatRooms);
        model.addAttribute("currentUser", currentUser);

        return "chat/chatList";
    }

    // 채팅방 입장
    @GetMapping("/room/{roomId}")
    public String getChatRoomPage(@PathVariable(value = "roomId") Long roomId, Model model) {
        ChatRoom chatRoom = chatRoomService.findRoomById(roomId);
        if (chatRoom == null) {
            return "redirect:/error"; // 채팅방이 없을 경우 에러 페이지로 리다이렉트
        }
        List<ChatMessage> chatMessage = chatMessageService.findMessagesByRoomId(roomId);
        UserDTO user = loginService.getLoginUserInfo();
        model.addAttribute("roomId", roomId);
        model.addAttribute("chatRoom", chatRoom);
        model.addAttribute("chatMessage", chatMessage);
        model.addAttribute("currentUserId", user.getUserNo());
        model.addAttribute("currentUserNickName", user.getUserNickName());

        return "chat/chatRoom"; // 채팅방 페이지로 이동
    }
    
    // 메세지 송신
    @MessageMapping("/chat.sendMessage/{roomId}")
    @SendTo("/sub/{roomId}")
    public ChatMessage sendMessage( @Payload ChatMessage chatMessage) {
        System.out.println(chatMessage.getMessage()+"메세지");
        System.out.println(chatMessage.getSenderId()+"센더아이디");
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




