package com.social.comments;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  UserRepository userRepository;

  private static final String USER_ID_INVALID = "user id is not valid.";

  public User addUser(User user) {
    return userRepository.save(user);
  }

  public List<User> getUsers(List<String> userIds){
    return StreamSupport.stream(userRepository.findAllById(userIds).spliterator(), false)
        .collect(Collectors.toList());
  }

  @Override
  public User getUser(String userId) {
    Optional<User> userOptional = userRepository.findById(userId);
    if(userOptional.isPresent()){
      return userOptional.get();
    }
    throw new NotFoundException(USER_ID_INVALID);
  }


}
