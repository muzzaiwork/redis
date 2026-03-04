# Docker Compose로 Spring Boot와 MySQL 한 번에 띄우기

### ✅ Docker Compose란?
**Docker Compose**는 여러 개의 컨테이너를 하나의 서비스로 정의하고 실행할 수 있게 도와주는 도구이다. `docker-compose.yml` 파일을 통해 애플리케이션 실행에 필요한 모든 서비스(App, DB, Redis 등)를 한 번에 관리할 수 있다.

---

### ✅ 1. Dockerfile 작성 (Spring Boot)
Spring Boot 애플리케이션을 컨테이너화하기 위해 `redis-app` 디렉토리 내에 `Dockerfile`을 생성한다.

**redis-app/Dockerfile**
```dockerfile
# 1단계: 빌드 (JDK 17 사용)
FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /app
COPY . .
RUN ./gradlew build -x test

# 2단계: 실행 (JRE 17 사용)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

### ✅ 2. docker-compose.yml 작성
프로젝트 루트 디렉토리에 Spring Boot 앱과 MySQL을 연동하는 `docker-compose.yml` 파일을 작성한다.

**docker-compose.yml**
```yaml
services:
  app:
    build: ./redis-app  # Dockerfile이 있는 디렉토리 경로
    ports:
      - "8080:8080"
    environment:
      - SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/mydb
      - SPRING_DATASOURCE_USERNAME=root
      - SPRING_DATASOURCE_PASSWORD=password
    depends_on:
      - mysql

  mysql:
    image: mysql:8.0
    container_name: mysql-container
    ports:
      - "3306:3306"
    environment:
      MYSQL_ROOT_PASSWORD: password
      MYSQL_DATABASE: mydb
    volumes:
      - ./mysql_data:/var/lib/mysql
    restart: always
```

- **`depends_on`**: `mysql` 서비스가 먼저 실행된 후 `app` 서비스가 실행되도록 설정한다.
- **`environment`**: Docker 네트워크 내부에서 DB 호스트명은 서비스 이름인 `mysql`이 된다.

---

### ✅ 3. 실행 및 확인

**1. 전체 서비스 실행 (백그라운드)**
```bash
docker-compose up -d --build
```
> `--build` 옵션을 추가하면 Dockerfile의 변경사항을 반영하여 이미지를 새로 빌드한다.

**2. 실행 상태 확인**
```bash
docker-compose ps
```

**3. 로그 확인**
```bash
docker-compose logs -f app
```

---

### ✅ 💡 팁: 네트워크 통신
Docker Compose 내에서 컨테이너끼리 통신할 때는 **서비스 이름**을 호스트명으로 사용한다. 
예를 들어, Spring Boot 앱(`app`)에서 MySQL에 접속할 때 `localhost`가 아닌 `mysql`을 사용하는 이유가 바로 이 때문이다.
