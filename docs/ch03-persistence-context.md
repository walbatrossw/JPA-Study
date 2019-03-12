# 영속성 관리

## 1. 영속성 컨텍스트 기본 개념

**영속성 컨텍스트(Persistence Context)란 "엔티티를 영구 저장하는 환경"이라는 뜻이다.** 엔티티 매니저로 엔티티를 저장하거나 조회하면 엔티티 매니저는 영속성 컨텍스트에서 엔티티를 보관하고 관리한다.

`persist()`메서드는 엔티티 매니저를 사용해서 회원 엔티티를 영속성 컨텍스트에
저장한다.

## 3. 엔티티 생명주기

엔티티에는 4가지 상태가 존재하며 생명주기는 아래의 그림과 같다.

![entity-lifetime]()

- 비영속(new/transient) : 영속성 컨텍스트와 관계가 없는 상태
- 영속(managed) : 영속성 컨텍스트에 저장된 상태
- 준영속(detached) : 영속성 컨텍스트에 저장되었다가 분리된 상태
- 삭제(removed) : 삭제된 상태

### 2.1 비영속

엔티티 객체를 생성하여 순수한 객체의 상태이며 저장하지 않은 상태를 말한다.
따라서 영속성 컨텍스트나 데이터베이스와 관련이 없다.

```java
// 객체 생성 : 비영속
Member member = new Member();
member.setId("id001");
member.setUsername("doubles");
```

### 2.2 영속

앤티티 매니저를 통해 앤티티를 영속성 컨텍스트에 저장한 상태를 말한다. 영속성 컨텍스트가 관리하는 엔티티를 영속상태라고 한다.

```java
// 저장 : 영속
entityManager.persist(member);
// 한건 조회 : 영속
entityManager.find("id001");
// 목록 조회 : 영속
List<Member> members = entityManager.createQuery("SELECT m FROM Member m", Member.class).getResultList();
```

### 2.3 준영속

영속성 컨텍스트가 관리하던 영속상태의 엔티티를 영속성 컨텍스트가 관리하지 않으면
준영속 상태가 된다. 특정 엔티티를 준영속 상태로 만드려면 아래의 메서드들을 호출하면 된다.
- `detach()`
- `close()`
- `clear()`

### 2.4 삭제

엔티티를 영속성 컨텍스트와 데이터베이스에서 삭제한다.

```java
entityManager.remove();
```

## 4. 영속성 컨텍스트 특징

## 5. 플러시

## 6. 준영속
