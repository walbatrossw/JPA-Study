package com.doubles.jpa07.inheritance_mapping.single_table;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

//@Entity
@DiscriminatorValue("A") // 지정하지 않으면 기본값으로 엔티티 이름이 지정
public class Album extends Item {

    private String artist;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
