package com.social.comments;

public class NotFoundException extends RuntimeException {

  private static final String DEFAULT_MESSAGE = "Could not find resource.";

  public NotFoundException(String message, Throwable ex) {
    super(message, ex);
  }

  public NotFoundException(Throwable ex) {
    super(DEFAULT_MESSAGE, ex);
  }

  public NotFoundException(String message){
    super(message);
  }

}
