package com.forohub.controller;

import com.forohub.dto.CommentDto;
import com.forohub.entity.Comment;
import com.forohub.exceptions.ResourceNotFoundException;
import com.forohub.service.impl.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @GetMapping
    public ResponseEntity<List<CommentDto>> getComments() {
        return ResponseEntity.ok(commentService.getComments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(commentService.getCommentById(id));
    }

    @PostMapping
    public ResponseEntity<CommentDto> postComment(@Valid @RequestBody Comment comment) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.postComment(comment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(commentService.deleteComment(id));
    }
}
