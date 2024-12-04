package com.vinay.radiant.service.serviceimpl;

import com.vinay.radiant.Exception.PostNotFoundException;
import com.vinay.radiant.Exception.UserNotFoundException;
import com.vinay.radiant.dto.PostDto;
import com.vinay.radiant.dto.UserDto;
import com.vinay.radiant.model.Post;
import com.vinay.radiant.model.User;
import com.vinay.radiant.repository.PostRepository;
import com.vinay.radiant.repository.UserRepository;
import com.vinay.radiant.service.PostService;
import com.vinay.radiant.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User Not Found With UserId : " + userId)
        );
        Post post = modelMapper.map(postDto, Post.class);
        post.setUser(user);
        post.setCreatedAt(LocalDateTime.now());
        Post savedPost = postRepository.save(post);
        return modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(Integer postId, Integer userId, PostDto updatedPostDto) throws PostNotFoundException, UserNotFoundException {
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with PostId : " + postId));
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User Not Found With UserId : " + userId)
        );
        if(!existingPost.getUser().getId().equals(user.getId())) {
            throw new BadCredentialsException("You can't update someone else's post");
        }
        existingPost.setCaption(updatedPostDto.getCaption());
        existingPost.setImage(updatedPostDto.getImage());
        existingPost.setVideo(updatedPostDto.getVideo());
        Post updatedPost = postRepository.save(existingPost);
        return modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public PostDto deletePost(Integer postId, Integer userId) throws UserNotFoundException, PostNotFoundException {
        PostDto postDto = findPostById(postId);
        Post post = modelMapper.map(postDto, Post.class);
        UserDto userDto = userService.findUserById(userId);
        User user = modelMapper.map(userDto, User.class);
        if(!post.getUser().getId().equals(user.getId())){
            throw new BadCredentialsException("You Can't Delete other's post");
        }
        postRepository.deleteById(postId);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> findPostByUserId(Integer userId) {
        List<Post> posts = postRepository.findPostByUserId(userId);
        return posts.stream()
                    .map(post -> modelMapper.map(post, PostDto.class))
                    .collect(Collectors.toList());
    }

    @Override
    public PostDto findPostById(Integer postId) throws PostNotFoundException {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException("Post Not Found With PostId : " + postId)
        );
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> findAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                    .map(post -> modelMapper.map(post, PostDto.class))
                    .collect(Collectors.toList());
    }

    @Override
    public PostDto savePost(Integer postId, Integer userId) throws PostNotFoundException, UserNotFoundException {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException("Post Not Found With PostId : " + postId)
        );
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User Not Found With UserId : " + userId)
        );
        if(user.getSavedPost().contains(post)){
            user.getSavedPost().remove(post);
        }else{
            user.getSavedPost().add(post);
        }
        userRepository.save(user);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostDto likePost(Integer postId, Integer userId) throws PostNotFoundException, UserNotFoundException {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException("Post Not Found With PostId : " + postId)
        );
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User Not Found With UserId : " + userId)
        );
        if(post.getLiked().contains(user)){
            post.getLiked().remove(user);
        }else{
            post.getLiked().add(user);
        }
        Post updatedPost = postRepository.save(post);
        return modelMapper.map(updatedPost, PostDto.class);
    }
}
