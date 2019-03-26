package com.doubles.jpa09.embedded_type_attribute_override;

import javax.persistence.Embeddable;

@Embeddable
public class Zipcode {

    private String zip;
    private String plusFour;
}
