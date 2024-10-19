package com.example.models;

public class Course {
    private int id;
    private String title;
    private String description;
    private String subjectName;
    private String teacherName;

    public Course(int id, String title, String description, String subjectName, String teacherName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.subjectName = subjectName;
        this.teacherName = teacherName;
    }

    public Course() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubjectName() {return subjectName;}

    public void setSubjectName(String subjectName) {this.subjectName = subjectName;}

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
