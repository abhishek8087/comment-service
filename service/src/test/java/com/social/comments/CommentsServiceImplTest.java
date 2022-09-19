package com.social.comments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CommentsServiceImpl.class})
@Import({
    UserServiceImpl.class
})
public class CommentsServiceImplTest {
  @Autowired
  private CommentsService commentsService;

  @Autowired
  private UserService userService;

  @MockBean
  private UserRepository userRepository;
  @MockBean
  CommentRepository commentRepository;

  Comment comment;
  User user;
  @Before
  public void setUp(){
    comment = new Comment();
    comment.setCreatedAt(new Date());
    comment.setAuthor("123");
    List<String> likes = new ArrayList<>();
    likes.add("123");
    likes.add("2");
    List<String> dislikes = new ArrayList<>();
    dislikes.add("123");
    dislikes.add("2");
    comment.setLikes(likes);
    comment.setDislikes(dislikes);
    comment.getReplies().add("12");
    comment.getReplies().add("212");
    comment.setId("1");
    comment.setText("Test");
    Mockito.when(commentRepository.findById("1")).thenReturn(
        Optional.ofNullable(comment));
    List<User> users = new ArrayList<>();
    user = new User();
    user.setId("123");
    user.setName("TestUser");
    user.setName("prfUrl");
    users.add(user);
    Mockito.when(userRepository.findById("123")).thenReturn(Optional.ofNullable(user));
    user = new User();
    user.setId("1234");
    user.setName("TestUser");
    user.setName("prfUrl");
    users.add(user);
    Mockito.when(userRepository.findById("1234")).thenReturn(Optional.ofNullable(user));

    Mockito.when(userRepository.findAllById(likes)).thenReturn(users);

    Mockito.when(commentRepository.save(comment)).thenReturn(comment);

  }

  @Test
  public void getCommentIdSuccess(){
    DetailedComment detailedComment = commentsService.getCommentById("1");
    Assert.assertSame(detailedComment.getLikes(),2);
  }

  @Test(expected = NotFoundException.class)
  public void getCommentIdNotFound(){
    DetailedComment detailedComment = commentsService.getCommentById("2");
  }

  @Test
  public void addCommentSuccess(){
    DetailedComment detailedComment = commentsService.addComment(comment);
    Assert.assertSame(detailedComment.getId(), comment.getId());
  }

  @Test(expected = NotFoundException.class)
  public void addCommentWithInvalidUser(){
    comment.setAuthor("12");
    DetailedComment detailedComment = commentsService.addComment(comment);
    Assert.assertSame(detailedComment.getId(), comment.getId());
  }

  @Test(expected = NotFoundException.class)
  public void addLikeWithInvalidCommentId(){
    commentsService.addLike("123","2");
  }

  @Test(expected = NotFoundException.class)
  public void addLikeWithInvalidUserId(){
    commentsService.addLike("12","1");
  }

  @Test
  public void addLikeSuccess(){
    commentsService.addLike("123","1");
  }

  @Test(expected = NotFoundException.class)
  public void addReplyWithInvalidCommentId(){
    commentsService.addReply("12",comment);
  }

  @Test
  public void addReplySuccess(){
    DetailedComment detailedComment = commentsService.addReply("1",comment);
    Assert.assertSame(detailedComment.getId(), "1");
  }


  @Test(expected = NotFoundException.class)
  public void addDisLikeWithInvalidCommentId(){
    commentsService.addDislike("123","2");
  }

  @Test(expected = NotFoundException.class)
  public void addDisLikeWithInvalidUserId(){
    commentsService.addDislike("12","1");
  }

  @Test
  public void addDisLikeSuccess(){
    commentsService.addDislike("123","1");
  }

  @Test
  public void getPaginatedReply(){
    Comment comment1 = new Comment();
    comment1.setId("12");
    comment1.setAuthor("123");
    Mockito.when(commentRepository.findById("12")).thenReturn(
        Optional.ofNullable(comment1));
    Comment comment2 = new Comment();
    comment2.setId("212");
    comment2.setAuthor("123");
    Mockito.when(commentRepository.findById("212")).thenReturn(
        Optional.ofNullable(comment2));
    List<DetailedComment> detailedComments = commentsService.getPaginatedReplies("1",0);
    Assert.assertSame(detailedComments.size(), 1);
  }

  @Test(expected = NotFoundException.class)
  public void getPaginatedReplyWithInvalidPage(){
    commentsService.getPaginatedReplies("1",1);
  }

  @Test
  public void getPaginatedUsersWhoLiked(){
    List<User> users = commentsService.getUsersWhoLiked("1",0);
    Assert.assertSame(users.size(), 2);
  }

  @Test(expected = NotFoundException.class)
  public void getPaginatedUsersWhoLikedIncorrectPageNumber(){
    commentsService.getUsersWhoLiked("1",1);
  }

  @Test
  public void getPaginatedUsersWhoDisLiked(){
    List<User> users = commentsService.getUsersWhoDisliked("1",0);
    Assert.assertSame(users.size(), 2);
  }

  @Test(expected = NotFoundException.class)
  public void getPaginatedUsersWhoDisLikedIncorrectPageNumber(){
    commentsService.getUsersWhoDisliked("1",1);
  }





}
