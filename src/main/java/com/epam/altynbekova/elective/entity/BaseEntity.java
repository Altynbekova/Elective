package com.epam.altynbekova.elective.entity;

public class BaseEntity implements Comparable<BaseEntity> {
    private Integer id;

    public BaseEntity() {
    }

    public BaseEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int compareTo(BaseEntity baseEntity) {
        return Integer.compare(this.getId(), baseEntity.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseEntity baseEntity = (BaseEntity) o;

        return this.getId().equals(baseEntity.getId());

    }

    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }

}
