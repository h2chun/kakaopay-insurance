# 카카오페이 사전과제3 - 계약관리시스템
## 목차
- [개발 환경](#개발-환경)
- [빌드 및 실행하기](#빌드-및-실행하기)
- [기능 요구사항](#기능-요구사항)
- [개발 제약사항](#개발-제약사항)
- [ENTITY 정보 및 기본 데이터](#ENTUTY-정보-및-기본-데이터)
- [API 명세](#API-명세)

---

## 개발 환경
- Eclipse
- JAVA8
- Spring Boot
- H2 DB
- Lombok
- JPA
- Gradle
- Junit

<br>

## 빌드 및 실행하기
### 설치 및 실행
```
$ git clone https://github.com/kakao-insurance-quiz/20220106-hhc.git
$ cd 20220106-hhc/kakaopayins
$ ./gradlew clean build
$ java -jar build/libs/kakaopayins-0.0.1-SNAPSHOT.jar
```
### 기능 확인
Postman 사용
- URI : `http://localhost:8080`

<br>

## 기능 요구사항
### 필수 요구사항
1. 계약 생성 API
    - 최초 계약 생성시 상태는 정상 계약으로 간주
    - 총 보험료는 계약 생성시전에 서버에서 계산
2. 계약정보 조회 API
    - 계약정보를 전달 받아서 해당 계약의 상세 내용 리턴
3. 계약 정보 수정 API
    - 계약내용 변경 업무
    - 총 보험료는 계약 생성시점에 서버에서 계산
    - 변경 가능 정보
        - 담보 추가/삭제
        - 계약기간 변경(시작일 변경 불가)
        - 계약상태 변경(기간만료 상태 제외)

4. 예상 총 보험료 계산 API
    - 상품/담보 정보와 계약기간을 통한 예상 보험료 리턴

### 선택 요구사항
1. 상품/담보 생성 API
2. 안내장 발송 기능  
  
<br>

## 개발 제약사항
- 계약기간은 월단위로 관리
- 계약자, 피보험자에 대한 사흥은 별도로 관리하지 않음
- 보험료의 수납에 대한 사항은 별도로 관리하지 않음
- 보험료는 소수정 3번째 자리에서 절사 (예. 27.5892 -> 27.58)
- 가입시 가입금액은 담보별로 고정되어 있음.
- 기간만료 상태의 계약은 계약 변경업무 불가.
 
<br>

## ENTITY 정보 및 기본 데이터 
- VARCHAR 타입 => (*V*)  
- INTEGER 타입 => (*I*)  
- TIMESTAMP 타입 => (*T*) 
- DECIMAL 타입 => (*D*)

*테이블 확인* :  `http://localhost:8080/h2-console`
    

### 계약테이블(CONT_MST)
|계약번호(*V*) `PK`|상품정보(*V*)|가입담보(*V*)|계약기간(*I*)|보험시작일(*T*)|보험종료일(*T*)|총보험료(*D*)|계약상태(*V*)|
| --- | --- | --- | --- | --- | --- | --- | --- |
|P2022011100001|PRDT02|P02002|5|2021-05-12|2021-10-11|196250.00|기간만료|
|P2022011100002|PRDT02|P02001,P02002|5|2022-01-10|2022-06-09|294934.20|정상계약|  

<br>

### 상품테이블(PRDT_INFO)
|상품코드(*V*) `PK`|상품명(*V*)|최소계약기간(*I*)|최대계약기간(*I*)|
| --- | --- | --- | --- |
|PRDT01|여행자 보험|1|3|
|PRDT02|휴대폰 보험|1|12|

<br>

### 담보테이블(CVR_INFO)
|담보코드(*V*) `PK`|담보명(*V*)|가입금액(*D*)|기준금액(*D*)|상품코드(*V*) `FK`|
| --- | --- | --- | --- | --- |
|P01001|상해치료비|1000000.00|100.00|PRDT01|
|P01002|항공기 지연도착시 보상금|500000.00|100.00|PRDT01|
|P02001|부분손실|750000.00|38.00|PRDT02|
|P02002|전체손실|1570000.00|40.00|PRDT02|

<br>

## API 명세
### 1. 계약 생성 API
+ Request  
    ```
    PUT /make
    ```
    ```
    예) localhost:8080/make?prdtInfo=PRDT02&insCvr=P02001&insCvr=P02002&contPrd=12
    ```
    
    #Request 파라미터  
    > prdtInfo : 상품코드 -> 상품테이블에 없는 코드는 오류.  
    > insCvr : 담보코드 -> 담보테이블에 없거나 참조하는 상품코드와 다르면 오류.  
    > contPrd : 계약기간 -> 해당 상품에 최소/최대 계약기간을 벗어나면 오류.

+ Response
    ```json
    {
        "contNo": "P2022011200003",
        "prdtInfo": "휴대폰 보험",
        "insCvr": "부분손실,전체손실",
        "contPrd": 12,
        "insStdt": "2022-01-12T15:00:00.000+00:00",
        "insEnddt": "2023-01-12T14:59:59.000+00:00",
        "totInsPrem": 707842.08,
        "contStat": "정상계약"
    }
    ```
    > contNo : 계약번호 : "P" + 계약일자 + 시퀀스  
    > prdtInfo : 상품정보 -> 데이터 확인을 위해 한글 상품명으로 리턴함  
    > insCvr : 보험담보 -> 데이터 확인을 위해 한글 상품명으로 리턴함  
    > contPrd : 계약기간 -> 입력값을 그대로 반환 함.  
    > insStdt : 보험시작일 -> 계약일자 + 1일 00:00:00  
    > insEnddt : 보험종료일 -> 보험시작일 * 계약기간  
    > totInsPrem : 총보험료 -> 담보테이블에 (가입금액/기분금액)에 계약기간을 곱한 값 
    > contStat : 계약상태 -> 계약 저장 시 정상계약으로 셋팅

<br>

### 2. 계약정보 조회 API
+ Request  
    ```
    GET /inq
    ```
    ```
    예) localhost:8080/inq?contInfo=P2022011100001
    ```

    #Request 파라미터  
    > contInfo : 계약정보 -> 계약테이블에 컬럼 중 어떤 값을 입력해도 결과가 조회 된다. 단, PK값이 아닐 경우 다건이 조회 된다.

+ Response
    ```json
    {
        "contNo": "P2022011100001",
        "prdtInfo": "휴대폰 보험",
        "insCvr": "전체손실",
        "contPrd": 5,
        "insStdt": "2021-05-11T15:00:00.000+00:00",
        "insEnddt": "2021-10-11T14:59:59.000+00:00",
        "totInsPrem": 196250.00,
        "contStat": "기간만료"
    }
    ```
    #Response
    > CONT_MST 테이블에 저장된 값이 출력되면 각 항목별 상세 정의는 *계약 생성 API* 와 동일

<br>

### 3. 계약 정보 수정 API
#### 1) 담보 추가/삭제
+ Request  
    ```
    POST /mod/cvr
    ```
    ```
    예) localhost:8080/mod/cvr?contNo=P2022011100002&insCvr=P02001
    ```

    #Request 파라미터  
    > contNo : 계약번호 -> 계약테이블에 PK값.  
    > insCvr : 보험담보 -> 변경할 값. 복수입력 가능, 중복 입력 시 오류, 가입상품과 관련 없는 담보 입력 시 오류

+ Response
    ```json
    {
        "contNo": "P2022011100002",
        "prdtInfo": "휴대폰 보험",
        "insCvr": "부분손실",
        "contPrd": 5,
        "insStdt": "2022-01-09T15:00:00.000+00:00",
        "insEnddt": "2022-06-09T14:59:59.000+00:00",
        "totInsPrem": 98684.20,
        "contStat": "정상계약"
    }
    ```
    #Request
    > **CONT_MST 테이블에 저장된 값이 입력한 담보에 따라 변경 됨.**  
    > insCvr : 계약담보 -> 입력한 담보로 변경.  
    > totInsPrem : 총보험료 -> 담보에 따라 기존에 값이 변경됨.

#### 2) 계약기간 변경(시작일 변경 불가)
+ Request  
    ```
    POST /mod/prd
    ```
    ```
    예) localhost:8080/mod/prd?contNo=P2022011100002&contPrd=10
    ```

    #Request 파라미터  
    > contNo : 계약번호 -> 계약테이블에 PK값.  
    > contPrd : 계약기간 -> 변경할 값. 계약된 상품에 유효 계약기간 범위를 벗어나면 오류.

+ Response
    ```json
    {
        "contNo": "P2022011100002",
        "prdtInfo": "휴대폰 보험",
        "insCvr": "부분손실",
        "contPrd": 10,
        "insStdt": "2022-01-09T15:00:00.000+00:00",
        "insEnddt": "2022-11-09T14:59:59.000+00:00",
        "totInsPrem": 197368.40,
        "contStat": "정상계약"
    }
    ```
    #Request
    > **CONT_MST 테이블에 저장된 값이 입력한 계약기간에 따라 변경 됨.**  
    > contPrd : 계약기간 -> 입력한 기간으로 변경.  
    > insEnddt : 보험종료일자 -> 시작일자* 입력 받은 계약기간으로 새로 계산함.  
    > totInsPrem : 총보험료 -> 입력받은 기간으로 새로 계산함.  

<br>

#### 3) 계약상태 변경(기간만료 상태 제외)
+ Request  
    ```
    POST /mod/stat
    ```
    ```
    예) localhost:8080/mod/stat?contNo=P2022011100002&contStat=청약철회
    ```

    #Request 파라미터  
    > contNo : 계약번호 -> 계약테이블에 PK값.  
    > contStat : 계약상태 -> 변경할 값. 기간만료 일 경우 오류. 해당 계약에 종료일자 지났다면 이 때도 기간만료로 보고 오류.

+ Response
    ```json
    {
        "contNo": "P2022011100002",
        "prdtInfo": "휴대폰 보험",
        "insCvr": "부분손실",
        "contPrd": 10,
        "insStdt": "2022-01-09T15:00:00.000+00:00",
        "insEnddt": "2022-11-09T14:59:59.000+00:00",
        "totInsPrem": 197368.40,
        "contStat": "청약철회"
    }
    ```
    #Request
    > CONT_MST 테이블에 청약상태만 변경됨

<br>

### 4. 예상 총 보험료 계산 API
+ Request  
    ```
    GET /cal  
    ```
    ```
    예) localhost:8080/make?prdtInfo=PRDT02&insCvr=P02001&insCvr=P02002&contPrd=12
    ``` 

    #Request 파라미터  
    > prdtInfo : 상품코드 -> 상품테이블에 없는 코드는 오류.  
    > insCvr : 담보코드 -> 담보테이블에 없거나 참조하는 상품코드와 다르면 오류.  
    > contPrd : 계약기간 -> 해당 상품에 최소/최대 계약기간을 벗어나면 오류.

+ Response
    ```
    45000.00
    ```
    > BigDecimal 타입에 계산된 금액 리턴.

<br>