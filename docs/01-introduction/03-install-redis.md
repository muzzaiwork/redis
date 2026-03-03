# 로컬(Windows, MacOS)에서 Redis 설치하기

### ✅ MacOS에서 Redis 설치하기

MacOS 환경에서 Redis를 설치하기 위해 패키지 매니저인 Homebrew를 사용한다.

**1. Redis 설치**

```bash
# Homebrew 설치 여부 확인
$ brew --version

# Redis 설치
$ brew install redis

# Redis 서비스 실행
$ brew services start redis

# Redis 서비스 상태 확인
$ brew services info redis

# Redis 서비스 중지 (필요 시)
$ brew services stop redis
```

**2. Redis 접속 및 확인**

Redis 설치 후 `redis-cli` 도구를 사용하여 접속이 가능한지 확인한다.

```bash
$ redis-cli

# 접속 후 ping 명령을 입력하여 응답 확인
127.0.0.1:6379> ping
PONG
```
`PONG`이라는 응답이 오면 정상적으로 Redis가 실행되고 있는 상태다.

---

### ✅ Windows에서 Redis 설치하기

Windows 환경에서는 공식적으로 Redis를 지원하지 않지만, WSL2(Windows Subsystem for Linux)를 사용하거나 오픈 소스 포트 버전을 설치하여 사용할 수 있다.

**1. WSL2를 통한 설치 (권장)**
- Microsoft에서 권장하는 방식으로, Windows 내 Linux 환경을 구축하여 Redis를 설치한다.
- [Microsoft 공식 가이드](https://learn.microsoft.com/ko-kr/windows/wsl/install)를 참고하여 WSL2를 설치한 후, Ubuntu 등에서 `sudo apt install redis-server` 명령으로 설치한다.

**2. 설치 파일(msi) 이용**
- 직접 설치 파일을 이용하고 싶다면 아래 블로그 링크의 가이드를 참고한다.
- [[Redis] 윈도우10 환경에서 레디스 설치하기](https://ittrue.tistory.com/318)
- [[REDIS] 📚 Window10 환경에 Redis 설치 & 설정](https://inpa.tistory.com/entry/REDIS-%F0%9F%93%9A-Window10-%ED%99%98%EA%B2%BD%EC%97%90-Redis-%EC%84%A4%EC%B1%84%ED%95%98%EA%B8%B0)
