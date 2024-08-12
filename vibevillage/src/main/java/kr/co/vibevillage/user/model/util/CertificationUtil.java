package kr.co.vibevillage.user.model.util;

import jakarta.annotation.PostConstruct;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CertificationUtil {
    //application.properties에 선언해둔 변수들
    // coolsms apiKey 주입
    @Value("${coolsms.apiKey}")
    private String apiKey;
    // coolsms secretKey 주입
    @Value("${coolsms.secretKey}") 
    private String secretKey;
    // 발신자 번호 주입
    @Value("${coolsms.fromNumber}")
    private String fromNumber;

    // 메시지 서비스를 위한 객체
    DefaultMessageService messageService;

    // 의존성 주입이 완료된 후 초기화를 수행하는 메서드
    @PostConstruct
    public void init(){
        // 메시지 서비스 초기화
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, secretKey, "https://api.coolsms.co.kr");
    }

    // 단일 메시지 발송
    public void sendSMS(String userPhone, String certificationCode){
        // 새 메시지 객체 생성
        Message message = new Message();
        // 발신자 번호 설정
        message.setFrom(fromNumber);
        // 수신자 번호 설정
        message.setTo(userPhone);
        // 메시지 내용 설정
        message.setText("[VibeVillage]본인확인 인증번호는 " + certificationCode + "입니다.");
        // 메시지 발송 요청
        this.messageService.sendOne(new SingleMessageSendingRequest(message));
    }
}
