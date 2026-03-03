# 🚀 Redis 마스터 가이드: 입문부터 실무까지

이 프로젝트는 Redis의 핵심 개념부터 대용량 트래픽 처리를 위한 실무 적용까지, 스스로 학습하고 기록하는 공간입니다.

---

## 🗺️ 학습 로드맵

### 1️⃣ Redis 기초 체력 다지기
- [x] [Redis란? / Redis의 장점](docs/01-introduction/01-what-is-redis.md)
- [x] [Redis 주요 사용 사례](docs/01-introduction/02-redis-use-cases.md)
- [ ] Redis가 채용 공고에 단골로 등장하는 이유 (대용량 트래픽)
- [ ] 로컬 환경(Windows/MacOS) Redis 설치 가이드

### 2️⃣ Redis 실무 활용 능력 키우기
- [ ] Redis 기본 명령어 정복
- [ ] 실무에서 통하는 Key 네이밍 컨벤션
- [ ] 캐싱(Caching) 전략 완벽 이해 (Cache Aside, Write Around)
- [ ] 캐싱 적용 전 반드시 체크해야 할 필수 사항

### 3️⃣ 백엔드 프레임워크와 Redis 연동
- [ ] **Spring Boot** 환경에서 Redis 셋팅 및 성능 비교
- [ ] **Nest.js** 환경에서 Redis 셋팅 (v11 포함) 및 성능 비교

### 4️⃣ 실전 인프라 구성 및 부하 테스트
- [ ] AWS EC2 + RDS + Spring Boot + Redis 아키텍처 설계
- [ ] k6를 활용한 Redis 적용 전/후 처리량(Throughput) 비교
- [ ] Docker Compose로 Redis & Spring Boot 한 번에 띄우기

### 5️⃣ 클라우드 환경의 Redis 운영 (ElastiCache)
- [ ] 왜 실무에서는 ElastiCache를 쓰는가?
- [ ] ElastiCache 아키텍처 구성 및 Spring Boot 연결
- [ ] 클라우드 리소스 관리 및 비용 최적화

---

## 📚 참고 강의
- [비전공자도 이해할 수 있는 Redis 입문/실전 (조회 성능 최적화편)](https://www.inflearn.com/course/redis-%EC%9E%85%EB%AC%B8-%EC%8B%A4%EC%A0%84-%EC%A1%B0%ED%9A%8C-%EC%84%B1%EB%8A%A5-%EC%B5%9C%EC%A0%81%ED%99%94)
- [강의 목차 및 노션 링크](docs/course-outline.md)
