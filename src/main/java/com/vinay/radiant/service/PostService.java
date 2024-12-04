package com.vinay.radiant.service;

import com.vinay.radiant.Exception.PostNotFoundException;
import com.vinay.radiant.Exception.UserNotFoundException;
import com.vinay.radiant.dto.PostDto;

import java.util.List;

public interface PostService {

    public PostDto createPost(PostDto post, Integer userId) throws UserNotFoundException;

    public PostDto updatePost(Integer postId, Integer userId, PostDto post) throws PostNotFoundException, UserNotFoundException;

    public PostDto deletePost(Integer postId, Integer userId) throws UserNotFoundException, PostNotFoundException;

    public List<PostDto> findPostByUserId(Integer userId);

    public PostDto findPostById(Integer postId) throws PostNotFoundException;

    public List<PostDto> findAllPosts();

    public PostDto savePost(Integer postId, Integer userId) throws PostNotFoundException, UserNotFoundException;

    public PostDto likePost(Integer postId, Integer userId) throws PostNotFoundException, UserNotFoundException;

}
