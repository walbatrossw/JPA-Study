# 고급매핑

## 1. 상속관계 매핑

객체의 상속관계를 데이터베이스에 매핑하는 방법에 대해 알아보자.

관계형 데이터베이스에는 객체지향 언어에서 다루는 상속이라는 개념이 없다. 대신 슈퍼타입 서브타입 관계라는
모델링 기법이 객체의 상속개념과 유사하다. 슈퍼타입 서브타입 논리 모델을 실제 물리 모델인 테이블로 구현할
때는 아래와 같이 3가지 방법을 선택할 수 있다.

- 각각의 테이블로 변환 : 모두 테이블로 만들고 조회할 때 조인(조인 전략)
- 통합 테이블로 변환 : 테이블 하나만 사용해 통합(단일 테이블 전략)
- 서브타입 테이블로 변환 : 서브 타입마다 하나의 테이블을 만듬(테이블 전략)

### 1.1 조인 전략
**엔티티 각각을 모두 테이블로 만들고 자식 테이블이 부모 테이블의 기본키를 받아 기본키 + 외래키로 사용하는 전략이다.**
조회할 때 조인을 자주 사용하며 객체와 달리 테이블은 타입의 개념이 없기 때문에 타입을 구분하는 칼럼을 추가해야 한다.

#### 1.1.1 예제 코드

![join-table-strategy](http://www.plantuml.com/plantuml/png/AyaioKbLUBDmmvkvEnJUjlQycRlbzOufN5oAAQbfcNcf2YM9IJcf6XMQ2iavYSN52XKADZOA6Y4wocvkZa2WgM1d1rTQK9IPcrXK6P-TMWdIO9DaLs6ZduIGJyilIirCpuFBbuBaI-suCZ9JKXLoKqioybEA8PpYajIYrFoIoeBKOYukY0iyGrnzDHMgkHI00DaYa7YyfuicMCBFqTSLp0W8ydI6i5n28WDSkMfXnZZwE8KIDWVPfIWIZa4Xdi4X2EDy_SCyNSatpiKpoDKvn3y8OZQO1JVQoT_VcmA7XePuGCtr37Ro0naBabVGrSslKG070X8Vx0iaFiXgBW00)

```java
// 상위 클래스 : 아이템
@Entity
@Inheritance(strategy = InheritanceType.JOINED) // 상속 매핑 전략 명시 - 조인전략
@DiscriminatorColumn(name = "DTYPE") // 상위 클래스에 구분 칼럼 지정
public abstract class Item {

    // 식별자 자동 생성
    @Id
    @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    // 이름
    private String name;

    // 가격
    private int price;

    // getter, setter

}
```

```java
// 하위 클래스 : 앨범
@Entity
@DiscriminatorValue("A") // 엔티티를 저장할 때 구분 칼럼 값 지정
public class Album extends Item {

    private String artist;

    // ...
}
```

```java
// 하위 클래스 : 영화
@Entity
@DiscriminatorValue("M")
public class Movie extends Item {

    private String director;
    private String actor;

    // ...
}
```

```java
// 하위 클래스 : 책
@Entity
@DiscriminatorValue("B")
@PrimaryKeyJoinColumn(name = "BOOK_ID") // ID 재정의
public class Book extends Item {

    private String author;
    private String isbn;

    // ...
}
```

- `@Inheritance(strategy = InheritanceType.JOINED)` : 상속 매핑은 상위(부모)클래스에서 사용, 매핑 전략을 조인 전략으로 지정
- `@DiscriminatorColumn(name = "DTYPE")` : 상위 클래스에 구분 칼럼 지정, 이 칼럼으로 저장된 자식 테이블을 구분
- `@DiscriminatorValue("M")` : 엔티티를 저장할 때 구분 칼럼에 입력할 값 지정
- `@PrimaryKeyJoinColumn(name = "BOOK_ID")` : 하위 테이블의 기본키 칼럼명을 변경하고 싶을 경우 사용

#### 1.1.2 장단점 및 특징

- 장점
  - 테이블 정규화
  - 외래키 참조 무결성 제약조건 활용 가능
  - 저장공간 효율화
- 단점
  - 조회할 때 조인이 많이 사용되므로 성능 저하
  - 조회 쿼리 복잡
  - 데이터를 등록할 INSERT SQL을 두번 실행
- 특징
  - 구분 칼럼을 사용해야하지만 하이버네이트를 포함한 몇몇 구현체에서는 구분칼럼 없이도 동작

### 1.2 단일 테이블 전략

**단일 테이블 전략은 테이블 하나만 사용하고, 구분칼럼으로 어떤 자식 데이터가 저장되었는지 구분한다** 조회할 때
조인을 사용하지 않으므로 일반적으로 가장 빠르다.

#### 1.2.1 예제 코드

![single-table-strategy](http://www.plantuml.com/plantuml/png/AyaioKbLUDEzyTME1eMthJrlvcvvlMCA5vTYIcfQPbwgGabYKawgHeMcGd9EOd6nGeM2ZOs2HeYEifkR8n1eAjZPmPKM56Nc9XPLncUdLe8qs2JPLTZe9o7aq_ABKZFJy-3ofI3vKZkkZCoKL8NSr9AC_9HY2AUu9BKeDR-aiY2rc8iBuWBF45TVJOLgBWKWGBO891xlwQ89bl1pz7K5CmA2F9qXBCUW4C_W433JnJF8rJd4FmWYZc25XdX0cPx1JduGfI6H0QvSjLm0)

```java
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)   // 상속 매핑 - 싱글테이블 전략
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    private String name;
    private String price;

    // ...
}
```

```java
@Entity
@DiscriminatorValue("A") // 지정하지 않으면 기본값으로 엔티티 이름이 지정
public class Album extends Item {

    private String artist;

    // ...

}
```

```java
@Entity
@DiscriminatorValue("B")
public class Book extends Item {

    private String author;
    private String isbn;

    // ...
}
```

```java
@Entity
@DiscriminatorValue("M")
public class Movie extends Item {

    private String director;
    private String actor;

    // ...
}
```

- `@Inheritance(strategy = InheritanceType.SINGLE_TABLE)` : 단일 테이블 전략에 사용
- 테이블 하나에 모든 것을 통합하기 때문에 구분칼럼을 필수로 사용해야 함

#### 1.2.2 장단점 및 특징
- 장점
  - 조인이 필요없기 때문에 조회성능이 빠름
  - 조회 쿼리 단순
- 단점
  - 자식 엔티티가 매핑한 칼럼은 모두 `null`을 허용해야함
  - 단일 테이블에 모든 것을 저장하므로 테이블이 커질 수 있음
  - 상황에 따라 조회성능이 오히려 느려질 수도 있음
- 특징
  - 구분칼럼을 반드시 사용해야함
  - `@DiscriminatorValue`를 지정하지 않으면 엔티티 이름이 기본값

### 1.3 구현 클래스마다 테이블 전략

**구현 클래스마다 테이블 전략은 자식 엔티티 마다 테이블을 만드는데 각각 필요한 칼럼이 모두 있다.**

#### 1.3.1 예제 코드

![table-per-concrete-class](http://www.plantuml.com/plantuml/png/AyaioKbLUBLrpTiPBGfl6xUydZVZJTUIryixNdSlKNZRslDcxfRNEwPmSIccfAPbvgKgb2GcvQHgL6Wg9ESa5XShL2ZOs2XeXEWekxav0e6cWfqTN6b1KMPkOb5bV7Pg9KY3JP9TXOr-4a8-BRyeDJCz3ovV2f8ljUFAo4n9LSXDBSd8Joc6S8fBKejIyqig2rABkRWWBd3qSGhrrLIevb800cIRF4DSVUCzNJJ14dwElgvWHa2GfpE4vHWKuXaSmbKBCyZNFypJbHo3N3o3N9r3_8CWfe9PS4ET_FszoN9SQ8W7r4JFO2S_e840)

```java
//@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // 상속매핑 - 구현 클래스마다 테이블 전략
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    private String name;

    private int price;

    // ...
}
```

```java
@Entity
public class Album extends Item {

    private String artist;

    // ...

}
```

```java
@Entity
public class Book extends Item {

    private String author;
    private String isbn;

    // ...
}
```

```java
@Entity
public class Movie extends Item {

    private String director;
    private String actor;

    // ...
}
```

- `@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)` : 구현 클래스마다 테이블 전략을 사용
- 자식 엔티티마다 테이블을 생성하는데 일반적으로 추천하지 않는 전략

#### 1.3.2 장단점 및 특징
- 장점
  - 서브 타입을 구분해서 처리할 때 효과적
  - `not null` 제약조건을 사용할 수 있음
- 단점
  - 여러 자식 테이블을 함께 조회할 때 성능이 느림
  - 자식 테이블을 통합해 쿼리하기 어려움
- 특징
  - 구분 칼럼을 사용하지 않음

## 2. @MappedSuperClass

등록일과 수정일 같이 여러 엔티티에서 공통으로 사용하는 매핑정보만 상속받고 싶을 경우 이 기능을 사용

## 3. 복합키와 식별관계 매핑

데이터베이스의 식별자가 하나 이상일 때 매핑하는 방법, 데이터베이스 설계에서 이야기하는 식별관계와 비식별 관계

## 4. 조인 테이블

테이블은 외래키 하나로 연관관계를 맺을 수 있지만 연관관계를 관리하는 연결테이블을 두는 방법

## 5. 엔티티 하나에 여러 테이블 매핑

엔티티 하나에 테이블 하나를 매핑하지만 엔티티 하나에 여러 테이블을 매핑하는 방법


## 01) 상속관계 매핑
객체의 상속관계를 데이터베이스에 어떻게 매핑?

- 관계형 데이터베이스에는 객체지향 언어에서 다루는 상속이라는 개념은 X
- 대신 '슈퍼타입 서브타입 관계'라는 모델링 기법이 객체의 상속개념과 유사함
- ORM에서 이야기하는 상속관계 매핑은 객체의 상속 구조와 데이터베이스 슈퍼서브타입 관계를 매핑하는 것
- '슈퍼타입 서브타입' 논리모델을 실제 물리모델인 테이블로 구현할 때 3가지의 방법을 선택할 수 있음
    1. 각각 테이블로 변환 - 각각 테이블로 만들고 조회할 때 조인 (조인 전략)
        * 장점 : 테이블 정규화, 외래키 참조 무결성 제약조건 활용, 저장공간 효율적
        * 단점 : 조인으로 인한 성능저하, 조회쿼리 복잡, 데이터 등록시 insert sql 두번 실행
    2. 통합 테이블로 변환 - 테이블을 하나만 사용해서 통합 (단일 테이블 전략)
        * 장점 : 조인이 필요없어 조회 성능이 빠름, 조회쿼리 단순
        * 단점 : 자식엔티티가 매핑한 칼럼은 모두 null 허용, 단일테이블에 모든것을 저장하기 때문에 오히려 조회성능이 떨어질 수 있음
        * 특징 : `@DiscriminatorColumn`을 꼭 설정해야함, `@DiscriminatorValue` 미지정시 기본엔티티 이름으로 저장됨
    3. 서브타입 테이블로 변환 - 서브타입마다 하나의 테이블을 만듬 (테이블 전략)
        * 장점 : 서브타입을 구분해 처리할 때 효과적, not null 제약조건을 사용할 수 있음
        * 단점 : 여러 자식테이블을 함께 조회할 때 성능이 느림(SQL에 UNION사용), 자식테이블을 통합해서 쿼리하기 어려움
        * 특징 : 구분칼럼 사용X
        * 추천하지않는 전략, 조인이나 단일테이블을 추천한다고한다.

## 02) `@MappedSuperClass`
등록일, 수정일 같이 여러 엔티티에서 공통으로 사용하는 매핑정보만 상속 받고 싶다면 이 기능을 사용!

- 부모클래스는 테이블과 매핑하지않고, 상속받는 자식클래스에게 매핑정보만 제공하고 싶을 때 사용
- `@Entity`는 실제 테이블에 매핑되지만 `@MappedSuperClass`는 실제 테이블에 매핑되지 않음
- 단순히 매핑정보를 상속할 목적으로만 사용
- `@MappedSuperClass`로 지정한 클래스는 엔티티가 아니기때문에 `em.find()`나 JPQL에서 사용X

## 03) 복합키와 식별관계 매핑
데이터베이스의 식별자가 하나 이상일 때 매핑하는 방법!!!

- 식별관계 VS. 비식별관계
    - 식별관계 : 부모테이블의 기본키를 내려받아 자식테이블의 기본키 + 외래키로 사용
    - 비식별관계 : 부모테이블의 기본키를 받아서 자식테이블의 외래키로만 사용
        1. 필수적 비식별 - 외래키 null 허용X
        2. 선택적 비식별 - 외래키 null 허용O
- 복합 키 : 비식별관계 매핑
    - 기본키를 구성하는 칼럼이 하나면 이전에 해왔던 것처럼 매핑하면 됨
    - JPA에서 둘 이상의 컬럼으로 구성된 복합 기본키를 사용하려면 별도의 식별자 클래스를 만들고, 그곳에 `equals()`, `hashCode()`를 구현해야하는데 2가지 방법으로 나눌 수 있음
        1. `@IdClass` - 관계형 데이터베이스에 가까운 방법
            * 조건1 : 식별자 클래스의 속성명과 엔티티에서 사용하는 식별자의 속성명이 같아야함
            * 조건2 : `Serializable` 인터페이스를 구현해야함
            * 조건3 : `equals()`, `hashCode()`를 구현해야함
            * 조건4 : 기본생성자 필요
            * 조건5 : 식별자 클래스는 public
        2. `@EmbeddedId` - 좀더 객체지향에 가까운 방법, `@IdClass`와 다르게 식별자 클래스에 기본키를 직접 매핑
            * 조건1 : `@Embeddable`어노테이션을 붙여주어야함
            * 조건2 : `Serializable`인터페이스 구현
            * 조건3 : `equals()`, `hashCode()` 구현
            * 조건4 : 기본생성자 필요
            * 조건5 : 식별자 클래스는 public
- 복합 키 : 식별관계 매핑
    - `@IdClass`
        * 식별관계는 기본키와 외래키를 같이 매핑해야 하므로 식별자 매핑 `@Id`와 연관관계 매핑 `@ManyToOne`을 같이 사용
    - `@EmbeddedId`
        * `@Id`대신 `@MapsId`를 사용한다.
        * `@MapsId`는 외래키와 매핑한 연관관계를 기본키에도 매핑하겠다는 의미
        * `@MapsId` 속성값은 `@EmbeddedId`를 사용한 식별자 클래스의 기본키 필드를 지정
- 비식별관계로 매핑
    - 식별관계 복합키를 사용하는 것보다 매핑도 쉽고 코드도 단순
    - 복합키도 없기 때문에 복합키 클래스를 만들지 않아도 됨
- 일대일 식별관계
    - 일대일 식별관계의 경우 자식 테이블의 기본 키 갑승로 부모 테이블의 기본키 값만을 사용
    - 부모테이블의 기본키가 복합키가 아니라면 자식테이블의 기본키를 복합키로 구성하지 않아도 무방
- 식별, 비식별 관계의 장단점
    - 식별관계보다는 비식별관계를 선호
    - 식별관계는 부모테이블의 기본키를 자식테이블로 전파하면서 자식테이블의 기본키 칼럼이 증가
    - 조인할 때 SQL이 복잡해지고 기본키 인덱스가 불필요하게 증가
    - 식별관계의 경우 2개 이상의 컬럼을 합해서 복합 기본키를 만들어야하는 경우가 많음
    - 식별관계를 사용할 때 기본키로 자연키 컬럼을 조합하는 경우가 많음
    - 비식별관계의 기본키는 대리키를 주로 사용
    - 식별관계의 경우 부모테이블의 기본키를 자식테이블의 기본키로 사용하기 때문에 테이블구조가 비식별관계보다 유연하지 못함
- ORM 프로젝트시 추천 방법은?
    - 비식별관계를 사용하고 기본키는 `Long`타입의 대리키를 사용
    - 대리키의 경우 비지니스와 관련이 없기 때문에 비지니스 요구사항이 변경되더라도 유연한 대처가 가능
    - `GenerateValue`를 통해 간편하게 대리키를 생성
    - 식별자 데이터 타입을 `Long`으로 추천하는 이유는 아주 크기 떄문(약920경)
    - 선택적 비식별(null 허용 O)보다는 필수적 비식별관계(null 허용 X)를 추천, 조인시 내부조인만 사용해도 되기 때문

## 04) 조인테이블
연결테이블을 매핑하는 방법!!!
- 데이터베이스 테이블의 연관관계를 설계하는 방법 2가지
    1. 조인 컬럼 사용(외래키) - `@JoinColumn`으로 매핑
        * 외래키 칼럼을 통해 관리
        * 선택적 비식별관계를 사용하기 때문에 외부조인을 사용해야함
        * 외래키 값이 null로 저장되는 단점이 존재
    2. 조인 테이블 사용(테이블 사용) - `@JoinTable`로 매핑, N:M를 1:N, N:1관계로 풀어내기 위해 사용
        * 조인 테이블이라는 별도의 테이블을 통해 관리
        * 연관관계를 관리하는 조인테이블을 추가하고 두 테이블의 연관관계를 관리
        * 두 테이블에는 외래키 칼럼이 존재하지 않음
        * 별도의 테이블을 만들어야하는 단점이 존재
        * 두 테이블을 조인하기 위해서는 조인테이블까지 추가로 조인
- 1:1 조인테이블
    - 1:1 관계를 만드려면 조인 테이블의 외래키 칼럼 각각에 총 2개의 유니크 제약조건을 걸어야 함
- 1:N 조인테이블
    - 1:N 관계를 만드려면 N와 관련된 컬럼에 유니크 제약조건을 걸어야 함
- N:1 조인테이블
    - N:1 관계는 1:N에서 방향만 반대
- N:N 조인테이블
    - N:N 관계를 만들려면 두 컬럼을 합해서 하나의 유니크 제약조건을 걸어야 함
- 조인테이블에 컬럼을 추가하면 `@JoinTable` 전략을 사용할 수 없기 때문에 새로운 엔티티를 만들어 조인테이블과 매핑해야함

## 05) 정리

1. 객체의 상속관계를 데이터베이스에 매핑하는 방법 학습
2. 매핑정보만 상속하는 `@MappedSuperClass`를 학습
3. 복합키 매핑하는 방법(식별, 비식별)을 학습
4. 조인테이블 학습
