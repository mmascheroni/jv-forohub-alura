package com.forohub.service;

import com.forohub.dto.CommentDto;
import com.forohub.entity.Comment;

import java.util.List;

public interface ICommentService {

    List<CommentDto> getComments();

    CommentDto getCommentById(Long id);

    CommentDto postComment(Comment comment);

    CommentDto updateComment(Comment comment);

    String deleteComment(Long id);
}
