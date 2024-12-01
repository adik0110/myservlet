package com.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Submission {
    private int id;
    private int assignmentId; // все инты переделать в названия
    private int studentId;
    private String filePath;
    private String comment;
    private Timestamp submissionDate;
}