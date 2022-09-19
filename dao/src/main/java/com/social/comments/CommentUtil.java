package com.social.comments;

import com.soical.comments.Comment;
import com.soical.comments.DetailedComment;
import com.soical.comments.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentUtil {

  @Autowired
  CommentRepository commentRepository;

  @Autowired
  UserRepository userRepository;

  private static final int MAX_REPLIES = 1;

  private static final int PAGE_SIZE = 5;

  public DetailedComment getComment(String id){
    Comment comment = commentRepository.findById(id).orElseGet(null);
    return buildDetailedComment(comment);
  }

  public DetailedComment save(Comment comment){
    Comment savedComment = commentRepository.save(comment);
    return buildDetailedComment(savedComment);
  }
  public DetailedComment addLike(String userId, String commentId){
    Comment comment = commentRepository.findById(commentId).orElseGet(null);
    Comment savedComment = null;
    if(Objects.nonNull(comment)){
      comment.getLikes().add(userId);
      savedComment = commentRepository.save(comment);
    }
    return buildDetailedComment(savedComment);
  }

  public DetailedComment addDislike(String userId, String commentId){
    Comment comment = commentRepository.findById(commentId).orElseGet(null);
    Comment savedComment = null;
    if(Objects.nonNull(comment)){
      comment.getDislikes().add(userId);
      savedComment = commentRepository.save(comment);
    }
    return buildDetailedComment(savedComment);
  }
  public DetailedComment addReply(String commentId, Comment reply){
    Comment comment = commentRepository.findById(commentId).orElseGet(null);
    reply.setCreatedAt(new Date());
    Comment savedReply = commentRepository.save(reply);
    Comment savedComment = null;
    if(Objects.nonNull(comment)){
      comment.getReplies().add(savedReply.getId());
      savedComment = commentRepository.save(comment);
    }
    return buildDetailedComment(savedComment);
  }

  public List<DetailedComment> getPaginatedReplies(String commentId, Integer pageNumber){
    Comment comment = commentRepository.findById(commentId).orElseGet(null);
    int start = pageNumber * PAGE_SIZE + 1;
    int end = start + PAGE_SIZE;
    if(end > comment.getReplies().size()) end = comment.getReplies().size();
    if(start > comment.getReplies().size()) return new ArrayList<>();
    List<String> replies = comment.getReplies().subList(start, end);
    List<DetailedComment> detailedComments = new ArrayList<>();
    for(String reply : replies){
      detailedComments.add(getComment(reply));
    }
    return detailedComments;
   }

   public List<User> getUsersWhoLiked(String commentId){
     Comment comment = commentRepository.findById(commentId).orElseGet(null);
     return StreamSupport.stream(userRepository.findAllById(comment.getLikes()).spliterator(), false)
         .collect(Collectors.toList());
   }

  public List<User> getUsersWhoDisliked(String commentId){
    Comment comment = commentRepository.findById(commentId).orElseGet(null);
    return StreamSupport.stream(userRepository.findAllById(comment.getDislikes()).spliterator(), false)
        .collect(Collectors.toList());
  }

  private DetailedComment buildDetailedComment(Comment comment){
    List<String> replyList = comment.getReplies().stream().limit(MAX_REPLIES).collect(Collectors.toList());
    List<Comment> replies = StreamSupport.stream(commentRepository.findAllById(replyList).spliterator(), false)
        .collect(Collectors.toList());
    List<User> likedUsers = StreamSupport.stream(userRepository.findAllById(comment.getLikes()).spliterator(), false)
        .collect(Collectors.toList());
    List<User> disLikedUsers = StreamSupport.stream(userRepository.findAllById(comment.getDislikes()).spliterator(), false)
        .collect(Collectors.toList());
    List<DetailedComment> detailedComments = commentToDetailedCommentList(replies);
    DetailedComment detailedComment = new DetailedComment();
    detailedComment.setText(comment.getText());
    detailedComment.setId(comment.getId());
    detailedComment.setAuthor(userRepository.findById(comment.getAuthor()).orElseGet(null));
    detailedComment.setCreatedAt(comment.getCreatedAt());
    detailedComment.setReplies(detailedComments);
    detailedComment.setLikes(likedUsers);
    detailedComment.setDislikes(disLikedUsers);
    return detailedComment;
  }

  private List<DetailedComment> commentToDetailedCommentList(List<Comment> comments){
    List<DetailedComment> detailedComments = new ArrayList<>();
    for(Comment comment : comments){
      detailedComments.add(getComment(comment.getId()));
    }
    return detailedComments;
  }
}
