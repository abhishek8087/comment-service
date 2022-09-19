package com.social.comments.exception;

import com.social.comment.domain.ErrorResponse;
import com.social.comments.NotFoundException;
import com.social.comments.UpstreamResourceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

  private static final String RESOURCE_NOT_FOUND = "resource_not_found";
  private static final String INTERNAL_SERVICE_ERROR = "internal_service_error";

  @ExceptionHandler(NotFoundException.class)
  public final ResponseEntity<ErrorResponse> handleNotFoundException(
      NotFoundException ex) {
    ErrorResponse error = new ErrorResponse();
    error.setType(RESOURCE_NOT_FOUND);
    error.setMessage(ex.getLocalizedMessage());
    log.error(ex.getLocalizedMessage());
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UpstreamResourceException.class)
  public final ResponseEntity<ErrorResponse> handleUpstreamException(
      UpstreamResourceException ex) {
    ErrorResponse error = new ErrorResponse();
    error.setType(INTERNAL_SERVICE_ERROR);
    error.setMessage(INTERNAL_SERVICE_ERROR);
    log.error(ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
