package com.doubles.jpa07.super_key_relation.identifying.embedded_id;

import javax.persistence.*;

// 복합키 : @EmbeddedId
//@Entity
public class GrandChild {

    @EmbeddedId
    private GrandChildId grandChildId;

    @MapsId("childId")
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "PARENT_ID"),
            @JoinColumn(name = "CHILD_ID")
    })
    private Child child;

    private String name;

}
