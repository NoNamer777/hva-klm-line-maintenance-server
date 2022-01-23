package Klm1.KLMLineMaintenanceServer.models.helper;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnAuthorizedException extends RuntimeException {
  public UnAuthorizedException(String message) {
    super(message);
  }
}
