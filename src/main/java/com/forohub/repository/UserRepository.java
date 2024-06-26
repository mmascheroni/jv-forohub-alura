package com.forohub.repository;

import com.forohub.entity.UserAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserAuthor, Long> {
    UserDetails findByEmail(String email);
}
