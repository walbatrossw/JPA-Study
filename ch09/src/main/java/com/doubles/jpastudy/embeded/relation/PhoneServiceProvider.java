package com.doubles.jpastudy.embeded.relation;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PhoneServiceProvider {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
