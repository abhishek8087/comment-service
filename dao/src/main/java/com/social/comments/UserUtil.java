package com.social.comments;

import com.soical.comments.User;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserUtil {

  @Autowired
  UserRepository userRepository;

  public User addUser(User user){
    return userRepository.save(user);
  }

  public User getUser(String userId){
    return userRepository.findById(userId).orElseGet(null);
  }

  public List<User> getUser(List<String> userIds){
    return StreamSupport.stream(userRepository.findAllById(userIds).spliterator(), false)
        .collect(Collectors.toList());
  }

}
