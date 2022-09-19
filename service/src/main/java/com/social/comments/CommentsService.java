package com.social.comments;

import com.soical.comments.Comment;
import com.soical.comments.DetailedComment;
import com.soical.comments.User;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentsService {

  @Autowired
  private CommentUtil commentUtil;

  public DetailedComment getCommentById(String id){
    return commentUtil.getComment(id);
  }
  public DetailedComment addComment(Comment comment){
    comment.setCreatedAt(new Date());
    return commentUtil.save(comment);
  }

  public DetailedComment addLike(String userId, String commentId){
    return commentUtil.addLike(userId, commentId);
  }

  public DetailedComment addReply(String commentId,Comment comment){
    return commentUtil.addReply(commentId, comment);
  }

  public List<DetailedComment> getPaginatedReplies(String commentId, Integer pageNumber){
    return commentUtil.getPaginatedReplies(commentId, pageNumber);
  }
  public DetailedComment addDislike(String userId, String commentId) {
    return commentUtil.addDislike(userId, commentId);
  }

  public List<User> getUsersWhoDisliked(String commentId) {
    return commentUtil.getUsersWhoDisliked(commentId);
  }

  public List<User> getUsersWhoLiked(String commentId) {
    return commentUtil.getUsersWhoLiked(commentId);
  }
}
