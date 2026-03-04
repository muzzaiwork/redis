# 기본적인 Spring Boot 프로젝트 셋팅하기

### ✅ 기본적인 Spring Boot 프로젝트 셋팅하기

> 동일한 환경에서 실습을 진행하기 위해 아래 버전을 사용할 것을 권장한다.
- Spring Boot는 3.x.x 버전
- MySQL 8.x 버전
- JDK 17

1. Spring Boot 프로젝트 셋팅
[start.spring.io](https://start.spring.io/)에서 아래와 같이 설정하고 프로젝트를 생성한다.

- **Project**: Gradle - Groovy (또는 Gradle - Kotlin)
- **Language**: Java
- **Spring Boot**: 3.2.x (또는 최신 안정 버전)
- **Java**: 17
- **Dependencies**:
    - `Spring Boot DevTools`
    - `Spring Web`
    - `Spring Data JPA`
    - `MySQL Driver`
    - `Lombok` (편의를 위해 추가 권장)

#### 2. 불필요한 테스트 코드 삭제
학습에 집중하기 위해 프로젝트 생성 시 기본으로 포함된 테스트 코드는 삭제하거나 주석 처리한다.

#### 3. application.yml 설정
`src/main/resources/application.properties` 파일을 지우고 `application.yml` 파일을 생성하여 DB 연결 정보를 작성한다.

**application.yml**
```yaml
# local 환경
spring:
  profiles:
    default: local
  datasource:
    url: jdbc:mysql://localhost:3306/mydb
    username: root
    password: password
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

#### 4. Board 엔티티 만들기
데이터를 담을 간단한 게시판(`Board`) 엔티티를 작성한다.

**Board.java**
```java
@Entity
@Table(name = "boards")
public class Board {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  private String content;

  @CreatedDate
  private LocalDateTime createdAt;

  // Getters
  public Long getId() { return id; }
  public String getTitle() { return title; }
  public String getContent() { return content; }
  public LocalDateTime getCreatedAt() { return createdAt; }
}
```

#### 5. Controller, Service, Repository 구성
기본적인 조회 기능을 위한 레이어별 코드를 작성한다.

**BoardRepository.java**
```java
public interface BoardRepository extends JpaRepository<Board, Long> {
  Page<Board> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
```

**BoardService.java**
```java
@Service
public class BoardService {
  private final BoardRepository boardRepository;

  public BoardService(BoardRepository boardRepository) {
    this.boardRepository = boardRepository;
  }

  public List<Board> getBoards(int page, int size) {
    Pageable pageable = PageRequest.of(page - 1, size);
    Page<Board> pageOfBoards = boardRepository.findAllByOrderByCreatedAtDesc(pageable);
    return pageOfBoards.getContent();
  }
}
```

**BoardController.java**
```java
@RestController
@RequestMapping("boards")
public class BoardController {
  private final BoardService boardService;

  public BoardController(BoardService boardService) {
    this.boardService = boardService;
  }

  @GetMapping
  public List<Board> getBoards(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int size
  ) {
    return boardService.getBoards(page, size);
  }
}
```

---

### ✅ 더미 데이터 넣기
대용량 트래픽 처리를 테스트하기 위해 MySQL에서 아래 SQL문을 실행하여 약 100만 건의 더미 데이터를 삽입한다.

```sql
-- 높은 재귀(반복) 횟수를 허용하도록 설정
SET SESSION cte_max_recursion_depth = 1000000; 

-- boards 테이블에 더미 데이터 삽입
INSERT INTO boards (title, content, created_at)
WITH RECURSIVE cte (n) AS
(
  SELECT 1
  UNION ALL
  SELECT n + 1 FROM cte WHERE n < 1000000
)
SELECT
    CONCAT('Title', LPAD(n, 7, '0')) AS title,
    CONCAT('Content', LPAD(n, 7, '0')) AS content,
    TIMESTAMP(DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 3650 + 1) DAY) + INTERVAL FLOOR(RAND() * 86400) SECOND) AS created_at
FROM cte;
```
