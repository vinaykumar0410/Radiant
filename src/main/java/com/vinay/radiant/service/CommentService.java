package com.vinay.radiant.service;

import com.vinay.radiant.Exception.CommentNotFoundException;
import com.vinay.radiant.Exception.PostNotFoundException;
import com.vinay.radiant.Exception.UserNotFoundException;
import com.vinay.radiant.dto.CommentDto;

public interface CommentService {

    public CommentDto createComment(
            CommentDto commentDto,
            Integer userId,
            Integer postId
    ) throws UserNotFoundException, PostNotFoundException;

    public CommentDto findCommentById(
            Integer commentId
    ) throws CommentNotFoundException;

    public CommentDto likeComment(
            Integer commentId,
            Integer userId
    ) throws UserNotFoundException, CommentNotFoundException;

}
