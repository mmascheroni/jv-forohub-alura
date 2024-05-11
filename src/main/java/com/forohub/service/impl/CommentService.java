package com.forohub.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forohub.dto.CommentDto;
import com.forohub.entity.Comment;
import com.forohub.exceptions.ResourceNotFoundException;
import com.forohub.repository.CommentRepository;
import com.forohub.service.ICommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentService implements ICommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<CommentDto> getComments() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentDto> commentsDto = comments.stream().map(comment -> objectMapper.convertValue(comment, CommentDto.class)).collect(Collectors.toList());
        if ( commentsDto.size() > 0 ) log.info("List of Comments found {}", commentsDto);
        log.warn("List of comments is empty");
        return commentsDto;
    }

    @Override
    public CommentDto getCommentById(Long id) throws ResourceNotFoundException {
        Optional<Comment> comment = commentRepository.findById(id);
        CommentDto commentDto;

        if (comment != null) {
            commentDto = objectMapper.convertValue(comment, CommentDto.class);
            log.info("Comment with id {} was found: {}", id, commentDto);
            return commentDto;
        }

        log.warn("Not found comment with id {}", id);
        throw new ResourceNotFoundException("Not found the comment with id " + id);
    }

    @Override
    public CommentDto postComment(Comment comment) {
        Comment commentSave = commentRepository.save(comment);
        CommentDto commentDto = objectMapper.convertValue(commentSave, CommentDto.class);
        log.info("Comment save successfully -> {}", commentDto);
        return commentDto;
    }

    @Override
    public CommentDto updateComment(Comment comment) {
        return null;
    }

    @Override
    public String deleteComment(Long id) throws ResourceNotFoundException {
        if ( getCommentById(id) != null ) {
            commentRepository.deleteById(id);
            log.warn("Comment with id {} was delete successfully", id);
        }

        return "Comment was delete successfully";
    }
}
