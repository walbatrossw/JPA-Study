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

**단일 테이블 전략은 테이블 하나만 사용하고, 구분칼럼으로 어떤 자식 데이터가 저장되었는지 구분한다.** 조회할 때
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

이 전략은 DB설계자와 ORM 전문가 둘다 추천하지 않는 방식이다.

## 2. @MappedSuperClass

**등록일과 수정일 같이 여러 엔티티에서 공통으로 사용하는 매핑정보만 상속받고 싶을 경우 이 기능을 사용한다.**
지금까지는 상위, 하위 클래스 모두 DB 테이블과 매핑했지만 상위 클래스는 테이블과 매핑하지 않고 하위 클래스에게
매핑 정보만 제공할 때 사용한다.

### 2.1 예제 코드

![MappedSuperClass](http://www.plantuml.com/plantuml/png/ZOyzJiD048NxFSKGcbZ2WWDO5ebmH2JR84oFrdY2L_XFthLa2oWGa0ewsjmXa4IYu4J6kGF-mmcYOPgTcVU-jxk22G_XqA1HX8wLH1XVUfHpM3yz5xFbryThllY4V3uhtfU4x3WuOG62eBQ7_LI2nfk2ea3_V6ztD1gK9O6gTjOePZwDiujhp2f0iTLKsRSt-YICaLq5dPvKUg8Ibpa8FWetT7WpS84nXYABa1FIlC3GZA5s9i4DWRAshneVIVNE71XQtMZ6OAoJkn_IqdJzdwHrV79Q3oLCcWyFAKSj44naRG1pwYEWFbQsncMWRsFSEj2dpDje5dfUft4tZCmMtOfMRcMv3VcpRdX2Wb0WzFB5QZrTGJ5zAHxVe7YuBxiUK0xcmQz4kLfy0m00)


```java
// 부모 클래스는 테이블과 매핑 X
// 자식 클래스에게 매핑 정보만 제공하고 싶을 경우 사용
@MappedSuperclass
public abstract class BaseEntity {

    // 식별자
    @Id
    @GeneratedValue
    private Long id; // 공통 속성

    // 이름
    private String name; // 공통 속성

    // ...
}
```

```java
@Entity
@AttributeOverride(name = "id", column = @Column(name = "MEMBER_ID"))   // 상속받은 칼럼 이름 재정의 - 하나만
public class Member extends BaseEntity {

    // 식별자, 이름 상속

    // 이메일
    private String email;

    // ...
}
```

```java
@Entity
@AttributeOverrides({ // 상속 받은 칼럼명을 다수 변경하고 싶을 경우
        @AttributeOverride(name = "id", column = @Column(name = "SELLER_ID")),      // 상속 받은 칼럼명 재정의
        @AttributeOverride(name = "name", column = @Column(name = "SELLER_NAME"))   // 상속 받은 칼럼명 재정의
})
public class Seller extends BaseEntity {

    // 식별자, 이름 상속

    // 샵 이름
    private String shopName;


}
```

- `@AttributeOverride` : 상위 클래스로부터 물려받은 매핑 정보를 재정의
- `@AttributeOverrides` : 상위 클래스로부터 물려받은 매핑 정보들(둘 이상)을 재정의
- `@AssociationOverride` : 연관관계를 재정의
- `@AssociationOverrides` : 연관관계들(둘 이상)을 재정의

### 2.2 특징

- `@MappedSuperClass`로 지정한 클래스는 엔티티가 아니기 때문에 `em.find()`나 JPQL 사용불가
- 이 클래스를 직접 사용할 일이 거의 없기 때문에 추상 클래스로 만드는 것을 권장

`@MappedSuperClass`는 테이블과 무관하고, 단순히 엔티티가 공통으로 사용하는 매핑정보만 모아주는 역할을 수행한다.
등록일자, 수정일자, 등록자, 수정자 같은 여러 엔티티에서 공통으로 사용하는 속성을 효과적으로 관리할 수 있다.

## 3. 복합키와 식별관계 매핑

데이터베이스의 식별자가 하나 이상일 때 매핑하는 방법과 데이터베이스 설계에서 이야기하는 식별관계와 비식별 관계에 대해 알아보자.

### 3.1 식별관계와 비식별 관계

**DB 테이블 사이의 관계는 외래키가 기본키에 포함되는지에 따라 식별관계와 비식별관계로 구분할 수 있다.**

![identifying-relationship](http://www.plantuml.com/plantuml/png/AyaioKbLUDCzz_Nc5Xs5rpjR84o5LriQNcrkuU9IJ4bDoynBLIX9JCf9rQZGL4ZEIImkLgXGiB5Hq0ZHKNPpSmG2JGKxExZIWgBCtCIYolZir4gG1fiakmeR_II4V5f-KMfcUXvSlXGaNsh7bP6PaggGcrgIaPzI3E4KbwGMfUQNL1Qa5dDnGLmGu1ZawXTYgAbGpQK01CXsGQJYFLqqmL9-ZhwkNBKmVSuUdZukn6X3TXsQ5B8ki7aux89eCoBFZLYreTg6nudO769CdaECgU8GQ7mfrDJewkPNAq2Y0IPi11JMquC96y64ZHLgyX72nbnSUVabgGfAA2bJII6nM27F42RtTchBcuuPRnOqOYHXY48Zk24j2zuspyMjq1ZXP5sm-J2Nwe8DWJbG5wGI0000)

- 식별 관계 : 부모 테이블의 기본키를 내려받아 자식 테이블의 기본키 + 외래키로 사용
- 비식별 관계 : 부모 테이블의 기본키를 내려받아 자식 테이블의 외래키로만 사용
  - 필수적 비식별 관계 : 외래키 NULL 허용하지 않음, 연관관계 필수
  - 선택적 비식별 관계 : 외래키 NULL 허용, 연관관계 선택

### 3.2 복합키 : 비식별 관계 매핑

기본 키를 구성하는 컬럼이 하나일 경우 이전에 작성한 것처럼 단순하게 매핑하면 된다. 하지만 둘 이상의 컬럼으로
구성된 복합 키본키는 별도의 식별자 클래스를 만들어야하고 그곳에 `equals()`와 `hashCode()`를 구현해야 한다.

JPA는 복합키를 지원하기 위해 `@IdClass`와 `@EmbeddedId` 2가지 방법을 제공한다.

#### 3.2.1 @IdClass

`@IdClass`는 관계형 데이터베이스에 가까운 방법이다.

![]()

```java
// 복합키 비식별 관계 매핑
@Entity
@IdClass(ParentId.class) // 관계형 데이터베이스에 가까운 방법, 식별자 클래스 지정
public class Parent {

    // 복합키 1
    @Id
    @Column(name = "PARENT_ID1")
    private String id1; // ParentId.id1과 연결

    // 복합키 2
    @Id
    @Column(name = "PARENT_ID2")
    private String id2; // ParentId.id2와 연결

    private String name;

    // ...
}
```

```java
// 식별자 클래스 반드시 public
public class ParentId implements Serializable { // Serializable 반드시 구현

    // 식별자 클래스의 속성명과 사용하는 식별자의 속성명이 일치해야함

    private String id1; // Parent.id1 매핑

    private String id2; // Parent.id2 매핑

    // 기본생성자 반드시 존재
    public ParentId() {
    }

    public ParentId(String id1, String id2) {
        this.id1 = id1;
        this.id2 = id2;
    }

    // equals, hashCode 반드시 구현
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParentId parentId = (ParentId) o;

        if (id1 != null ? !id1.equals(parentId.id1) : parentId.id1 != null) return false;
        return id2 != null ? id2.equals(parentId.id2) : parentId.id2 == null;
    }

    @Override
    public int hashCode() {
        int result = id1 != null ? id1.hashCode() : 0;
        result = 31 * result + (id2 != null ? id2.hashCode() : 0);
        return result;
    }
}
```

`@IdClass`를 사용할 때 식별자 클래스는 아래와 같이 5가지 조건을 만족해야한다.
1. 식별자 클래스의 속성명과 엔티티에서 사용하는 식별자의 속성명이 같아야 함.
2. `Serializable` 인터페이스를 구현해야함.
3. `equals()`, `hashCode()`를 구현해야 함.
4. 기본 생성자가 있어야 함.
5. 식별자 클래스는 `public`이어야 함.

#### 3.2.2 @EmbeddedId

`@EmbeddedId`는 객체지향에 가까운 방법이다.

### 3.3 복합키 : 식별 관계 매핑

### 3.4 비식별 관계로 구현

### 3.5 일대일 식별관계

### 3.6 식별, 비식별 관계의 장단점


## 4. 조인 테이블

테이블은 외래키 하나로 연관관계를 맺을 수 있지만 연관관계를 관리하는 연결테이블을 두는 방법

## 5. 엔티티 하나에 여러 테이블 매핑

엔티티 하나에 테이블 하나를 매핑하지만 엔티티 하나에 여러 테이블을 매핑하는 방법


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
