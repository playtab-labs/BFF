# lineup-service BFF 연동 구현 계획

## Context

lineup-service (페스티벌 라인업/공연자/타임테이블/즐겨찾기) gRPC 서비스를 기존 BFF에 통합한다.
기존 user-service 연동 패턴을 따르되, lineup-service만의 차이점(public RPC, LocalizedText 타입)을 반영한다.

---

## 핵심 설계 결정

### 1. API 노출 방식: GraphQL only
- 기존 패턴: auth = REST, user data = GraphQL
- lineup은 도메인 데이터이므로 GraphQL이 적합
- 클라이언트가 필요한 필드만 선택 가능 (LocalizedText 등)
- REST가 추후 필요하면 Facade를 그대로 재사용 가능

### 2. 인증 헤더: 기존 `GrpcAuthInterceptor` 재사용
- lineup-service도 `x-identity-id` + `x-role` 메타데이터를 수신하도록 수정 예정 (lineup-service 쪽에서 처리)
- BFF에서는 별도 인터셉터 없이 기존 `GrpcAuthInterceptor`를 lineup-service 채널에도 동일하게 적용

### 3. LocalizedText: JSON scalar
- `map<string, string>` → GraphQL `JSON` scalar로 노출
- `graphql-java-extended-scalars` 라이브러리 추가
- 클라이언트에서 locale별 텍스트를 자유롭게 선택 가능

### 4. Config: 별도 `LineupServiceProperties`
- `@ConfigurationProperties(prefix = "clients.lineup-service")`
- 기존 `GrpcProperties` 수정 없이 독립 구성
- `@ConfigurationPropertiesScan`이 이미 활성화되어 있으므로 자동 등록

---

## 파일 변경 목록

### Phase 1: Proto & 빌드

| 작업 | 파일 |
|------|------|
| 생성 | `src/main/proto/lineup_service.proto` |
| 수정 | `build.gradle` — `graphql-java-extended-scalars:22.0` 의존성 추가 |

### Phase 2: gRPC 설정

| 작업 | 파일 |
|------|------|
| 생성 | `config/LineupServiceProperties.java` — host(localhost), port(9091), plaintext(true), deadlineSeconds(5) |
| 수정 | `config/GrpcClientConfig.java` — `lineupServiceChannel` (기존 `GrpcAuthInterceptor` 재사용) + `LineupServiceBlockingStub` 빈 추가 |
| 수정 | `resources/application.yml` — `clients.lineup-service` 블록 추가 |
| 수정 | `.env.example` — LINEUP_SERVICE_HOST/PORT/PLAINTEXT/DEADLINE 추가 |
| 수정 | `.env` — LINEUP_SERVICE_HOST/PORT/PLAINTEXT/DEADLINE 추가 |
| 수정 | `test/resources/application.yml` — `clients.lineup-service` 블록 추가 |

> Java 파일 경로 prefix: `src/main/java/com/playtab/bff/`

### Phase 3: gRPC 클라이언트

| 작업 | 파일 |
|------|------|
| 생성 | `grpc/client/LineupGrpcClient.java` — 5개 RPC 래핑, deadline 적용 |

### Phase 4: DTO

| 작업 | 파일 |
|------|------|
| 생성 | `lineup/dto/output/PerformerDto.java` |
| 생성 | `lineup/dto/output/StageDto.java` |
| 생성 | `lineup/dto/output/FestivalDayDto.java` |
| 생성 | `lineup/dto/output/PerformanceScheduleDto.java` |
| 생성 | `lineup/dto/output/PerformanceDurationDto.java` |
| 생성 | `lineup/dto/output/FavoriteDto.java` |

### Phase 5: GraphQL 스키마

| 작업 | 파일 |
|------|------|
| 생성 | `resources/graphql/lineup.graphqls` |
| 생성 | `config/GraphqlScalarConfig.java` — JSON scalar 등록 |

```graphql
scalar JSON

type Performer {
  id: ID!
  name: JSON!
  description: JSON!
  imageUrl: String!
  isActive: Boolean!
  isFavorited: Boolean!
  createdAt: String
  updatedAt: String
}

type Stage {
  id: ID!
  name: JSON!
  locationDesc: JSON!
  displayOrder: Int!
}

type FestivalDay {
  id: ID!
  dayNumber: Int!
  eventDate: String!
}

type PerformanceDuration {
  durationMinutes: Int!
  durationLabel: String!
}

type PerformanceSchedule {
  id: ID!
  performer: Performer!
  stage: Stage!
  festivalDay: FestivalDay!
  startAt: String!
  endAt: String!
  status: ScheduleStatus!
  duration: PerformanceDuration!
}

type Favorite {
  id: ID!
  performerId: ID!
  createdAt: String
}

enum ScheduleStatus {
  SCHEDULED
  CANCELLED
  COMPLETED
}

extend type Query {
  performers(activeOnly: Boolean, stageName: String): [Performer!]!
  schedulesByDay(dayNumber: Int!, stageName: String): [PerformanceSchedule!]!
  myFavorites: [Performer!]!
}

extend type Mutation {
  addFavorite(performerId: ID!): Favorite!
  removeFavorite(performerId: ID!): Boolean!
}
```

### Phase 6: Facade & Controller

| 작업 | 파일 |
|------|------|
| 생성 | `lineup/service/LineupFacade.java` — proto↔DTO 변환, gRPC 호출 오케스트레이션 |
| 생성 | `lineup/graphql/LineupGraphqlController.java` — Query/Mutation 매핑 |

### Phase 7: 인증/보안 업데이트

| 작업 | 파일 |
|------|------|
| 수정 | `security/GraphqlAuthInterceptor.java` — PUBLIC_FIELDS에 `"performers"`, `"schedulesByDay"` 추가 |

### 에러 핸들링
- 변경 불필요. 기존 `GraphqlExceptionHandler`가 `StatusRuntimeException`을 일반적으로 처리
- lineup-service의 커스텀 에러(PERFORMER_NOT_FOUND, FAVORITE_ALREADY_EXISTS 등)는 gRPC status description으로 전달됨

---

## 검증 방법

1. `./gradlew clean build` — 컴파일 및 proto 코드 생성 확인
2. `./gradlew bootRun` — 애플리케이션 기동 확인
3. GraphiQL (`http://localhost:8080/graphiql`)에서:
   - 비로그인: `{ performers { id name imageUrl } }` → 응답 확인 (isFavorited=false)
   - 비로그인: `{ schedulesByDay(dayNumber: 1) { id performer { name } stage { name } startAt endAt } }`
   - 로그인(JWT 헤더 포함): `mutation { addFavorite(performerId: "1") { id performerId } }`
   - 로그인: `{ myFavorites { id name isFavorited } }`
   - 로그인: `mutation { removeFavorite(performerId: "1") }`
