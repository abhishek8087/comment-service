package com.social.comments;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {

  @Override
  <S extends Comment> S save(S entity);
  @Override
  Iterable<Comment> findAllById(Iterable<String> strings);
  @Override
  Optional<Comment> findById(String s);


}
