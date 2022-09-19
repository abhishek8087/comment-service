package com.social.comments;

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
  private Integer likes;
  private Integer dislikes;
  private Date createdAt;
  private User author;
}
