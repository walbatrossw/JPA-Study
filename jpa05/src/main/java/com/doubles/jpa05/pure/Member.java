package com.doubles.jpa05.pure;

public class Member {

    private String id;
    private String username;

    private Team team;

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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "member - id : " + id + ", name : " + username + ", team : " + team;
    }
}
