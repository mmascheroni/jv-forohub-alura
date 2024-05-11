package com.forohub.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
public class UserAuthor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotBlank(message = "The name cannot be empty")
    private String name;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "The email cannot be empty")
    @Email(message = "Please enter a correct email")
    private String email;

    @NotBlank(message = "The password cannot be empty")
    @Size(min = 6, max = 150, message = "The password must have a minimum of 6 characters and maximum of 50")
    private String password;

    @NotNull(message = "The role field is required")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "author")
    @JsonIgnore
    private List<Comment> comments;

    public UserAuthor(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
