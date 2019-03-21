package com.doubles.jpa07.inheritance_mapping.join;

import javax.persistence.*;

//@Entity
@DiscriminatorValue("A") // 엔티티를 저장할 때 구분 칼럼 값 지정
public class Album extends Item {

    private String artist;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
