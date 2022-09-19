package com.social.comments;

import com.soical.comments.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  UserUtil userUtil;


  public User addUser(User user) {
    return userUtil.addUser(user);
  }

  public List<User> getUsers(List<String> userIds){
    return userUtil.getUser(userIds);
  }

}
