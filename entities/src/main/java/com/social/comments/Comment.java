package com.social.comments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="comments")
public class Comment {
  @Id
  private String id;
  private String text;
  private List<String> replies = new ArrayList<>();
  private List<String> likes = new ArrayList<>();
  private List<String> dislikes = new ArrayList<>();
  private Date createdAt;
  private String author;
}
