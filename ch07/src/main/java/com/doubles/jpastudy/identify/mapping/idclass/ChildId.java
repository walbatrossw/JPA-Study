package com.doubles.jpastudy.identify.mapping.idclass;

import java.io.Serializable;

// 자식Id
public class ChildId implements Serializable {

    private String parent;  // Child.parent 매핑
    private String childId; // Child.childId 매핑

    public ChildId() {
    }

    public ChildId(String parent, String childId) {
        this.parent = parent;
        this.childId = childId;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChildId childId1 = (ChildId) o;

        if (!parent.equals(childId1.parent)) return false;
        return childId.equals(childId1.childId);
    }

    @Override
    public int hashCode() {
        int result = parent.hashCode();
        result = 31 * result + childId.hashCode();
        return result;
    }
}
