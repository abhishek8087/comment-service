package com.social.comments;

import java.util.List;

public interface CommentsService {

  DetailedComment getCommentById(String id);
  DetailedComment addComment(Comment comment);
  DetailedComment addLike(String userId, String commentId);
  DetailedComment addReply(String commentId,Comment comment);
  List<DetailedComment> getPaginatedReplies(String commentId, Integer pageNumber);
  DetailedComment addDislike(String userId, String commentId);
  List<User> getUsersWhoDisliked(String commentId, Integer pageNumber);
  List<User> getUsersWhoLiked(String commentId, Integer pageNumber);

}
