package kr.co.vibevillage;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.StandardEnvironment;

import java.util.Map;
import java.util.stream.Collectors;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class VibevillageApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(VibevillageApplication.class);
	}
	public static void main(String[] args) {

		// application.properties 파일에 .env 명시되어있는 변수들을 사용하기 위한 코드
		Map<String, Object> env = Dotenv.load()
				.entries()
				.stream()
				.collect(
						Collectors.toMap(DotenvEntry::getKey, DotenvEntry::getValue));
		new SpringApplicationBuilder(VibevillageApplication.class)
				.environment(new StandardEnvironment() {
					@Override
					protected void customizePropertySources(MutablePropertySources propertySources) {
						super.customizePropertySources(propertySources);
						propertySources.addLast(new MapPropertySource("dotenvProperties", env));
					}
				}).run(args);
	}

}