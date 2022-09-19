package com.social.comments.api;

import com.social.comment.api.CommentApi;
import com.social.comment.domain.CommentRequest;
import com.social.comment.domain.CommentResponse;
import com.social.comment.domain.User;
import com.social.comments.CommentsService;
import com.social.comments.mapper.RequestMapper;
import com.social.comments.mapper.ResponseMapper;
import com.soical.comments.DetailedComment;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentsController implements CommentApi {

 @Autowired
 CommentsService commentsService;

 @Autowired
 RequestMapper requestMapper;

 @Autowired
 ResponseMapper responseMapper;


 @Override
 public ResponseEntity<CommentResponse> addComment(CommentRequest commentRequest) {
  DetailedComment comment = commentsService.addComment(requestMapper.domainToEntity(commentRequest));
  return ResponseEntity.ok(responseMapper.entityToDomain(comment));
 }

 @Override
 public ResponseEntity<CommentResponse> addDislike(String commentId, String userId) {
  DetailedComment comment = commentsService.addDislike(userId, commentId);
  return ResponseEntity.ok(responseMapper.entityToDomain(comment));
 }

 @Override
 public ResponseEntity<CommentResponse> addLike(String commentId, String userId) {
  DetailedComment comment = commentsService.addLike(userId, commentId);
  return ResponseEntity.ok(responseMapper.entityToDomain(comment));
 }


 @Override
 public ResponseEntity<CommentResponse> addReply(String commentId, CommentRequest commentRequest) {
  DetailedComment comment = commentsService.addReply(commentId, requestMapper.domainToEntity(commentRequest));
  return ResponseEntity.ok(responseMapper.entityToDomain(comment));
 }

 @Override
 public ResponseEntity<CommentResponse> getCommentById(String commentId) {
  DetailedComment comment = commentsService.getCommentById(commentId);
  return ResponseEntity.ok(responseMapper.entityToDomain(comment));
 }

 @Override
 public ResponseEntity<List<CommentResponse>> getMoreReplies(String commentId,
     Integer pageNumber) {
  List<DetailedComment> detailedComments = commentsService.getPaginatedReplies(commentId, pageNumber);
  List<CommentResponse> commentResponses = new ArrayList<>();
  for(DetailedComment detailedComment : detailedComments){
   commentResponses.add(responseMapper.entityToDomain(detailedComment));
  }
  return ResponseEntity.ok(commentResponses);
 }


 @Override
 public ResponseEntity<List<com.social.comment.domain.User>> getUsersWhoDisliked(String commentId) {
  return ResponseEntity.ok(responseMapper.entityToDomain(commentsService.getUsersWhoDisliked(commentId))) ;
 }

 @Override
 public ResponseEntity<List<com.social.comment.domain.User>> getUsersWhoLiked(String commentId) {
  return ResponseEntity.ok(responseMapper.entityToDomain(commentsService.getUsersWhoLiked(commentId))) ;
 }

}
