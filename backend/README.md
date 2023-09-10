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

### accounts (계좌) - 계좌 출금/입금과 관련된 API 

#### POST /account - 출금

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

#### POST /login - 로그인 

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
#### Post /chart/{:stockId} - 주식 차트 조회
- Request
```json
{
  "from": "String format(yyyy-mm-dd)",
  "to": "String format(yyyy-mm-dd)",
  "unit": "String format(day|month|week)"
}
```
- Response
```json
{
  "status": "OK | ERROR",
  "description": "String",
  "data": [{
    "highPrice": "Int", 
    "lowPrice": "Int", 
    "volume": "Int", 
    "closePrice": "Int",
    "openPrice": "Int",
    "from": "String format(yyyy-mm-dd)",
    "to": "String format(yyyy-mm-dd)"
  },
  {
    "highPrice": "Int", 
    "lowPrice": "Int", 
    "volume": "Int", 
    "closePrice": "Int",
    "openPrice": "Int",
    "from": "String format(yyyy-mm-dd)",
    "to": "String format(yyyy-mm-dd)"
  }]
}
```

### init (시작 시 초기화)

### quote (호가)
#### GET /quote/{stocksId} - 호가창 보기 (특정 종목만)

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

### stock (주식)

#### Get /stock/{:userId}/history?limit=10&offset=0  - 주식 주문 내역 확인
- Request (limit = "가져올 갯수, default=10", offset = "페이지 시작점, default=0")
- Response
```json
{
  "status": "OK | ERROR",
  "description": "String",
  "data": {
    "history": [ 
      {
        "timestamp": "2023-01-01 01:01:01.111111", 
        "price": "Int", 
        "stockAmount": "Int", 
        "stocks": "String" 
      } 
    ]
  }
}
```

#### Post /stock/buy - 매수

- Request

```json
{
  "userId": "String",
  "stockId": "String",
  "stockAmount": "Int",
  "bidPrice": "Int"   
}
```

- Response

```json
{
  "status": "OK | ERROR",
  "description": "String",
  "data": {
    "stockPriceId": "Long"
  }
}
```

#### Post /stock/sell - 매도
- Request
```json
{
  "userId": "String",
  "stockId": "String",
  "stockAmount": "Int",
  "askPrice": "Int" 
}
```
- Response
```json
{
	"status": "OK | ERROR",
    "description": "String",
	"data": {
		"stockPriceId": "Long" 
	}
}
```

#### Put /stock/{:userId}/{:stockPriceId}/buy - 주문 매수 내역 정정

- Request
```json
{
  "stockAmount": "Int",
  "bidPrice": "Int"   
}
```

- Response
```json
{
	"status": "OK | ERROR",
    "description": "String",
	"data": {
		"stockPriceId": "Long"
	}
}
```

#### Put /stock/{:userId}/{:stockPriceId}/sell - 주문 매도 내역 정정
- Request
```json
{
  "stockAmount": "Int",
  "askPrice": "Int"
}
```
- Response
```json
{
  "status": "OK | ERROR",
  "description": "String",
  "data": {
    "stockPriceId": "Long"
  }
}
```
  
#### GET /stock/{userId}/history - 주식 주문 내역 확인
- Request
- Response
```json
{
  "status": "OK | ERROR",
  "description": "String",
  "data": {
    "history": [
      {
        "stockHistoryId": "Long",
        "userId": "Long",
        "timestamp": "String",
        "price": "Int",
        "stocksId": "Long",
        "stockAmount": "Int",
        "historyType": "String"
      }
    ]
  }
}
```


### stocks (종목)
#### Get /stocks/{:stocksId} - 종목 정보 보기
- Request
- Response
```json
{
  "status": "OK | ERROR",
  "description": "String",
  "data": {
    "stocksName": "String",
    "createdAt": "2023-01-01 11:11:11.111111",
    "financialStatementsContent": "String",
    "stockShares": "Long",
  }
}
```

#### Post /stocks - 종목 등록
- Request
```json
{
  "stocksName": "String",
  "financialStatementsContent": "String"
}
```
- Response
```json
{
  "status": "OK | ERROR",
  "description": "String",
  "data": {
    "stocksName": "String",
    "createdAt": "2023-01-01 11:11:11.111111",
    "financialStatementsContent": "String", 
    "stockShares": "Long"
  }
}
```
#### Delete /stocks/{:stocksId} - 종목 등록 취소
- Request
- Response
```json
{
  "status": "OK | ERROR",
  "description": "String"
}
```
#### Put /stocks/{:stocksId} - 종목 정보 업데이트
- Request
```json
{
  "stocksName": "String",
  "financialStatementsContent": "String"
}
```
- Response
```json
{
  "status": "OK | ERROR",
  "description": "String",
  "data": {
    "stocksName": "String",
    "createdAt": "2023-01-01 11:11:11.111111",
    "financialStatementsContent": "String",
    "stockShares": "Long"
  }
}
```

### users (유저)

#### GET /users/{userId} - 유저 조회

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

#### POST /users - 유저 생성

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

#### DELETE /users/{userId} - 유저 삭제

- Request
- Response

```json
{
  "status": "OK | ERROR",
  "description": "String",
  "data": "Null"
}
```

#### PUT /users/{userId} - 유저 정보 수정

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



