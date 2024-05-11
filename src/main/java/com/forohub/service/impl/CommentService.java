package com.forohub.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forohub.dto.CommentDto;
import com.forohub.dto.CourseDto;
import com.forohub.dto.UserDto;
import com.forohub.entity.Comment;
import com.forohub.entity.Course;
import com.forohub.entity.UserAuthor;
import com.forohub.exceptions.ResourceNotFoundException;
import com.forohub.repository.CommentRepository;
import com.forohub.repository.CourseRepository;
import com.forohub.repository.UserRepository;
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
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<CommentDto> getComments() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentDto> commentsDto = comments.stream().map(comment -> objectMapper.convertValue(comment, CommentDto.class)).collect(Collectors.toList());
        if ( commentsDto.size() > 0 ) log.info("List of Comments found {}", commentsDto);
        else log.warn("List of comments is empty");
        return commentsDto;
    }

    @Override
    public CommentDto getCommentById(Long id) throws ResourceNotFoundException {
        Optional<Comment> comment = commentRepository.findById(id);
        CommentDto commentDto;

        if (comment.isPresent()) {
            commentDto = objectMapper.convertValue(comment, CommentDto.class);
            log.info("Comment with id {} was found: {}", id, commentDto);
            return commentDto;
        }

        log.warn("Not found comment with id {}", id);
        throw new ResourceNotFoundException("Not found the comment with id " + id);
    }

    @Override
    public CommentDto postComment(Comment comment) throws ResourceNotFoundException {
        Long idCourse = comment.getCourse().getId();
        Long idUser = comment.getAuthor().getId();
        Course course = courseRepository.findById(idCourse).orElse(null);
        UserAuthor userAuthor = userRepository.findById(idUser).orElse(null);

        if ( course == null && userAuthor == null) {
            log.error("The course with {] and the author with {} not exists", idCourse, idUser);
            throw new ResourceNotFoundException("The course with id '" + idCourse + "' and the author with id '" + idUser + "' not exists");
        }
        else if (course == null) {
            throw new ResourceNotFoundException("The course with id '" + idCourse + "' not exist");
        }
        else if (userAuthor == null) {
            throw new ResourceNotFoundException("The user with id '" + idUser + "' not exist");
        }

        Comment commentSave = commentRepository.save(comment);
        CommentDto commentDto = objectMapper.convertValue(commentSave, CommentDto.class);
        if ( course != null ) commentDto.setCourse(course);
        if ( userAuthor != null ) commentDto.setAuthor(userAuthor);
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
