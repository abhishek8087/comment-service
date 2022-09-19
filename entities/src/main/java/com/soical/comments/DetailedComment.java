package com.soical.comments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DetailedComment {
  private String id;
  private String text;
  private List<DetailedComment> replies = new ArrayList<>();
  private List<User> likes = new ArrayList<>();
  private List<User> dislikes = new ArrayList<>();
  private Date createdAt;
  private User author;
}
