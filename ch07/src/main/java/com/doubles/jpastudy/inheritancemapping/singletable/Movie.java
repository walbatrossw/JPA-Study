package com.doubles.jpastudy.inheritancemapping.singletable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

//@Entity
@DiscriminatorValue("M")
public class Movie extends Item {
}
