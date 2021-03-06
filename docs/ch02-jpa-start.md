> 본 글은 [자바 ORM 표준 JPA 프로그래밍](https://book.naver.com/bookdb/book_detail.nhn?bid=9252528)를 읽고 개인적으로
학습한 내용 복습하기 위해 작성된 글로 내용상 오류가 있을 수 있습니다. 오류가 있다면 지적 부탁 드리겠습니다.


# JPA - 시작

## 1. JPA 프로젝트 생성 및 설정

### 1.1 프로젝트 생성

IntelliJ에서 maven 프로젝트를 생성하고 `pom.xml`을 아래와 같이 작성해준다.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.doubles</groupId>
    <artifactId>jpa02</artifactId>
    <version>1.0-SNAPSHOT</version>

    <dependencies>
        <!--JPA, 하이버네이트-->
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-entitymanager</artifactId>
            <version>4.3.10.Final</version>
        </dependency>
        <!--h2 DB-->
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <version>1.4.187</version>
        </dependency>
    </dependencies>
</project>
```

- JPA, 하이버네이트 라이브러리 추가
- H2 데이터베이스 라이브러리 추가

### 1.2 H2 데이터베이스 설치 및 테이블 생성

[H2 데이터베이스](http://www.h2database.com/h2-2014-04-05.zip)를 다운받고, 압축을 해제한 뒤
`h2/bin` 경로에서 아래와 같이 실행 시킨다.

```shell
java -jar h2[버전].jar
```

그리고 `http://localhost:8082/`로 접속하여 h2 로그인 화면이 나오면 아래의 정보를 입력하고 연결을
진행한다.
- 드라이버 클래스 : org.h2.driver
- JDBC URL : jdbc:h2:tcp://localhost/~/test
- 사용자명 : sa
- 비밀번호 : 없음

그리고 예제에 필요한 테이블 생성을 해준다.

```sql
CREATE TABLE MEMBER (
  ID VARCHAR(255) NOT NULL,
  NAME VARCHAR(255),
  AGE INTEGER NOT NULL,
  PRIMARY KEY (ID)
)
```

### 1.3 persistence.xml 설정

JPA는 `persistence.xml`을 사용해 필요한 정보를 관리한다. 이 설정 파일이 `META-INF/persistence.xml` 경로에 있으면 별도의 설정 없이 인식한다.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--JPA는 persistence.xml을 사용하여 필요한 설정 정보를 관리-->
<persistence xmlns="http://xmlns.jcp.org/xml/ns/persistence" version="2.1"> <!-- 버전 명시 -->
    <persistence-unit name="jpa02"> <!-- 데이터베이스당 하나의 영속성 유닛을 등록하기 위해 고유한 이름 부여 -->
        <properties>

            <!-- 필수 속성 -->
            <!--javax.persistence로 시작하는 속성은 JPA 표준속성으로 특정 구현체에 종속되지 X-->
            <!--JPA 표준 속성-->
            <property name="javax.persistence.jdbc.driver" value="org.h2.Driver"/> <!--JDBC 드라이버-->
            <property name="javax.persistence.jdbc.user" value="sa"/> <!--데이터베이스 접속 id-->
            <property name="javax.persistence.jdbc.password" value=""/> <!--데이터베이스 접속 pw-->
            <property name="javax.persistence.jdbc.url" value="jdbc:h2:tcp://localhost/~/test"/> <!--데이터베이스 접속 URL-->

            <!--하이버네이트 속성-->
            <!--hibernate로 시작하는 속성은 하이버네이트 전용 속성으므로 하이버네이트에서만 사용가능-->
            <!--데이터베이스 방언 : 데이터베이스별로 SQL문법과 함수가 조금씩 다른 것을 의미-->
            <!--H2 데이터베이스 방언-->
            <property name="hibernate.dialect" value="org.hibernate.dialect.H2Dialect" />

            <!--
            오라클 10g 데이터베이스 방언
            <property name="hibernate.dialect" value="org.hibernate.dialect.Oracle10gDialect" />
            -->

            <!--
            Mysql 데이터베이스 방언
            <property name="hibernate.dialect" value="org.hibernate.dialect.MySQL5InnoDBDialect" />
            -->

            <!-- 옵션 -->
            <property name="hibernate.show_sql" value="true" /> <!--하이버네이트가 실행한 SQL을 출력-->
            <property name="hibernate.format_sql" value="true" /> <!--하이버네이트가 실행한 SQL을 출력할 때 정렬-->
            <property name="hibernate.use_sql_comments" value="true" /> <!--쿼리를 출력할 때 주석도 함께-->
            <property name="hibernate.id.new_generator_mappings" value="true" /> <!--JPA표준에 맞춘 새로운 키 생성 전략 사용-->

            <!--<property name="hibernate.hbm2ddl.auto" value="create" />-->
        </properties>
    </persistence-unit>
</persistence>
```

### 1.4 프로젝트 구조

위와 같이 설정을 마무리하고 기본패키지 하위에 앞으로 작성할 클래스를 추가하면 아래와 같이 프로젝트 구조가
이루어진다.

```
src/main
├── java
|     └── 기본패키지
|             ├── JpaMain.java (실행클래스)
|             └── Member.java (회원 엔티티)
├── resources
|       └── META-INF
|               └── persistence.xml (JPA 설정정보)
pom.xml
```

## 2. 객체 매핑

애플리케이션에서 사용할 회원 클래스를 아래와 같이 작성해준다.

```java
// Member 클래스
@Entity // 클래스와 테이블을 매핑하는 것을 알려준다. 엔티티 클래스라 한다.
@Table(name = "MEMBER") // 매핑할 테이블 정보 명시, 생력할 경우 클래스명으로 테이블 이름 매핑
public class Member {

    @Id // 기본키 매핑, 식별자 필드
    @Column(name = "ID") // 필드를 컬럼에 매핑
    private String id;

    @Column(name = "NAME")
    private String username;

    private Integer age; // 매핑 정보가 없을 경우 필드명이 컬럼명으로 매핑 , 대소문자를 구분하는 DB일 경우 명시해줘야한다.

    // getter, setter 생략
}
```

기본적인 매핑 애너테이션은 추가적으로 정리할 예정

## 3. 애플리케이션 개발

JPA 애플리케이션을 실행하는 코드는 아래와 같다.

```java
// JpaMain 클래스
public class JpaMain {
    public static void main(String[] args) {

        // 1. 엔티티 매나저 팩토리 생성
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa02");

        // 2. 엔티티 매나저 생성
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // 3. 트랜잭션 획득
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();  // 4. 트랜잭션 시작
            logic(entityManager);       // 5. 비지니스 로직 수행
            entityTransaction.commit(); // 6. 트랜잭션 커밋
        } catch (Exception e) {
            entityTransaction.rollback();   // 6. 트랜잭션 롤백
        } finally {
            entityManager.close();      // 7. 엔티티 매니저 종료
        }
        entityManagerFactory.close();   // 7. 엔티티 매니저 팩토리 종료
    }

    // 비지니스 로직
    private static void logic(EntityManager entityManager) {

        // 회원 객체 생성
        String id = "id01";
        Member member = new Member();
        member.setId(id);
        member.setUsername("doubles");
        member.setAge(20);

        // 등록
        entityManager.persist(member);

        // 수정
        member.setAge(25);

        // 조회 : 하나의 회원
        Member findMember = entityManager.find(Member.class, id);
        System.out.println("findMember : " + findMember.getUsername() + ", " + findMember.getAge());

        // 조회 : 목록
        List<Member> members = entityManager.createQuery("SELECT m FROM Member m", Member.class).getResultList();
        System.out.println("members.size = " + members.size());

        // 삭제
        entityManager.remove(member);
    }
}
```

위의 코드는 엔티티 **매니저 설정, 트랜잭션 관리, 비지니스 로직** 으로 크게 3부분으로 나누어져 있다. 이 세부 내용을 살펴보자.

### 3.1 엔티티 매니저 설정

엔티티 매니저의 생성과정은 아래와 같은 순서로 진행이 된다.

1. 설정정보 조회(`/META-INF/persistence.xml`)
2. 엔티티 매니저 팩토리 생성
3. 엔티티 매니저 생성
4. 종료

위의 과정에서 주의해야할 사항들은 아래와 같다.
- 엔티티 매니저 팩토리는 애플리케이션 전체에 한번만 생성하고 공유해서 사용해야한다.
- 엔티티 매니저는 데이터베이스 커넥션과 밀접한 관계가 있으므로 스레드가 공유하거나 재사용해서는 안된다.
- 사용이 끝난 엔티티 매니저는 반드시 종료해야 한다.
- 애플리케이션을 종료할 때 엔티티 매니저 팩토리도 종료해야 한다.

### 3.2 트랜잭션 관리

JPA를 사용하면 항상 트랜잭션 안에서 데이터를 변경해야 한다. 트랜잭션 없이 데이터를 변경할 경우 항상 예외가
발생한다. 트랜잭션을 시작하려면 엔티티 매니저에서 트랜잭션 API를 받아와야만 한다.

- 비지니스 로직이 정상 작동하면 트랜잭션을 커밋(commit)
- 예외가 발생하면 롤백(rollback)

### 3.3 비지니스 로직

위의 코드에서 비지니스 로직을 보면 회원 엔티티를 하나 생성하고 엔티티 매니저를 통해 데이터베이스에 등록,
수정, 삭제, 조회를 한다.

- 등록
  - 엔티티를 저장하려면 엔티티 매니저의 `persist()`메서드에 저장할 엔티티를 넘겨주면 된다.

- 수정
  - JPA는 별도의 수정 메서드가 존재하지 않는다.
  - 단순히 엔티티의 값만 변경하면 JPA는 어떤 엔티티가 변경되었는지 추척한다.

- 삭제
  - 엔티티를 삭제하려면 엔티티 매니저의 `remove()`메서드에 삭제할 엔티티를 넘겨주면 된다.

- 조회 : 하나의 엔티티
  - `find()`메서드는 조회할 엔티티 타입과 `@Id`로 DB 테이블의 기본키와 매핑한 식별자 값으로 엔티티
  하나를 조회한다.
  - 조회한 결과 값으로 엔티티를 생성해 반환한다.

- 조회 : 엔티티 목록
  - 하나 이상의 회원 목록을 조회할 경우 JPQL를 사용한다.

그렇다면 JPQL에 대해 간략하게 알아보자. (JPQL은 추후에 따로 정리할 예정)

JPA는 엔티티 객체를 중심으로 개발하므로 검색할 때도 테이블이 아닌 엔티티 객체를 대상으로 검색해야 한다.
그런데 테이블이 아닌 엔티티 객체를 대상으로 검색하려면 데이터베이스의 모든 데이터를 불러와 엔티티 객체로
변경한 다음 검색을 해야하는데 이는 사실상 불가능하다.

그래서 필요한 데이터만 데이터베이스에 불러오려면 검색조건이 포함된 SQL을 사용해야 한다. JPA는 이러한 문제를 JPQL(Java Persistence Query Language)이라는 쿼리언어로 해결한다.

JPA는 SQL을 추상화한 JPQL을 제공하는데 SQL과 문법이 거의 유사해서 SELECT, FROM, WHERE, GROUP BY, HAVING, JOIN 등을 사용할 수 있다.

- JPQL : 엔티티 객체(클래스, 필드)를 대상으로 쿼리
- SQL : 데이터베이스 테이블을 대상으로 쿼리

```sql
SELECT m FROM Member m
```

위의 `Member`는 데이터베이스의 MEMBER 테이블이 아니라 엔티티의 `Member`이다. JPQL은 데이터베이스 테이블의 `MEMBER`를 전혀 알지 못하는 것이다.

JPQL을 사용하려면 `entityManager.createQuery(JPQL, returnType)`메서드를 실행해서 쿼리 객체를 생성한 후에 쿼리객체의 `getResultList()`메서드를 호출하면 된다.

### 3.4 애플리케이션 실행 결과

```console
findMember : doubles, 25
Hibernate:
    /* insert com.doubles.jpa02.Member
        */ insert
        into
            MEMBER
            (age, NAME, ID)
        values
            (?, ?, ?)
Hibernate:
    /* update
        com.doubles.jpa02.Member */ update
            MEMBER
        set
            age=?,
            NAME=?
        where
            ID=?
Hibernate:
    /* SELECT
        m
    FROM
        Member m */ select
            member0_.ID as ID1_0_,
            member0_.age as age2_0_,
            member0_.NAME as NAME3_0_
        from
            MEMBER member0_
members.size = 1
Hibernate:
    /* delete com.doubles.jpa02.Member */ delete
        from
            MEMBER
        where
            ID=?
```

위와 같이 콘솔에 수행한 SQL들이 출력되는 것을 확인할 수 있다.

## 4. Summery / Conculsion

- `persistent.xml`설정
- 앤티티 매니저 팩토리 -> 앤티티 매니저 -> 트랜잭션 획득 -> 비지니스 로직 수행 -> 앤티티 매니저 종료 -> 앤티티 매니저 팩토리 종료
- 매핑 애너테이션 : `@Entity`, `@Table`, `@Column`, `@Id`
- 앤티티 매니저 메서드
  - `persist()` : 저장
  - `remove()` : 삭제
  - `find()` : 한건 조회
  - JPQL : 목록 조회
    - `createQuery()` : 쿼리 객체 생성
    - `getResultList()` : 결과 값 호출
