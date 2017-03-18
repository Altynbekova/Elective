package com.epam.altynbekova.elective.entity;

public class Lecturer extends User{
    private String jobTitle;

    public Lecturer() { }

    public Lecturer(Integer id, String firstName, String lastName, String login, String password,
                    String jobTitle) {
        super(id, firstName, lastName, login, password, Role.LECTURER);
        this.jobTitle = jobTitle;
    }

    public Lecturer(String firstName, String lastName, String login, String password, String jobTitle) {
        super(firstName, lastName, login, password, Role.LECTURER);
        this.jobTitle = jobTitle;
    }

    public Lecturer(Integer id) {
        super(id);
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

}
