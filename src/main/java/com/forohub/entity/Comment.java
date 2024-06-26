package com.forohub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The title cannot be empty")
    private String title;

    @NotBlank(message = "The message cannot be empty")
    private String message;

    private LocalDate dateCreation;

    private boolean status;

    @ManyToOne
    @JoinColumn(name = "author_comment_id")
    @NotNull(message = "The author field is required")
    private UserAuthor author;

    @ManyToOne
    @JoinColumn(name = "course_comment_id")
    @NotNull(message = "The course field is required")
    private Course course;

    public Comment(String title, String message, LocalDate dateCreation, boolean status, UserAuthor author, Course course) {
        this.title = title;
        this.message = message;
        this.dateCreation = dateCreation;
        this.status = status;
        this.author = author;
        this.course = course;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setAuthor(UserAuthor author) {
        this.author = author;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
