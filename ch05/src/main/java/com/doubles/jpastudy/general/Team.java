package com.doubles.jpastudy.general;

public class Team {

    private String id;
    private String name;

    // 기본생성자
    public Team() {
    }

    // 생성자 : 팀아이디, 팀이름
    public Team(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
