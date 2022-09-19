package com.social.comments.mapper;

import com.social.comment.domain.CommentResponse;
import com.social.comment.domain.User;
import com.soical.comments.DetailedComment;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ResponseMapper {

  public CommentResponse entityToDomain(DetailedComment comment){
    CommentResponse commentResponse = new CommentResponse();
    for(com.soical.comments.User user : comment.getLikes()){
      User userResponse = new User();
      userResponse.setId(user.getId());
      userResponse.setName(user.getName());
      userResponse.setProfileUrl(user.getProfileUrl());
      commentResponse.addLikesItem(userResponse);
    }
    for(com.soical.comments.User user : comment.getDislikes()){
      User userResponse = new User();
      userResponse.setId(user.getId());
      userResponse.setName(user.getName());
      userResponse.setProfileUrl(user.getProfileUrl());
      commentResponse.addDislikesItem(userResponse);
    }
    for(DetailedComment detailedComment : comment.getReplies()){
      commentResponse.addRepliesItem(entityToDomain(detailedComment));
    }
    User author = new User();
    author.setName(comment.getAuthor().getName());
    author.setId(comment.getAuthor().getId());
    author.setProfileUrl(comment.getAuthor().getProfileUrl());
    commentResponse.setAuthor(author);
    commentResponse.setCreatedAt(comment.getCreatedAt() == null ? null : comment.getCreatedAt().toString());
    commentResponse.setText(comment.getText());
    commentResponse.setId(comment.getId());
    return commentResponse;
  }

  public List<User> entityToDomain(List<com.soical.comments.User> users){
    List<User> response = new ArrayList<>();
    for(com.soical.comments.User user : users){
      User domainUser = new User();
      domainUser.setProfileUrl(user.getProfileUrl());
      domainUser.setName(user.getName());
      domainUser.setId(user.getId());
      response.add(domainUser);
    }
    return response;
  }

}
