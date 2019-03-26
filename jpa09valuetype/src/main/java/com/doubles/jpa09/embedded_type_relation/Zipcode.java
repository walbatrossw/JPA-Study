package com.doubles.jpa09.embedded_type_relation;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class Zipcode {

    private String zip;
    private String plusFour;
}
