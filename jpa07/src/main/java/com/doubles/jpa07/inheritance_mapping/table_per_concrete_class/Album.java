package com.doubles.jpa07.inheritance_mapping.table_per_concrete_class;

import javax.persistence.Entity;

@Entity
public class Album extends Item {

    private String artist;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
