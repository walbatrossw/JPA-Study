package com.doubles.jpastudy.identify.mapping.embeddedid;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ChildId implements Serializable{

    private String parentId; // @MapsId("parentId")로 매핑

    @Column(name = "CHILD_ID")
    private String id;


    // equals(), hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChildId childId = (ChildId) o;

        if (!parentId.equals(childId.parentId)) return false;
        return id.equals(childId.id);
    }

    @Override
    public int hashCode() {
        int result = parentId.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }
}
