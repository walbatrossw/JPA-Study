# 다양한 연관관계 매핑

## 1. 연관관계를 매핑할 때 고려해야할 사항

여러가지 연관관계들에 대해 정리하기에 앞서 이전에 정리했던 내용들을 복습해보자.

### 1.1 다중성

- 다대일(`@ManyToOne`)
- 일대다(`@OneToMany`)
- 일대일(`@OneToOne`)
- 다대다(`@ManyToMany`)

### 1.2 단방향, 양방향

- 테이블의 경우 외래키 하나로 조인을 사용해 양방향으로 쿼리가 가능하므로 방향이라는 개념이 없다.
- 객체는 참조용 필드를 가진 객체만 연관된 객체를 조회할 수 있다.
- 객체관계에서 한 쪽만 참조하는 것을 단방향 양쪽을 참조하는 것을 양방향 관계라 한다.

### 1.3 연관관계의 주인

- DB는 외래키 하나로 두 테이블이 연관관계를 맺기 떄문에 테이블의 연관관계를 관리하는 포인트는 외래키 하나다.
- 엔티티를 양방향으로 매핑하면 2곳에서 서로를 참조하기 때문에 연관관계를 관리하는 포인트는 2곳이다.
- 두 객체의 연관관계 중 하나를 정해 DB의 외래키를 관리하는데 이것을 연관관계의 주인이라 한다.
- 외래키를 가진 테이블과 매핑한 엔티티가 외래키를 관리하는 것이 효율적이다.
- 주인이 아닌방향은 외래키를 변경할수 없고, 읽기만 가능하다.
- 연관관계의 주인은 `mappedBy`속성을 사용하지 않는다.
- 연관관계의 주인이 아닐 경우 `mappedBy`속성을 사용하고 연관관계의 주인 필드 이름을 값으로 입력한다.


## 2. 다대일(N:1)

다대일(N:1) 관계의 반대방향은 항상 일대다(1:N)이며, DB 테이블의 외래키는 항상 다(N) 쪽에 있기때문에 연관관계의 주인은
항상 다(N)이다.

### 2.1 단방향 [N:1]

![n-to-1-oneway](http://www.plantuml.com/plantuml/png/TL4_Jy905D_lKpphGer5S3HD4umbCR4CW3DfxGCQQKkkHm9H4WQTT37npyI6KJUY30v-8bg-WtTSgHZnqgJltk_VkppciGv2V3E9RqV9-8jNh_7qRV7uJCXPnuuYiD1taC850Nuqy5muW9CmQALtXhRFEtN-Ipkz26bW-yYxDKOzpXokl0JhNwS_cZHPsL50qmnGYegC8LikDhq0WTbE1tDz5QHx7tGTSlNTxNAvn8yAXf5HkzJpRJfedED027J7wEjxpW_U32bwhI33UmBl6QJjkGW-idReHl8ICQGOia4N8qBa19Pf7PfLDNtPUfH46iT7wX9QJwcR9Ii3JcjcjLAoJF5iwIdWJOw_P2k9Z85Xg6dX9GWA8K7849JaQJgVZSJtSGNnosJnSAU07K6ba1Bcqt7oFeDrdYGKKyBY-ZOPp-BF-msS_Mn9cd2i583lIGwJ8NbXjLpyibhDcXTgCQcilW40)

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

### 2.2 양방향 [N:1, 1:N]

![n-to-1-twoway](http://www.plantuml.com/plantuml/png/TL8nJy906DtlLtpM1Gojr94qJJ2MnCHs09oC8Qqze96sf7Sa42LXq4aN4iM93MFSY38u-8je-G_sUct512-NzBxllU-zbxjIX_OmX0-By74IpRyXcat3vVjczdfXLEGIo1NZ4g6hdaa8wDYrS00t2E8bWcD31SvyhvFM0u83ptHnt6tGmCdx59jkt6l67uJ6cHWh_vKoTiYuYJjXH8TG9fJc4JBa00G0HPAuLOgAeWf24a4MqWm8xTcuxNWOg6drS65O19vf28e2XMPflrQhngi8gffJ-u7Zci6eTOr7R42nrA5oQFtYRJ_0JiVByG731ohgEZQEizEkRnDU48e3xDDH7nE4U09Tqu-rUZ6zZsqhZhHEJue9jEtIrAfw3fmtjBfHrJLsBroJmRiK_unb8zm6nhue-hV0A0XvFiKWH2_BzMh2zkSTX6-BpVDK09E0KKu9w-Ky-bZ1DeyJv9ImkN-Cvglmwsa7voX_HP4f6cM8duk7oH69IIM_ItBAiaficYNbfny0)

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

![one-to-one-two-way-target](http://www.plantuml.com/plantuml/png/VP3BJi9058RtynH7tD24OjmsJHFKaXWf9g1hqj819lH2fYM1Y0dUTYuqCQ0BNEd27L4MB7mYEho37QPSGeYJPgPpldF-_y-497GmiD5lzDYFT-37tGtC7nxPQ19DNzZm2TZRD7f_dTz-W0niy1oDl-Q3JvYDH-nxabct0XkEPvC-_txk4QewPX20WLqBKxX242yPY0qAb4DAl7fIQGUOUgQBj-kENst6WmeK5Zy8NIybHU4VoU8EELVaK990N2s9birga3fCBMMLn7d5ELDbUSs5549xDguH3qDeMWvEToGGyJgWgf2-oEpdyxbuIQ1fgzOM9Qv9kvKcxl81rT8wwe6rvZMVObBtLhpDULj33MBZs3Ti-7OWBa68AVR3RWi728a4XcuSwIKfUPXDAq4gfoVI0csw5CwFppRGPLal5NE6pjz7g8g6NQfReyinOGOzNvRz7eWcz0S0)

대상 테이블에 외래키가 있는 양방향 관계는 아래의 코드와 같이 작성할 수 있다. 일대일 매핑에서 대상 테이블에
외래키를 두고 양방향 매핑을 설정한다.

```java
// 일대일 양방향 대상테이블의 외래키
@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    // 일대일 매핑
    @OneToOne(mappedBy = "member")  // 연관관계 주인 지정
    private Locker locker;

    // getter, setter

}
```

```java
// 일대일 양방향 대상테이블의 외래키
@Entity
public class Locker {

    @Id
    @GeneratedValue
    @Column(name = "LOCKER_ID")
    private Long id;

    private String name;

    // 일대일 매핑
    @OneToOne
    @JoinColumn(name = "MEMBER_ID") // 외래키 지정
    private Member member;

    // getter, setter

}
```

## 4. 다대다(N:M)

관계형 데이터베이스에서는 정규화된 테이블 2개로 다대다 관계를 표현할 수 없다.

예를 들어 회원들이 상품을 주문 하고, 상품들은 회원에 의해 주문되어진다고 가정한다면 이 관계는 다대다 관계이다. 이러한 다대다 관계를 아래와 같이 표현할 수는 없다.

![n-to-n-table-unable](http://www.plantuml.com/plantuml/png/AyaioKbLUDlQysRkbjSxfhoPFk5Dono5rziwNcreUDsrzEtKEGf-LhuAhxkNlEvf099SN8efgMcPUQaA9Ob9EQaQ5PeAoJc9nSKA5GesDWeQ8JhARcwEGA2fO6S7LrfGb9cRM5GPdvrQ2T9WasHNOQEVX91FoozApKpFWykNWkHBxRWoCbDI5N9JIpBoKueXdEAIrABK_9BAWjHYBYw82tnTVPrSWpGLghaKW03PAeXClAUB9bWgDDWroC_HrvMh5gOr8CZV9TGv19jUg1HSCqmZn4o5sdnTtVng1QWgBW00)

보통 이러한 경우 다대다 관계를 일대다(1:N), 다대일(N:1) 관계로 풀어내는 연결 테이블을 사용한다.

![n-to-n-linked-table](http://www.plantuml.com/plantuml/png/AyaioKbLyBFoLNZTlUHrJmEGL7WpVSEhJHiKthRsl9cxvVMEALnSYYcfQPbvgGebYKavgHeLcWh9EOd5nGeL2ZOs2XeXEifkRev0eAbWPmTNMb2KcPjOL1cVdLe9qc2JP5TXev-4a4_BBqhDJC-3ovU2v4ljk38oKr8LSbDBCl9JYY6SufBKejJyaig2r68kBeWBV5rzdLo3D1MgkHI00DagY4oyfuicM2eqs3N8pz7NbQiMLMjyG92_IwXp215683iKJGfP5zH0hDgGvU0EmUC2TBDWFe1Ohr1JewkRNws0wXEOB8olK9klgrjJem2J1W00)

위와 같이 `MEMBER_PRODUCT` 연결테이블을 추가해서 다대다 관계를 풀어낼 수 있다. 이 연결 테이블은 회원이 주문한 상품을 나타낸다.

![n-to-n-entity](http://www.plantuml.com/plantuml/png/AyaioKbLUDszv7LF0v1KU3Dzojlk3K34nPMSarXShE2RcfkKMgHGpSLL2nCAACfFAKqkWOf15IXfAIdewe8fg3mN5m00)

하지만 객체의 경우는 테이블과 다르게 객체 2개로 다대다 관계를 나타낼 수 있다. 회원 객체는 컬렉션을 사용해
상품들을 참조하면 되고, 상품들도 컬렉션을 사용해 회원들을 참조하면 된다. `@ManyToMany`를 사용하면 이러한 다대다 관계를 편리하게 매핑할 수 있다.

### 4.1 단방향

다대다 단방향 관계는 아래와 같이 코드를 작성해주면 된다.

```java
// 다대다 단방향
// 회원 클래스
@Entity
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    private String id;

    private String username;

    @ManyToMany // 다대다 매핑
    @JoinTable(name = "MEMBER_PRODUCT", // 연결테이블 지정
            joinColumns = @JoinColumn(name = "MEMBER_ID"), // 현재 방향인 회원과 매핑할 조인 컬럼 정보 지정
            inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID")) // 반대 방향인 상품과 매핑할 조인 컬럼 정보 지정
    private List<Product> products = new ArrayList<Product>();

    // constructor, getter, setter
}
```

```java
// 다대다 단방향
// 상품 클래스
@Entity
public class Product {

    @Id
    @Column(name = "PRODUCT_ID")
    private String id;

    private String name;

    // constructor, getter, setter
}
```

회원 엔티티와 상품 엔티티를 `@ManyToMany`와 `@JoinTable`을 사용해서 연결테이블을 바로 매핑하여,
회원과 상품을 연결하는 엔티티 없이 매핑을 완료할 수 있다.

아래는 `@JoinTable`의 속성에 대한 설명이다.

- `@JoinTable.name` : 연결테이블(`MEMBER_PRODUCT`) 지정
- `@JoinTable.joinColumns` : 현재 방향인 회원과 매핑할 조인 컬럼(`MEMBER_ID`) 정보를 지정
- `@JoinTable.inverseJoinColumns` : 반대 방향인 상품과 매핑할 조인 칼럼(`PRODUCT_ID`) 정보 지정

다대다를 저장하는 코드는 아래와 같다.

```java
@Test
public void testSave() {
    // GIVEN
    transaction.begin();
    Product productA = new Product("productA", "상품A"); // 상품 생성, 저장
    manager.persist(productA);

    Member member1 = new Member("m01", "kim");  // 회원 생성, 저장
    member1.getProducts().add(productA); // 연관관계 설정
    manager.persist(member1);
    transaction.commit();

    // WHEN
    Member findMember = manager.find(Member.class, member1.getId());
    List<Product> products = findMember.getProducts(); // 상품 그래프 탐색

    // THEN
    assertEquals(member1, findMember);
    assertEquals(productA.getId(), products.get(0).getId());
}
```

콘솔화면을 보면 아래와 같이 회원과 상품의 연관관계를 설정했기 때문에 회원을 저장할 때 연결 테이블에도 값이
저장되는 것을 확인할 수 있다.

```console
Hibernate:
    insert
    into
        product
        (name, product_id)
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
        member_product
        (member_id, product_id)
    values
        (?, ?)
```

상품을 탐색하는 테스트 코드를 작성하면 아래와 같다.

```java
@Test
public void testFind() {
    // GIVEN

    // WHEN
    Member member = manager.find(Member.class, "m01");
    List<Product> products = member.getProducts();

    // THEN
    assertEquals("m01", member.getId());
    assertEquals("productA", products.get(0).getId());
}
```

회원을 조회하고, 상품을 조회하면 아래와 같이 `MEMBER_PRODUCT`테이블과 상품 테이블을 조인해서 연관된
상품을 조회하는 SQL이 실행된다.

```console
Hibernate:
    select
        member0_.member_id as member_i1_0_0_,
        member0_.username as username2_0_0_
    from
        member member0_
    where
        member0_.member_id=?
Hibernate:
    select
        products0_.member_id as member_i1_0_0_,
        products0_.product_id as product_2_1_0_,
        product1_.product_id as product_1_2_1_,
        product1_.name as name2_2_1_
    from
        member_product products0_
    inner join
        product product1_
            on products0_.product_id=product1_.product_id
    where
        products0_.member_id=?
```

### 4.2 양방향

다대다 매핑을 양방향으로 설정하기 위해서는 역방향에서도 `@ManyToOne`을 사용한다. 그리고 양쪽에서 원하는
곳에 `mappedBy`로 연관관계의 주인을 지정한다.

```java
// 다대다 양방향
// 상품 클래스
@Entity
public class Product {

    @Id
    private String id;

    private String name;

    @ManyToMany(mappedBy = "products") // 역방향 추가
    private List<Member> members = new ArrayList<Member>();

    // constructor, getter, setter
}
```

### 4.3 연결 엔티티

### 4.3 새로운 기본키 사용

## 5. Summery / Conclusion
