# 🗓️ Spring Scheduler - 일정 관리 시스템

## 1. 프로젝트 개요

이 프로젝트는 Java와 Spring Boot 기반으로 개발된 **일정 관리 REST API 서비스**입니다.  
사용자는 일정을 등록/수정/삭제할 수 있으며, **작성자 정보 관리, 유효성 검증, 예외 처리, 페이징 처리** 등 다양한 기능을 제공합니다.  
또한, **3 Layer Architecture**와 **DTO 패턴**, **Enum 기반 에러 코드 관리**, **Bean Validation**, **글로벌 예외 처리**를 통해  
**확장성과 유지보수성**을 갖춘 구조로 설계되었습니다.

---

## 2. 주요 기능

- ✅ 일정 등록, 조회(단건/전체), 수정, 삭제
- ✅ 일정 작성자 정보 분리 (작성자 테이블과 외래키 관계 설정)
- ✅ 일정 수정 이력 저장 (History 테이블)
- ✅ 페이징 처리 (`/schedules/paged`)
- ✅ 비밀번호 기반 수정/삭제
- ✅ 유효성 검사 및 예외 처리
- ✅ 에러 코드 통일화 (enum 기반)
- ✅ RESTful API 명세 제공

---

## 3. 📁 프로젝트 구조

```plaintext
📦 src
 ┣ 📂 img                         # ERD 이미지 등 정적 리소스
 ┣ 📂 main
 ┃ ┣ 📂 java
 ┃ ┃ ┗ 📂 org.example
 ┃ ┃   ┣ 📂 config              # 설정 클래스 (예: CORS, JDBC 등)
 ┃ ┃   ┣ 📂 controller          # REST API 컨트롤러 계층
 ┃ ┃   ┣ 📂 dto                 # 요청/응답 DTO 클래스
 ┃ ┃   ┣ 📂 entity              # DB 엔티티 및 enum (ErrorCode 포함)
 ┃ ┃   ┣ 📂 exception           # 사용자 정의 예외, 예외 핸들러
 ┃ ┃   ┣ 📂 repository          # 데이터 접근 계층 (JDBC Repository)
 ┃ ┃   ┣ 📂 service             # 비즈니스 로직 처리 계층
 ┃ ┃   ┗ 📜 Main.java           # 애플리케이션 시작점 (SpringBootApplication)
 ┃ ┗ 📂 resources
 ┃   ┣ 📜 application.properties       # 환경설정 (DB 연결 등)
 ┃   ┗ 📜 application.properties-copy  # 백업본
 ┣ 📂 test                     # 테스트 코드 (미작성 or 향후 작성 예정)
┣ 📜 build.gradle              # 빌드 설정
┣ 📜 gradlew / gradlew.bat     # Gradle Wrapper 실행 스크립트
┣ 📜 schedule.sql              # 초기 DB 테이블 생성 SQL
┣ 📜 README.md                 # 프로젝트 설명 파일
┗ 📜 .gitignore                # Git 버전 관리 제외 설정
```

---

## 4. ⚙️ 기술 스택

| 구분 | 기술 |
|------|------|
| Language | Java 17 |
| Framework | Spring Boot 3.x |
| DB | MySQL |
| Build Tool | Gradle |
| Validation | Hibernate Validator |
| ORM | JDBC (Spring Data JDBC) |
| 기타 | Lombok, REST API 기반 설계 |

---

## 5. 실행 방법

### 요구 사항

- Java 17+
- MySQL 실행 중 (`schedule_db` 데이터베이스 생성 필요)
- Gradle 설치

### 실행 단계

1. 세팅
```bash
# DB 세팅
mysql -u root -p < schedule.sql

# 애플리케이션 실행
./gradlew bootRun
```

이 다음 resources폴더를 만들고 application.properties에 copy본을 넣기
```bash
# MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/schedule_db?serverTimezone=Asia/Seoul
spring.datasource.username=root
spring.datasource.password=1q2w3e4r!
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# SQL
logging.level.org.springframework.jdbc.core=DEBUG
```

---

## 6. API 사용 예시
 - Postman 사용을 권장!
### 일정 등록

```http
POST /schedules
Content-Type: application/json
```

#### 요청 바디
```json
{
  "title": "주간 회의",
  "description": "팀 회의입니다.",
  "author": {
    "name": "홍길동",
    "email": "hong@example.com"
  },
  "date": "2025-03-20",
  "time": "10:00:00",
  "password": "secure123"
}
```

### 유효성 검사 실패 응답
```json
{
  "code": "1003",
  "message": "title: 제목은 필수입니다., author.email: 이메일 형식이 올바르지 않습니다.",
  "status": 400
}
```

---

## 7. 유효성 검사

| 필드 | 검증 어노테이션 | 설명 |
|------|------------------|------|
| title | `@NotBlank`, `@Size(max=200)` | 200자 이하 필수 |
| password | `@NotBlank` | 필수 |
| author.name | `@NotBlank` | 필수 |
| author.email | `@Email` | 이메일 형식 |

- Bean Validation 기반
- `@Valid` + `@ExceptionHandler(MethodArgumentNotValidException.class)` 활용

---

## 8. 예외 처리

### GlobalExceptionHandler를 통한 공통 처리

```md
## 내부 에러 코드 설명 표

| 코드 | 상태 코드 | 에러 명칭 | 설명 |
|------|------------|-----------|------|
| `1001` | 404 Not Found | `SCHEDULE_NOT_FOUND` | 조회한 일정이 존재하지 않을 경우 발생 |
| `1002` | 403 Forbidden | `PASSWORD_MISMATCH` | 수정/삭제 시 비밀번호가 일치하지 않을 경우 발생 |
| `1003` | 400 Bad Request | `VALIDATION_ERROR` | 입력값이 유효성 조건을 충족하지 않을 경우 발생 |
| `1999` | 500 Internal Server Error | `INTERNAL_SERVER_ERROR` | 처리되지 않은 예외 또는 서버 내부 오류 발생 |
```

```md
> ❗️ `code`는 시스템 내부에서 에러의 종류를 구분하기 위한 고유 번호입니다.  
> ❗️ 프론트엔드 또는 테스트 코드에서 이 `code` 값을 기준으로 분기처리할 수 있습니다.
```

### 예외 응답 포맷

```json
{
  "code": "1002",
  "message": "비밀번호가 일치하지 않습니다.",
  "status": 403
}
```

---

## 9. 객체지향 설계 특징

| 항목 | 적용 예 |
|------|---------|
| 관심사 분리 | Controller / Service / Repository |
| DTO 패턴 | 요청/응답 분리, 보안 필드 제한 |
| OCP 원칙 | ErrorCode enum을 통해 메시지 통일화 |
| Validation 확장성 | 어노테이션 기반 검증 + 커스텀 예외 처리 가능 |

---

## 10. 트러블슈팅

### ❌ `@Valid` 메시지가 출력되지 않음
- 해결: `GlobalExceptionHandler`에 `MethodArgumentNotValidException` 처리 추가
- `error.getDefaultMessage()` 로 DTO의 메시지 추출 후 `ErrorResponse.setMessage(...)`로 대체

---

## 11. 향후 개선 예정

- Swagger 연동을 통한 API 문서 자동화
- JWT 기반 인증 기능 추가
- 알림 스케줄링 기능 추가 (`schedule_notifications`)
- 프론트 연동을 위한 CORS 설정 및 도메인 분리

---

## 12. 📜 라이선스

이 프로젝트는 **MIT License** 하에 자유롭게 사용 가능합니다.

---

## ✅ 작성 블로그

- [Spring_6기_일정_관리_앱 시리즈](https://velog.io/@hyang_do/series/Spring6%EA%B8%B0%EC%9D%BC%EC%A0%95%EA%B4%80%EB%A6%AC%EC%95%B1)
- [Spring_6기_일정_관리_앱 트러블 슈팅 시리즈](https://velog.io/@hyang_do/series/Spring6%EA%B8%B0%EC%9D%BC%EC%A0%95%EA%B4%80%EB%A6%AC%EC%95%B1%ED%8A%B8%EB%9F%AC%EB%B8%94%EC%8A%88%ED%8C%85)

