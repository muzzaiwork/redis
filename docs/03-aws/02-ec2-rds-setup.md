# EC2, RDS, Spring Boot, Redis 셋팅

이 문서는 AWS 환경에서 EC2, RDS를 생성하고, Spring Boot 애플리케이션과 Redis를 설치하여 연동하는 전체 과정을 설명합니다.

---

### ✅ EC2 생성 및 설정

#### 1. 인스턴스 생성 시 고려 사항
- **인스턴스 유형**: `t3a.small` 이상 권장
  - `t2.micro` 프리티어 환경에서는 Spring Boot, Redis, MySQL 등을 동시에 구동하기에 메모리가 부족하여 인스턴스가 멈출 수 있습니다.
- **보안 그룹 (Security Group)**:
  - **8080번 포트**: Spring Boot 애플리케이션 접속을 위해 인바운드 규칙에 추가합니다.
  - **22번 포트**: SSH 접속을 위해 자신의 IP를 허용합니다.

#### 2. EC2에 Redis 설치
EC2 서버에 접속한 후 아래 명령어를 실행하여 Redis를 설치합니다.

```bash
$ sudo apt update
$ sudo apt install redis -y
```

**설치 확인:**
```bash
$ redis-cli

127.0.0.1:6379> ping
PONG
```

#### 3. JDK 17 설치
Spring Boot 3.x.x 버전을 구동하기 위해 JDK 17을 설치합니다.

```bash
$ sudo apt install openjdk-17-jdk -y
```

**설치 확인:**
```bash
$ java -version
```

---

### ✅ RDS 생성 및 설정

#### 1. RDS 생성 시 고려 사항
- **데이터베이스 이름**: 초기 데이터베이스 이름을 `mydb`로 설정합니다.
- **보안 그룹 (Security Group)**:
  - **3306번 포트**: EC2 인스턴스의 보안 그룹 또는 IP로부터의 접속을 허용하도록 인바운드 규칙을 설정합니다.

---

### ✅ Spring Boot 프로젝트 설정 및 배포

#### 1. application.yml 멀티 프로파일 설정
로컬 환경과 운영(AWS) 환경을 구분하기 위해 프로파일 설정을 수정합니다.

**src/main/resources/application.yml**
```yaml
# local 환경 (기본값)
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
  data:
    redis:
      host: localhost
      port: 6379

logging:
  level:
    org.springframework.cache: trace 

---
# prod 환경 (AWS 운영 환경)
spring:
  config:
    activate:
      on-profile: prod
  datasource:
    url: jdbc:mysql://{rds 엔드포인트}:3306/mydb
    username: {rds 계정명}
    password: {rds 비밀번호}
  data:
    redis:
      host: localhost # EC2 내부에 설치된 Redis 사용 시
      port: 6379
```

> **참고**: 실제 프로젝트에서는 보안을 위해 `application.yml`을 `.gitignore`에 등록하거나, 민감한 정보를 환경 변수(Environment Variable)로 관리해야 합니다.

#### 2. EC2에서 프로젝트 빌드 및 실행
EC2 터미널에서 Github 저장소를 클론하고 빌드합니다.

```bash
# 1. Github Clone
$ git clone {Github Repository 주소}
$ cd {프로젝트 경로}

# 2. 빌드 (테스트 제외)
$ ./gradlew clean build -x test 

# 3. JAR 실행 (prod 프로파일 활성화)
$ cd build/libs
$ java -jar -Dspring.profiles.active=prod {빌드된 jar 파일명}.jar
```

---

### ✅ RDS 더미 데이터 삽입

JPA `ddl-auto: update` 설정에 의해 테이블이 생성된 후, 성능 측정을 위해 대량의 데이터를 삽입합니다.

#### 1. 더미 데이터 SQL 실행
MySQL 8.0 이상의 재귀 CTE를 사용하여 100만 건의 데이터를 삽입합니다. DB GUI 툴(DataGrip, DBeaver 등)로 RDS에 접속하여 실행하세요.

```sql
-- 높은 재귀 반복 횟수를 허용하도록 설정
SET SESSION cte_max_recursion_depth = 1000000; 

-- boards 테이블에 100만 건의 더미 데이터 삽입
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
