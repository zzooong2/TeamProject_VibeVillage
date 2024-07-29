package kr.co.vibevillage.env;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Env {
    // 일반적인 클래스에서 env 파일에 명시한 변수의 값을 확인하기 위한 클래스
    @Bean
    public Dotenv test() {
        return Dotenv.load();
    }
}
