package com.doubles.jpastudy.identify.mapping.embeddedid;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class GrandChildId implements Serializable {

    private ChildId childId; // @MapsId("childId)로 매핑

    @Column(name = "GRANDCHILD_ID")
    private String id;

    // equals(), hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GrandChildId that = (GrandChildId) o;

        if (!childId.equals(that.childId)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        int result = childId.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }
}
