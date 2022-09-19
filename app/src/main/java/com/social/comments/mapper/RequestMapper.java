package com.social.comments.mapper;

import com.social.comment.domain.CommentRequest;
import com.soical.comments.Comment;
import org.springframework.stereotype.Component;

@Component
public class RequestMapper {

  public Comment domainToEntity(CommentRequest commentRequest){
    Comment comment = new Comment();
    comment.setAuthor(commentRequest.getAuthor());
    comment.setText(commentRequest.getText());
    return comment;
  }

}
