package com.social.comments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentsServiceImpl implements CommentsService {

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  UserService userService;

  private static final int MAX_REPLIES = 1;

  private static final int COMMENT_PAGE_SIZE = 5;
  private static final int USER_PAGE_SIZE = 5;

  private static final String COMMENT_ID_INVALID = "comment id is not valid.";


  public DetailedComment getCommentById(String id){
    Comment comment = getComment(id);
    return buildDetailedComment(comment);
  }
  public DetailedComment addComment(Comment comment){
    userService.getUser(comment.getAuthor());
    Comment savedComment = commentRepository.save(comment);
    return buildDetailedComment(savedComment);
  }

  public DetailedComment addLike(String userId, String commentId){
    Comment comment = getComment(commentId);
    User user = userService.getUser(userId);
    comment.getLikes().add(user.getId());
    Comment savedComment = commentRepository.save(comment);
    return buildDetailedComment(savedComment);
  }


  public DetailedComment addReply(String commentId,Comment comment){
    Comment replyTo = getComment(commentId);
    comment.setCreatedAt(new Date());
    Comment savedReply = commentRepository.save(comment);
    replyTo.getReplies().add(savedReply.getId());
    Comment savedComment = commentRepository.save(replyTo);
    return buildDetailedComment(savedComment);
  }

  public List<DetailedComment> getPaginatedReplies(String commentId, Integer pageNumber){
    Comment comment = getComment(commentId);
    int start = pageNumber * COMMENT_PAGE_SIZE + 1;
    int end = start + COMMENT_PAGE_SIZE;
    if(end > comment.getReplies().size()) end = comment.getReplies().size();
    if(start > comment.getReplies().size()) throw new NotFoundException("No more replies found.");
    List<String> replies = comment.getReplies().subList(start, end);
    List<DetailedComment> detailedComments = new ArrayList<>();
    for(String reply : replies){
      detailedComments.add(getCommentById(reply));
    }
    return detailedComments;
  }
  public DetailedComment addDislike(String userId, String commentId) {
    Comment comment = getComment(commentId);
    User user = userService.getUser(userId);
    comment.getDislikes().add(user.getId());
    Comment savedComment = commentRepository.save(comment);
    return buildDetailedComment(savedComment);
  }

  public List<User> getUsersWhoDisliked(String commentId, Integer pageNumber) {
    Comment comment = getComment(commentId);
    return userService.getUsers(getPagedUsers(comment.getDislikes(),pageNumber));
  }

  public List<User> getUsersWhoLiked(String commentId, Integer pageNumber) {
    Comment comment = getComment(commentId);
    return userService.getUsers(getPagedUsers(comment.getLikes(),pageNumber));
  }

  private List<String> getPagedUsers(List<String> users, int pageNumber){
    int start = pageNumber * USER_PAGE_SIZE;
    int end = start + USER_PAGE_SIZE;
    if(end > users.size()) end = users.size();
    if(start > users.size()) throw new NotFoundException("No more replies found.");
    return users.subList(start, end);
  }

  private DetailedComment buildDetailedComment(Comment comment){
    List<String> replyList = comment.getReplies().stream().limit(MAX_REPLIES).collect(Collectors.toList());
    List<Comment> replies = StreamSupport.stream(commentRepository.findAllById(replyList).spliterator(), false)
        .collect(Collectors.toList());
    List<DetailedComment> detailedComments = commentToDetailedCommentList(replies);
    DetailedComment detailedComment = new DetailedComment();
    detailedComment.setText(comment.getText());
    detailedComment.setId(comment.getId());
    detailedComment.setAuthor(userService.getUser(comment.getAuthor()));
    detailedComment.setCreatedAt(comment.getCreatedAt());
    detailedComment.setReplies(detailedComments);
    detailedComment.setLikes(comment.getLikes().size());
    detailedComment.setDislikes(comment.getDislikes().size());
    return detailedComment;
  }

  private List<DetailedComment> commentToDetailedCommentList(List<Comment> comments){
    List<DetailedComment> detailedComments = new ArrayList<>();
    for(Comment comment : comments){
      detailedComments.add(getCommentById(comment.getId()));
    }
    return detailedComments;
  }

  private Comment getComment(String commentId){
    Optional<Comment> commentOptional = commentRepository.findById(commentId);
    if(commentOptional.isPresent()){
      return commentOptional.get();
    }
    throw new NotFoundException(COMMENT_ID_INVALID);
  }

}
