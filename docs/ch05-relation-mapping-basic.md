> 본 글은 [자바 ORM 표준 JPA 프로그래밍](https://book.naver.com/bookdb/book_detail.nhn?bid=9252528)를 읽고 개인적으로
학습한 내용 복습하기 위해 작성된 글로 내용상 오류가 있을 수 있습니다. 오류가 있다면 지적 부탁 드리겠습니다.

# JPA - 연관관계 매핑 기초

객체의 참조와 테이블의 외래키를 매핑하는 것을 정리해보자.

가장 먼저 연관관계 매핑에서 핵심 키워드를 살펴보면 아래와 같다.

- 방향
  - 단방향(회원 --> 팀 또는 팀 --> 회원)
  - 양방향(회원 <--> 팀)
- 다중성
  - 다대일(회원 대 팀)
  - 일대다(팀 대 회원)
  - 다대다
- 연관관계의 주인 : 객체를 양뱡향 관계로 만들면 연관관계의 주인을 정해야 함

## 1. 단방향 연관관계

회원과 팀의 관계를 통해 단방향 연관관계를 표현하면 아래와 같이 나타낼수 있고 차이점을 아래와 같이 구분할
수 있다.

![many-to-one-one-way](http://www.plantuml.com/plantuml/png/RP31JeD048RlynHZlPH4gju92KQQUC13nNC3ChKReJIxImBH9ZtepQE9Uk0T6kNWCo6-Wy1IR1CdsTrClld_sHd9P4XORlBgewZUr_Zpz5pbnVVNIvM_OVMwAOirrgVyV0HmREVCdk0grlNu0LtRSW1kGay8T2ZoYUCzO1qiQ1-Nl0XbVRLP8eWll8XWjTMqV4-nvPqRwhg9WvFHw7JG5WCS13HZ2qBf-I4DKmsLKug6WKFtw70yjkhGq3JxqYLdaSUpwHrbZS3mpTGuzl_uBER4RXSzJngUc31d0M54SXu7GYL24gTOPaiI0Ee7QY5QDyLkAqMc5-TQYtQxD7lhmVMLFRcq7BiPMtas-3_7FLad-GK0)

- 객체 연관관계
  - 회원 객체(`Member`)는 `team`필드로 팀 객체(`Team`)와 연관관계
  - 회원 객체(`Member`)와 팀 객체(`Team`)는 단반향 관계
  - 회원은 `team`필드를 통해 팀을 알 수 있지만 팀은 회원을 알 수 없음

- 테이블 연관관계
  - 회원 테이블은 `TEAM_ID` 외래키로 팀 테이블과 연관관계
  - 회원 테이블(`MEMBER`)과 팀테이블(`TEAM`)은 양방향 관계
  - 회원 테이블은 `TEAM_ID` 외래키를 통해 회원과 팀, 팀과 회원을 양방향으로 조인할 수 있음

```sql
-- 회원과 팀 SQL JOIN
SELECT * FROM MEMBER M
JOIN TEAM T ON M.TEAM_ID = T.TEAM_ID

-- 팀과 회원 SQL JOIN
SELECT * FROM TEAM T
JOIN MEMBER M ON T.TEAM_ID = M.TEAM_ID
```

객체의 연관관계와 테이블의 연관관계에서 가장 큰 차이점은 방향이다. 객체의 연관관계는 언제나 단방향이다.
객체의 연관관계를 양방향으로 만들고 싶으면 반대쪽에도 필드를 추가해 참조를 보관하면 된다. 하지만 이것을
정확히 말하면 양방향이 아니라 단방향이 2개인 것이다.

```java
// 양방향 연관관계
class A {
  B b;
}

class B {
  A a;
}
```


### 1.1 순수한 객체 연관관계

### 1.2 테이블 연관관계

### 1.3 객체 관계 매핑

### 1.4 @JoinColumn

### 1.5 @ManyToOne

## 2. 연관관계 사용

### 2.1 저장

### 2.2 조회

### 2.3 수정

### 2.4 연관 관계 제거

### 2.5 연관된 엔티티 삭제

## 3. 양방향 연관관계

### 3.1 양방향 연관관계 매핑

### 3.2 일대다 컬렉션 조회

## 4. 연관관계의 주인

## 5. 양방향 연관관계 저장

## 6. 양방향 연관관계 주의사항

## 7. Summery / Conclusion
