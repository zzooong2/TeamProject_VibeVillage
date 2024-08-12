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
	implementation ("org.springframework.boot:spring-boot-starter-thymeleaf") // Thymeleaf 의존성 주입
	implementation("org.springframework.boot:spring-boot-devtools:3.2.8") // devtools 의존성 주입
	implementation("org.springframework.boot:spring-boot-starter-data-redis") // redis 의존성 주입
	implementation("net.nurigo:sdk:4.3.0") // coolsms 의존성 주입

	runtimeOnly("com.oracle.database.jdbc:ojdbc11")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:3.0.3")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}








