package com.forohub.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.forohub.entity.Course;
import com.forohub.entity.UserAuthor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {


    private Long id;

    private String title;

    private String message;

    private LocalDate dateCreation;

    private boolean status;

    private UserDto author;

    private Course course;

    @Override
    public String toString() {
        return "CommentDto {" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", dateCreation=" + dateCreation +
                ", status=" + status +
                ", author=" + author +
                ", course=" + course +
                '}';
    }
}
