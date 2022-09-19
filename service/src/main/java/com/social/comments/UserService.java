package com.social.comments;

import java.util.List;

public interface UserService {

  User addUser(User user);
  List<User> getUsers(List<String> userIds);

  User getUser(String userId);
}
