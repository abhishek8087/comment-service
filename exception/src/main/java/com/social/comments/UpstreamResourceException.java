package com.social.comments;

public class UpstreamResourceException extends RuntimeException{

  private static final String DEFAULT_MESSAGE = "Internal server error";

  public UpstreamResourceException(String message){
    super(message);
  }
}
