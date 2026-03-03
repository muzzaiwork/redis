# Redis 기본 명령어 정복

Redis를 효율적으로 학습하기 위해 모든 명령어를 다 외우기보다는 실무에서 가장 빈번하게 사용되는 핵심 명령어 7가지를 먼저 익히는 것이 중요하다. 특히 조회 성능 개선을 위한 캐싱 전략에서는 아래의 명령어들만 알아도 충분하다.

### ✅ 데이터(Key, Value) 저장하기

가장 기본적인 데이터 저장 명령어다.

```bash
# set [key 이름] [value]
$ set jaeseong:name "jaeseong park" # 공백이 포함된 경우 쌍따옴표로 묶어준다.
$ set jaeseong:hobby soccer
```

### ✅ 데이터 조회하기 (Key로 Value 값 조회하기)

저장된 Key를 사용하여 Value를 가져온다.

```bash
# get [key 이름]
$ get jaeseong:name
$ get jaeseong:hobby

$ get pjs:name # 존재하지 않는 데이터를 조회할 경우 (nil)이 반환된다.
```

### ✅ 저장된 모든 key 조회하기

현재 데이터베이스에 저장된 모든 Key 목록을 확인한다. (운영 환경에서는 성능 저하를 일으킬 수 있으므로 주의해서 사용해야 한다.)

```bash
$ keys *
```

### ✅ 데이터 삭제하기 (Key로 데이터 삭제하기)

특정 Key와 그에 해당하는 데이터를 삭제한다.

```bash
# del [key 이름]
$ del jaeseong:hobby

$ get jaeseong:hobby # 삭제 여부 확인 시 (nil)이 반환된다.
```

### ✅ 데이터 저장 시 만료시간(TTL) 설정하기

Redis는 인메모리 데이터베이스로서 메모리 공간이 한정적이다. 따라서 모든 데이터를 영구적으로 저장하기보다는 만료시간(TTL, Time To Live)을 설정하여 효율적으로 관리한다.

```bash
# set [key 이름] [value] ex [만료 시간(초)]
$ set jaeseong:pet dog ex 30
```

### ✅ 만료시간(TTL) 확인하기

설정된 만료시간이 얼마나 남았는지 확인한다.

```bash
# ttl [key 이름]
$ ttl jaeseong:pet 

# 결과값 의미:
# - n: 남은 시간(초)
# - -2: Key가 존재하지 않음
# - -1: Key는 존재하지만 만료시간이 설정되어 있지 않음
```

### ✅ 모든 데이터 삭제하기

현재 연결된 데이터베이스의 모든 데이터를 삭제한다.

```bash
$ flushall
```
