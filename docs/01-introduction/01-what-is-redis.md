# Redis란? / Redis의 장점

### ✅ Redis란?

Redis의 의미를 인터넷에 검색해보면 아래와 같이 나온다.

> 레디스(Redis)는 Remote Dictionary Server의 약자로서, “키-값” 구조의 비정형 데이터를 저장하고 관리하기 위한 오픈 소스 기반의 비관계형 데이터베이스 관리 시스템(DBMS)이다. 
- 위키백과 -
> 

너무 어렵게 적혀있다. First Word 법칙에 따라 쉽게 바꿔서 이해해본다. 

> **Redis는 데이터 처리 속도가 엄청 빠른 NoSQL 데이터베이스이다.**
> 

이렇게 기억하고 있어도 충분하다. NoSQL 데이터베이스를 풀어서 얘기하자면 Key-Value의 형태로 저장하는 데이터베이스라고 생각하면 된다.

Redis는 단순히 속도만 빠른 것이 아니라, 다음과 같은 특징을 가진다.

1.  **다양한 데이터 구조 지원**: String뿐만 아니라 List, Set, Hash, Sorted Set(ZSet), Bitmaps 등 다양한 자료구조를 지원한다. 이는 개발자가 서비스 요구사항에 맞는 최적의 방식으로 데이터를 저장하고 관리할 수 있게 한다.
2.  **영속성(Persistence) 제공**: 인메모리 데이터베이스이지만 데이터를 디스크에 저장할 수 있는 기능(RDB, AOF)을 제공한다. 덕분에 서버가 재시작되어도 데이터를 복구할 수 있다.
3.  **싱글 스레드 기반의 단순함**: 한 번에 하나의 명령만 처리하므로 Race Condition(경쟁 상태)을 피하기 쉽고, 원자적(Atomic) 연산을 보장한다.

#### Key-Value 구조 (예시)

```mermaid
graph LR
    subgraph "Redis (Memory)"
        K1[Key: user:1:name] --> V1[Value: "jaeseong"]
        K2[Key: user:1:age] --> V2[Value: 30]
        K3[Key: user:1:hobby] --> V3[Value: "soccer"]
    end
```

출처 : https://redis.io/nosql/key-value-databases/

### ✅ Redis의 장점

Redis는 다양한 장점을 가지고 있다. 여러 특징 중 딱 1가지만 확실하게 기억한다.

> **레디스(Redis)는 인메모리(in-memory)에 모든 데이터를 저장한다. 
그래서 데이터의 처리 성능이 굉장히 빠르다.**
> 

MySQL과 같은 RDBMS는 대부분 디스크(Disk)에 데이터를 저장한다. 하지만 Redis는 메모리(RAM)에 데이터를 저장한다. 디스크보다 메모리에서의 데이터 처리속도가 월등하게 빠르기 때문에, Redis는 RDBMS에 비해 훨씬 빠른 성능을 보여준다.

그 외에도 Redis는 다음과 같은 강력한 장점을 가진다.

-   **읽기/쓰기 성능의 극대화**: 초당 수십만 건의 작업을 처리할 수 있어 대용량 트래픽이 발생하는 서비스의 병목 현상을 해결하는 데 최적이다.
-   **간편한 확장성**: Redis Cluster 등을 통해 수평적 확장이 용이하며, 고가용성(HA)을 위한 복제(Replication) 및 자동 페일오버(Sentinel) 기능을 지원한다.
-   **풍부한 생태계**: 자바(Jedis, Lettuce), 노드(Node-redis), 파이썬 등 거의 모든 프로그래밍 언어에서 클라이언트 라이브러리를 지원하여 연동이 매우 쉽다.
