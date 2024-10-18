package com.example.models;

import java.util.Date;

public class Comment {
    private int id;
    private int submissionId;
    private int teacherId;
    private String commentText;
    private Date commentDate;

    public Comment(int id, int submissionId, int teacherId, String commentText, Date commentDate) {
        this.id = id;
        this.submissionId = submissionId;
        this.teacherId = teacherId;
        this.commentText = commentText;
        this.commentDate = commentDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(int submissionId) {
        this.submissionId = submissionId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }
}
