//package kr.co.vibevillage.chatting.service;
//
//import jakarta.annotation.PostConstruct;
//import kr.co.vibevillage.chatting.model.ChatRoom;
//import org.springframework.stereotype.Repository;
//
//import java.util.*;
//
//// import 생략....
//
//@Repository
//public class ChatRoomRepository {
//
//    private Map<String, ChatRoom> chatRoomMap;
//
//    @PostConstruct
//    private void init() {
//        chatRoomMap = new LinkedHashMap<>();
//    }
//
//    public List<ChatRoom> findAllRoom() {
//        // 채팅방 생성순서 최근 순으로 반환
//        List<ChatRoom> chatRooms = new ArrayList<>(chatRoomMap.values());
//        Collections.reverse(chatRooms);
//        return chatRooms;
//    }
//
//    public ChatRoom findRoomById(String id) {
//        return chatRoomMap.get(id);
//    }
//
//    public ChatRoom createChatRoom(String name) {
//        ChatRoom chatRoom = ChatRoom.create(name);
//        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);
//        return chatRoom;
//    }
//}