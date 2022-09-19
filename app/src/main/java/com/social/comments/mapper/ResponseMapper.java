package com.social.comments.mapper;

import com.social.comment.domain.CommentResponse;
import com.social.comment.domain.User;
import com.social.comments.DetailedComment;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ResponseMapper {

  public CommentResponse entityToDomain(DetailedComment comment){
    CommentResponse commentResponse = new CommentResponse();
    commentResponse.setLikes(comment.getLikes());
    commentResponse.setDislikes(comment.getDislikes());
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

  public List<User> entityToDomain(List<com.social.comments.User> users){
    List<User> response = new ArrayList<>();
    for(com.social.comments.User user : users){
      User domainUser = new User();
      domainUser.setProfileUrl(user.getProfileUrl());
      domainUser.setName(user.getName());
      domainUser.setId(user.getId());
      response.add(domainUser);
    }
    return response;
  }

}
