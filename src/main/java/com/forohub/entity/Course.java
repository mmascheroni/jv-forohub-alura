package com.forohub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "courses")
@NoArgsConstructor
@Getter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The name cannot be empty")
    private String name;

    public Course(String name) {
        this.name = name;
    }

    public void setNombre(String name) {
        this.name = name;
    }
}