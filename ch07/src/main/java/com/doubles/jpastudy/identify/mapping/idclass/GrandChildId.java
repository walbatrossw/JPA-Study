package com.doubles.jpastudy.identify.mapping.idclass;

import java.io.Serializable;

public class GrandChildId implements Serializable {

    private ChildId child;  // GrandChild.child 매핑
    private String id;      // GrandChild.id 매핑

    public GrandChildId() {
    }

    public GrandChildId(ChildId child, String id) {
        this.child = child;
        this.id = id;
    }

    public ChildId getChild() {
        return child;
    }

    public void setChild(ChildId child) {
        this.child = child;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GrandChildId that = (GrandChildId) o;

        if (!child.equals(that.child)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        int result = child.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }
}
