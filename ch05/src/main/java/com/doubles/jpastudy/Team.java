package com.doubles.jpastudy;

public class Team {
    private String id;
    private String username;

    // 기본 생성자
    public Team() {
    }

    // 생성자
    public Team(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
