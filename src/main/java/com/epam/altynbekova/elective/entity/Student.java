package com.epam.altynbekova.elective.entity;

import java.util.*;

public class Student extends User {
    private Map<Course, Completion> courses;

    public Student() {
    }

    public Student(Integer id, String firstName, String lastName, String login, String password,
                   Map<Course, Completion> courses) {
        super(id, firstName, lastName, login, password, Role.STUDENT);
        this.courses = courses;
    }

    public Student(String firstName, String lastName, String login, String password) {
        super(firstName, lastName, login, password, Role.STUDENT);
    }

    public Map<Course, Completion> getCourses() {
        return courses;
    }

    public void setCourses(Map<Course, Completion> courses) {
        this.courses = new HashMap<>(courses);
    }
}
