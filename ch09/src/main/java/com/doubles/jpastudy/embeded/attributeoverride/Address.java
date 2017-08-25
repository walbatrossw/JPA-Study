package com.doubles.jpastudy.embeded.attributeoverride;

import javax.persistence.Embeddable;


@Embeddable
public class Address {

    private String city;
    private String street;
    private String zipcode;

}
