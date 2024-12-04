package com.vinay.radiant.service.serviceimpl;

import com.vinay.radiant.Exception.CommentNotFoundException;
import com.vinay.radiant.Exception.PostNotFoundException;
import com.vinay.radiant.Exception.UserNotFoundException;
import com.vinay.radiant.dto.CommentDto;
import com.vinay.radiant.dto.PostDto;
import com.vinay.radiant.dto.UserDto;
import com.vinay.radiant.model.Comment;
import com.vinay.radiant.model.Post;
import com.vinay.radiant.model.User;
import com.vinay.radiant.repository.CommentRepository;
import com.vinay.radiant.repository.PostRepository;
import com.vinay.radiant.service.CommentService;
import com.vinay.radiant.service.PostService;
import com.vinay.radiant.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer userId, Integer postId) throws UserNotFoundException, PostNotFoundException {
        UserDto userDto = userService.findUserById(userId);
        User user = modelMapper.map(userDto, User.class);
        PostDto postDto = postService.findPostById(postId);
        Post post = modelMapper.map(postDto, Post.class);
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setUser(user);
        comment.setCreatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);
        post.getComments().add(savedComment);
        postRepository.save(post);

        return modelMapper.map(modelMapper.map(comment,CommentDto.class), CommentDto.class);
    }

    @Override
    public CommentDto findCommentById(Integer commentId) throws CommentNotFoundException {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new CommentNotFoundException("Comment Not Found With CommentId : " + commentId)
        );
        return modelMapper.map(comment, CommentDto.class);
    }

    @Override
    public CommentDto likeComment(Integer commentId, Integer userId) throws UserNotFoundException, CommentNotFoundException {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()->new CommentNotFoundException("Comment Not Found With CommentId : " + commentId)
        );
        UserDto userDto = userService.findUserById(userId);
        User user = modelMapper.map(userDto, User.class);
        if(comment.getLiked().contains(user)){
            comment.getLiked().remove(user);
        }else{
            comment.getLiked().add(user);
        }
        Comment savedComment = commentRepository.save(comment);
        return modelMapper.map(savedComment, CommentDto.class);
    }
}
