package com.finalwork.po;

public class Course {
    private String course_id;
    private String name;
    private Integer week;

    public Course() {}

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{" +
                "course_id:'" + course_id + '\'' +
                ", name:'" + name + '\'' +
                '}';
    }
}
