# BFF (Backend-For-Frontend)

PlayTab 서비스의 BFF 서버입니다.
클라이언트(웹/앱)에게는 **REST**와 **GraphQL** API를 제공하고, 내부 마이크로서비스와는 **gRPC**로 통신합니다.

## 기술 스택

- Java 21
- Spring Boot 3.5
- Spring GraphQL
- gRPC (Netty)
- Gradle

## 프로젝트 구조

```
src/main/java/com/playtab/bff/
├── auth/           # 인증 (REST)
├── user/           # 사용자 (GraphQL)
├── config/         # gRPC 클라이언트 설정
├── grpc/           # gRPC 클라이언트 및 매퍼
├── common/error/   # 에러 핸들링 (REST, GraphQL)
├── security/       # 보안 설정
├── rest/           # 헬스체크 등 공통 REST
└── graphql/        # 공통 GraphQL (health)

src/main/proto/     # Protocol Buffer 정의
src/main/resources/
├── application.yml
└── graphql/        # GraphQL 스키마 (.graphqls)
```

## 실행 방법

### 1. 환경변수 설정

`.env` 파일을 프로젝트 루트에 생성합니다.

```env
SERVER_PORT=8080
USER_SERVICE_HOST=localhost
USER_SERVICE_PORT=9090
USER_SERVICE_PLAINTEXT=true
GRPC_DEADLINE_SECONDS=10
```

> `.env`는 `.gitignore`에 포함되어 있습니다.

### 2. 빌드

```bash
./gradlew build
```

### 3. 실행

```bash
./gradlew bootRun
```

> gRPC로 통신하는 User Service(`localhost:9090`)가 실행 중이어야 정상 동작합니다.

## API

### REST (`/api/v1`)

| Method | Path | 설명 |
|--------|------|------|
| POST | `/users/signup` | 이메일 회원가입 |
| POST | `/auth/login/email` | 이메일 로그인 |
| POST | `/auth/login/social` | 소셜 로그인 |
| POST | `/auth/refresh` | 토큰 갱신 |
| POST | `/auth/logout` | 로그아웃 |
| POST | `/auth/email-verifications/send` | 이메일 인증코드 발송 |
| POST | `/auth/email-verifications/verify` | 이메일 인증코드 확인 |
| GET | `/health` | 헬스체크 |

### GraphQL (`/graphql`)

**Query**

| 이름 | 설명 |
|------|------|
| `me` | 내 프로필 조회 |
| `mySettings` | 내 설정 조회 |
| `myAuthSummary` | 인증 요약 조회 |
| `health` | 헬스체크 |

**Mutation**

| 이름 | 설명 |
|------|------|
| `updateMyProfile` | 프로필 수정 |
| `updateMySettings` | 설정 수정 |
| `updateConsents` | 약관 동의 업데이트 |
| `verifyAdult` | 성인 인증 |

GraphiQL UI: [http://localhost:8080/graphiql](http://localhost:8080/graphiql)

## 테스트

### GraphQL 호출 예시 (cURL)

```bash
curl -X POST http://localhost:8080/graphql \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{"query": "{ me { identityId email name gender } }"}'
```

### REST 호출 예시 (cURL)

```bash
curl -X POST http://localhost:8080/api/v1/auth/login/email \
  -H "Content-Type: application/json" \
  -d '{"email": "user@example.com", "password": "password123"}'
```

## 라이선스

MIT License