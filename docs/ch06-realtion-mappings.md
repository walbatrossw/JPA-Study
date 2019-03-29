# 다양한 연관관계 매핑

연관관계들에 대해 정리해보자.

## 1. 다대일(N:1)

다대일(N:1) 관계의 반대방향은 항상 일대다(1:N)이며, DB 테이블의 외래키는 항상 다(N) 쪽에 있기때문에 연관관계의 주인은
항상 다(N)이다.

### 1.1 단방향

![n-to-1-oneway](http://www.plantuml.com/plantuml/png/RP31JeD048RlynHZlPH4gju92KQQUC13nNC3ChKReJIxImBH9ZtepQE9Uk0T6kNWCo6-Wy1IR1CdsTrClld_sHd9P4XORlBgewZUr_Zpz5pbnVVNIvM_OVMwAOirrgVyV0HmREVCdk0grlNu0LtRSW1kGay8T2ZoYUCzO1qiQ1-Nl0XbVRLP8eWll8XWjTMqV4-nvPqRwhg9WvFHw7JG5WCS13HZ2qBf-I4DKmsLKug6WKFtw70yjkhGq3JxqYLdaSUpwHrbZS3mpTGuzl_uBER4RXSzJngUc31d0M54SXu7GYL24gTOPaiI0Ee7QY5QDyLkAqMc5-TQYtQxD7lhmVMLFRcq7BiPMtas-3_7FLad-GK0)

```java
// 다대일 단방향
// 회원 클래스(N)
@Entity
public class Member {

    @Id // 식별자
    @GeneratedValue // 기본키 자동 생성
    @Column(name = "MEMBER_ID") // 컬럼명과 매핑
    private Long id;

    private String username;

    @ManyToOne  // 다대일
    @JoinColumn(name = "TEAM_ID") // 외래키 이름 매핑
    private Team team;

    // getter, setter
}
```

```java
// 다대일 단방향
// 팀 클래스(1)
@Entity
public class Team {

    @Id // 식별자
    @GeneratedValue // 기본키 자동 생성
    @Column(name = "TEAM_ID") // 컬럼명 매핑
    private Long id;

    private String name;

    // getter, setter
}
```

위의 코드는 회원과 팀의 다대일 단방향 관계를 나타낸 것으로 회원은 팀을 참조할 수 있지만 반대로 팀에서 회원을
참조할 수 없다. `@JoinColumn(name = "TEAM_ID")`를 사용하여 `Member.team` 필드를 `TEAM_ID`외래키와
매핑하였고, `Member.team` 필드를 통해 회원 테이블의 `TEAM_ID` 외래키를 관리한다.

### 1.2 양방향

![n-to-1-twoway](http://www.plantuml.com/plantuml/png/VL6xJiCm5Dtz5PUmD8WKM54KAOXKGY8TsZ9N2RbjBVAeR4TA19KO-06MCZ4oi4NGhohX7x3h40ggi2nPznpVSywr1HKnGhry_AXUFvSliAwUwxSLr4_LUdMlzli38Rxddta3M6IMbTt1o7DzGgxZW7FmCGcHmIq1kIoW4Pp2K32QJXiavyZI8C6_kC0WaTX87eGilkLK-OzOVuTGiad09Nn9kL1IJIRZm03RiXmmZhlT4wEnsejmGbC44OGnTWeJj741jWsTqU5-h-VAPOBZj5VdZ2O1AyStMAe6EtGA-oZyuISPGpfDMpvNVEwG6OrGnXEpBEAwu08PPgAS8oT49z1FQpP3RrjfPdnnRcweRHVr0ornDVG6VTVtrEXQKz6x57-rDIrV)

```java
// 다대일 양방향
// 회원 클래스(N)
@Entity
public class Member {

    @Id // 식별자
    @GeneratedValue // 기본키 자동 생성
    @Column(name = "MEMBER_ID") // 컬럼명과 매핑
    private Long id;

    private String username;

    @ManyToOne  // 다대일
    @JoinColumn(name = "TEAM_ID") // 외래키 이름 매핑
    private Team team;

    // getter, setter ...

    public void setTeam(Team team) {
        this.team = team;
        // 무한 루프에 빠지지 않도록 체크
        if (!team.getMembers().contains(this)) {
            team.getMembers().add(this);
        }
    }
}
```

```java
// 다대일 양방향
// 팀 클래스(1)
@Entity
public class Team {

    @Id // 식별자
    @GeneratedValue // 기본키 자동 생성
    @Column(name = "TEAM_ID") // 컬럼명 매핑
    private Long id;

    private String name;

    // 일대다 매핑
    @OneToMany(mappedBy = "team") // 주인 지정
    private List<Member> members = new ArrayList<Member>();

    // 양방향 연관관계 설정
    public void addMember(Member member) {

        this.members.add(member);

        // 무한 루프에 빠지 않도록 체크
        if (member.getTeam() != this) {
            member.setTeam(this);
        }
    }

    // getter, setter
}
```

일대다와 다대일 연관관계는 항상 다(N)에 외래키가 있고, 외래키를 가진 쪽이 연관관계의 주인이다.
주인이 아닌 쪽은 조회를 위한 JPQL이나 객체 그래프를 탐색할 때 사용한다.

양방향 연관관계는 항상 서로를 참조해야하기 때문에 연관관계 편의 메서드를 작성하는 것이 편하다.
한쪽에만 편의 메서드를 작성하거나 양쪽 모두에 작성할 수 있다. 하지만 양쪽에 작성하는 경우 무한
루프에 빠지지 않도록 주의해야한다.

## 2. 일대다(1:N)

일대다의 관계는 다대일의 반대방향으로 엔티티를 하나 이상 참조할 수 있도록 자바 컬렉션(`Collection`, `List`, `Set`, `Map`)
중에 하나를 사용해야한다.

### 2.1 단방향

보통 자신이 매핑한 테이블의 외래키를 관리하지만, 일대다 단방향 관계는 반대쪽 테이블에 있는 외래키를
관리한다. 일대다(1:N)관계에서는 외래키는 항상 다(N) 쪽 테이블에 있기 때문인데 다(N) 쪽 엔티티에는
참조 필드가 없고, 반대쪽 엔티티에만 참조필드가 존재한다. 그래서 반대쪽 테이블의 외래키를 관리하는
특이한 모습이 나타나게 된다.

```java
// 일대다 단방향
// 팀 클래스(1)
@Entity
public class Team {

    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    @OneToMany  // 일대다 매핑
    // 일대다에서 명시하지 않을 경우 조인테이블을 생성
    @JoinColumn(name = "TEAM_ID")   // MEMBER 테이블의 TEAM_ID 외래키를 관리
    private List<Member> members = new ArrayList<Member>();

    // getter , setter
}
```

```java
// 일대다 단방향
// 회원 클래스(N)
@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String username;

    // 참조 필드가 없음

    // getter, setter

}
```

일대다 단방향 관계를 매핑할 때는 일(1) 쪽에 `@JoinColumn`을 명시해야만 한다. 그렇지 않으면
JPA는 연결테이블을 중간에 두고 연관관계를 관리하는 조인 테이블 전략을 기본으로 사용해서 매핑한다.

```java
@Test
public void testSave() {
    // GIVEN
    transaction.begin();

    Member member1 = new Member("kim");
    Member member2 = new Member("lee");

    Team team = new Team("team01");
    team.getMembers().add(member1);
    team.getMembers().add(member2);

    manager.persist(member1); // INSERT member1
    manager.persist(member2); // INSERT member2
    manager.persist(team);  // INSERT team, UPDATE member1.fk, UPDATE member2.fk

    transaction.commit();

    // WHEN
    Team findTeam = manager.find(Team.class, team.getId());
    List<Member> members = findTeam.getMembers(); // 회원 객체 그래프 탐색

    // THEN
    assertThat(members.get(0), Matchers.<Member>hasProperty("username", is("kim")));
    assertThat(members.get(1), Matchers.<Member>hasProperty("username", is("lee")));

}
```

```console
Hibernate:
    call next value for hibernate_sequence
Hibernate:
    call next value for hibernate_sequence
Hibernate:
    call next value for hibernate_sequence
Hibernate:
    insert
    into
        member
        (username, member_id)
    values
        (?, ?)
Hibernate:
    insert
    into
        member
        (username, member_id)
    values
        (?, ?)
Hibernate:
    insert
    into
        team
        (name, team_id)
    values
        (?, ?)
Hibernate:
    update
        member
    set
        team_id=?
    where
        member_id=?
Hibernate:
    update
        member
    set
        team_id=?
    where
        member_id=?

```

일대다 단방향은 외래키가 다른 테이블에 존재하며 INSERT SQL을 수행하고 UPDATE SQL을 추가로
실행해야만 하는 단점이 있다. 위의 테스트 코드와 콘솔 출력결과를 보면 회원과 팀 INSERT SQL을
실행하고, 회원의 팀 외래키 칼럼을 추가하기 위해 UPDATE SQL이 실행된 것을 확인 할 수 있다.

일대다 단방향 매핑을 사용하면 엔티티 매핑한 테이블이 아닌 다른 테이블의 외래키를 관리해야 하기
때문에 성능과 관리에 부담이 될 수 있다. 이러한 문제를 해결하기 위해서는 일대다 단방향 매핑 대신
다대일 양방향 매핑을 사용하는 것이 좋다.

일대다 단방향 매핑보다는 다대일 양방향 매핑이 권장된다.

### 2.2 양방향

일대다(N:1) 양방향 매핑은 존재하지 않는다. 대신 다대일 양방향 매핑을 사용해야 한다. 그 이유는
`@OneToMany`는 연관관계의 주인이 될 수 없고 `@ManyToOne`에서 연관관계의 주인이 될 수 있기
때문이다.

하지만 일대다 양방향 매핑이 완전히 불가능한 것은 아니다. 아래와 같이 일대다 단방향 매핑
반대편에 같은 외래키를 사용하는 다대일 단방향 매핑을 읽기 전용으로 추가하면 된다.

```java
// 일대다 양방향
@Entity
public class Team {

    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long ld;

    private String name;

    @OneToMany
    @JoinColumn(name = "TEAM_ID")
    private List<Member> members = new ArrayList<Member>();

    // getter, setter
}
```

```java
// 일대다 양방향 매핑
@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String username;

    // 읽기 전용 설정
    @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false)
    private Team team;

    // getter, setter
}
```

위의 방법은 일대다(1:N) 양방향 매핑이기보다는 일대다(1:N) 단방향 매핑 반대쪽인 다대일(N:1) 매핑을
읽기 전용으로 추가해 일대다(1:N) 양방향처럼 보이도록 하는 방법이다. 일대다(1:N) 단방향의 단점을
그대로 가지고 있기 때문에 되도록이면 다대일(N:1) 양방향 매핑을 사용하는 것이 좋다.

## 3. 일대일(1:1)

일대일(1:1)의 관계는 서로 하나만의 관계를 가진다. 예를 들어 회원은 하나의 사물함만을 사용하고,
사물함도 하나의 회원에 의해서만 사용된다고 생각하면 이해가 쉽다.

일대일(1:1)의 관계의 특징
- 일대일 관계는 그 반대도 일대일 관계
- 일대일(1:1) 관계에서는 어느 곳이나 외래키를 가질 수 있음

테이블이 주 테이블이든 대상 테이블이든 외래키 하나만 있으면 양쪽으로 조회가 가능하기 때문에
어느 테이블에 외래키를 가질지 선택해야 한다.

- 주 테이블에 외래키
  - 주 객체가 대상 객체를 참조하는 것처럼 주 테이블의 외래키를 두고 대상 테이블을 참조
  - 외래키를 객체 참조와 비슷하게 사용할 수 있음
  - 주 테이블만 확인해도 대상 테이블과 연관관계가 있는지 알 수 있는 장점을 가짐

- 대상 테이블에 외래키
  - 테이블 관계를 일대일에서 일대다로 변경할 때 테이블 구조를 그대로 유지할 수 있는 장점을 가짐

### 3.1 주 테이블에 외래키

JPA에서 일대일(1:1) 매핑을 할 때 주 테이블에 외래키가 있으면 좀 더 편리하게 매핑이 가능하다.

#### 3.1.1 단방향

![one-to-one-one-way](http://www.plantuml.com/plantuml/png/VKzBIm9H5DxVNt6oZGCEqLQ6WHuZH8w1rbfcdADUd8VCN479GA9MkMWXMOjfLP0xAHSk-aNEzJyqu_MLH8VBVPplTIwZp4RWuNVKxyKxHAC13wSml-_pS19D1tpu2Fnb6hq-ps_VG8ReuICQZ-PFxp0RX_npajbGWG_7iqalMLztX5Hi8mX0HyT47wu9n2K3jI0B9UPJjxRij0BqNSF1tRxjLHgnC0lvnOMGcvMbQFnZMVXb5-kMGwGEKo3Bwk9SkUvPMAKk0ZDC6zDj2KH66nG5qfUP_LpkA2u9L7LDRVhKCVnEkO6THA2OQbivC3TurVEHrjmrtahmbahgr49maDKzAn2FWA6F7kiqCI14JA1h-h5Mb9Q_suuIIFdiL5f0sodvYvFpBUYgf1KBHxgMV5su2i9VhZlIbKo4GTUJPQyBWaH-0000)

```java
// 일대일 단방향 주 테이블에 외래키
@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String username;

    // 일대일 매핑
    @OneToOne
    @JoinColumn(name = "LOCKER_ID") // 외래키 제약 조건 설정
    private Locker locker;

    // getter, setter
}
```

```java
// 일대일 단방향 주 테이블에 외래키
@Entity
public class Locker {

    @Id
    @GeneratedValue
    @Column(name = "LOCKER_ID")
    private Long id;

    private String name;

    // 참조 없음

    // getter, setter
}
```

#### 3.1.2 양방향

![one-to-one-two-way](http://plantuml.com/plantuml/uml/VL1DQy905BplhtYr5mF6qgk4W6qZb1eBsfubCQikvaCs4PHQaDBUFBGWQW_fgJruazQ3X_uYi_w79cwC8jB7ikpEp9itF8zu9WRc_mR3GNX3C1inVmdhbo7p5y5on2QlmDwNmSTq_VG98h3nMp2VhSTVi9hxx7kHsKc1JUQhnI0wFyy8rKpDTK75beufF208ImHYG0ug7YLs8qOwBgQsPk53t7HghR0n1yND0w77hIK7_h4i7R6pU8vS72XoYwE9eWofyrGo9uOZICfc4_Z4m7LYO_0qtSJfhW0yJ1Sa2T9tcTD28H-M0BASIDkKM1hjLLku5pL8kjoLplGTNtSe9WqxuJiHtv5HanWu3Emr7SFb7zV353jUhurTX7W2LL4lbB8Ghs5_56UgrrV2XjgVKhozlDcZxYjAkPHNbMWRt9KBZhaUj6xR-33eEwBez86Bq1y0)

```java
// 일대일 양방향 주 테이블에 외래키
@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;
    private String username;

    // 일대일 매핑
    @OneToOne
    @JoinColumn(name = "LOCKER_ID") // 외래키 제약 조건 설정
    private Locker locker;  // 연관관계의 주인

    // getter, setter
}
```

```java
// 일대일 양방향 주 테이블에 외래키
@Entity
public class Locker {

    @Id
    @GeneratedValue
    @Column(name = "LOCKER_ID")
    private Long id;

    private String name;

    // 일대일 매핑
    @OneToOne(mappedBy = "locker") // 연관관계 주인 지정 --> Member.locker
    private Member member;

    // getter, setter
}
```


### 3.2 대상 테이블에 외래키

#### 3.2.1 단방향

일대일 관계 중에서 대상 테이블에 외래키가 있는 단방향 관계는 JPA에서 지원하지 않는다.

#### 3.2.2 양방향

![one-to-one-two-way-target](http://plantuml.com/plantuml/uml/VL3BJi905DtFLrnnGnEASKkQ9gWbCL9CGDUafGDCw8DCXmGY9lZQkT34W2vmfGjtH5cmy8je-GSxJ2c446ycypZdt7DFXX5cOmZ7ly7Z8Dg17tSti7Xu3CVJOFOIZfuWV9i5xw-Bsm-G8Hm-1vElnV0JvfDn-3tDhAKGZYRpwO2ldtk4QhRX-w1ZnyGKhX149GEn80SLHedRY96EZwbhE7WRjxrQAshCGN5vGUXwPIc0VonBE-mSaICN1-9kSJHPLY5rc4hcn327b6msWVSiN2Sk1cQODavt9H1Xkg0eaBx8x1SA-QWaKDL4sgR4CMYlsi8ztg2OQbSvCDTytQEODDo4xt2-ew8ciN0Kc3KzonSFds6AFTPhOnyXaK3Nz2EjBCNVi3bACDNJ4sb9RKufdX-VRL2N5QrSokiQ_mtXAWIxNBTQLsrY6FGzMVRw84Je3m00)

```java

```

## 4. 다대다

### 4.1 단방향

### 4.2 양방향

### 4.3 연결 엔티티

### 4.3 새로운 기본키 사용

## 5. Summery / Conclusion
