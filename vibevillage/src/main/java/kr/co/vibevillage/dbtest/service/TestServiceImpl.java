package kr.co.vibevillage.dbtest.service;

import kr.co.vibevillage.dbtest.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.vibevillage.env.Env;

@Service
public class TestServiceImpl implements TestService {

    // env 객체 생성
    private final Env env;

    // 생성자 주입
    @Autowired
    public TestServiceImpl(Env env) {
        this.env = env;
    }

    // maper 객체 생성
    @Autowired
    TestMapper testMapper;

    // TestService 인터페이스에 선언한 추상 메소드 구현
    @Override
    public String testXML() {
        System.out.println("---------------------Database Connect---------------------");

        String text = testMapper.testXML();

        // .env 파일에 있는 변수 호출 테스트
        System.out.println("ORACLE_HOST: " + env.test().get("ORACLE_HOST"));

        // USER 테이블에 있는 U_NICKNAME 데이터 가져와서 Controller로 전달
        return text;
    }
}

