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

## 2. 일대다(1:N)

### 2.1 단방향

### 2.2 양방향

## 3. 일대일(1:1)

### 3.1 주 테이블에 외래키

### 3.2 대상 테이블에 외래키

## 4. 다대다

### 4.1 단방향

### 4.2 양방향

### 4.3 연결 엔티티

### 4.3 새로운 기본키 사용

## 5. Summery / Conclusion
