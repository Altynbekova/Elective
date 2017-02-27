package com.epam.altynbekova.elective.entity;

import java.util.Comparator;

public class Course extends BaseEntity{
    private String name;
    private String description;
    private boolean isAvailable;
    private Lecturer lecturer;

    public Course() {
    }

    public Course(Integer id, String name, String description, boolean isAvailable, Lecturer lecturer) {
        super(id);
        this.name = name;
        this.description = description;
        this.isAvailable=isAvailable;
        this.lecturer = lecturer;
    }

    public Course(int id) {
        super(id);
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public int compareTo (Course course){
        int result=this.getName().compareToIgnoreCase(course.getName());
        if(result==0)result=Integer.compare(this.getId(),course.getId());
        return result;
    }
}
