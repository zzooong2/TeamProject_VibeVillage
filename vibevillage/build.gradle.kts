plugins {
	java
	war
	id("org.springframework.boot") version "3.2.8"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "kr.co"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}
configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {

	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3")

	// 외부에 노출되면 안되는 중요한 정보들을 .env 파일에 변수로 저장하여 사용하고 .gitignore파일에 .env 파일을 등록하여 Github에 업로드 되지 않도록 설정
	implementation("io.github.cdimascio:dotenv-java:3.0.0")
	implementation("org.springframework.boot:spring-boot-starter-validation:3.2.8") // 유효성검사 의존성 주입
	implementation ("org.springframework.boot:spring-boot-starter-thymeleaf") // Thymeleaf 의존성 주입
	implementation ("org.thymeleaf.extras:thymeleaf-extras-springsecurity6") // Thymeleaf-Spring Security 의존성 주입
	implementation("org.springframework.boot:spring-boot-devtools:3.2.8") // devtools 의존성 주입
	implementation("org.springframework.boot:spring-boot-starter-data-redis") // redis 의존성 주입
	implementation("net.nurigo:sdk:4.3.0") // coolsms 의존성 주입

	// JWT 의존성 추가
	implementation("io.jsonwebtoken:jjwt-api:0.11.5") // Jwt API
	implementation("io.jsonwebtoken:jjwt-impl:0.11.5") // Jwt Implementation
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5") // JWT JSON Parsing

	runtimeOnly("com.oracle.database.jdbc:ojdbc11")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:3.0.3")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// 스프링 부트 리로드 툴
	implementation("org.springframework.boot:spring-boot-devtools:3.2.4")
	// 웹 소캣
	implementation("org.springframework.boot:spring-boot-starter-websocket:3.2.1")
	implementation("org.webjars:stomp-websocket:2.3.4")
	implementation("org.webjars:stomp-websocket:2.3.3")


	// 테스트 코드
	// JUnit 5
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
	testRuntimeOnly ("org.junit.jupiter:junit-jupiter-engine:5.10.0")

	// Mockito
	testImplementation("org.mockito:mockito-core:5.10.0")
	testImplementation("org.mockito:mockito-junit-jupiter:5.10.0")

	// https://mvnrepository.com/artifact/org.mockito/mockito-inline
	testImplementation("org.mockito:mockito-inline:5.2.0")



}

tasks.withType<Test> {
	useJUnitPlatform()
}








