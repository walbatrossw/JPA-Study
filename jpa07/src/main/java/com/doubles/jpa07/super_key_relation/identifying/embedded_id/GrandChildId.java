package com.doubles.jpa07.super_key_relation.identifying.embedded_id;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

// 복합키 : @EmbeddedId
@Embeddable
public class GrandChildId implements Serializable {

    private ChildId childId;

    @Column(name = "GRANDCHILD_ID")
    private String id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GrandChildId that = (GrandChildId) o;

        if (childId != null ? !childId.equals(that.childId) : that.childId != null) return false;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        int result = childId != null ? childId.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
