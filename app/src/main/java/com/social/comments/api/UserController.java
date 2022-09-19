package com.social.comments.api;

import com.social.comment.api.UserApi;
import com.social.comment.domain.User;
import com.social.comments.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UserApi {

  @Autowired
  UserService userService;

  @Override
  public ResponseEntity<User> addUser(User user) {
   userService.addUser(com.soical.comments.User.builder().id(user.getId())
        .name(user.getName())
        .profileUrl(user.getProfileUrl())
        .build());
    return ResponseEntity.ok(user);
  }
}
