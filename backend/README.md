# 모의 주식 거래 백엔드 API 서버

이 문서는 모의 주식 거래 백엔드 API서버를 유지/보수 하기 위한 개발자를 대상으로 작성 되었습니다.

## 목적

이 문서의 목적은 API 서버를 더 잘 유지/보수 하기 위해 작성 된 가이드 입니다.

## 스펙

- SpringBoot v3.1
- kotlin-jdsl v2.2.1
- h2database v2.2.220
- jjwt v0.11.5
- JDK 17

## 개요

- accounts (계좌) - 입금/출금 등 계좌에 관련된 패키지
- auth (인증) - 로그인/로그아웃 등 인증에 관련된 패키지
- chart (차트) - 일별/주별/월별 차트를 보기 위한 패키지
- init (시작 시 초기화) - 시작 시 단 한번만 실행 하는 패키지
- quote (호가) - 현재 호가창 조회 패키지
- response (응답 포맷) - API 의 전체 응답 포맷 패키지
- schedule (스케줄) - 봇 자동 매매 / 전날의 매치되지 않은 데이터 제거 / 매수 및 매도 연결
- stock (주식) - 주식 거래에 대한 API 패키지
- stocks (종목) - 종목에 대한 API 패키지
- users (유저) - 유저와 관련된 API 패키지
- utils (유틸리티) - 날짜 변환 등 큰 패키지 영역에 속하지 않는 메서드 집합 패키지

## 빌드

- gradlew clean
- gradlew build
- docker build -t backend:latest .

## API 명세

### accounts (계좌)

#### POST /account

- Request

```json
{
  "fromAccountId": "Long",
  "price": "Int",
  "toAccountId": "Long"
}
```

- Response

```json
{
  "status": "OK | ERROR",
  "description": "String",
  "data": "Null"
}
```

### auth (인증)

#### POST /login

- Request

```json
{
  "email": "String",
  "password": "String"
}
```

- Response

```json
{
  "status": "OK | ERROR",
  "description": "String",
  "data": {
    "userId": "Long"
  }
}
```

### chart (차트)

### init (시작 시 초기화)

### quote (호가)

#### GET /quote/{stocksId}

- Request
- Response
```json
{
  "status": "OK | ERROR",
  "description": "String",
  "data": {
    "currentList": [
      {
        "stockPriceId": "Long",
        "price": "Int",
        "stocksId": "Long",
        "stocksName": "String",
        "amount": "Int",
        "type": "String"
      }
    ]
  }
}
```

### response (응답 포맷)

### schedule (스케줄)

### stock (주식)

### stocks (종목)

### users (유저)

#### GET /users/{userId}

- Request
- Response

```json
{
  "status": "OK | ERROR",
  "description": "String",
  "data": {
    "accountPrice": "Int",
    "stocksInfo": [
      {
        "stocksId": "Long",
        "stocksName": "String",
        "financialStatementsContent": "String",
        "stockShares": "Long",
        "createdAt": "String"
      }
    ],
    "totalEarningRate": "Double",
    "email": "String",
    "username": "String"
  }
}
```

#### POST /users

- Request

```json
{
  "email": "String",
  "password": "String",
  "username": "String"
}
```

- Response

```json
{
  "status": "OK | ERROR",
  "description": "String",
  "data": {
    "createdAt": "Date String(yyyy-mm-dd HH:MM:SS.ffffff)",
    "email": "String",
    "username": "String",
    "userId": "Long",
    "accountId": "Long"
  }
}
```

#### DELETE /users/{userId}

- Request
- Response

```json
{
  "status": "OK | ERROR",
  "description": "String",
  "data": "Null"
}
```

#### PUT /users/{userId}

- Request

```json
{
  "username": "String",
  "password": "String",
  "newPassword": "String"
}
```

- Response

```json
{
  "status": "OK | ERROR",
  "description": "String",
  "data": "Null"
}
```

### utils (유틸리티)



