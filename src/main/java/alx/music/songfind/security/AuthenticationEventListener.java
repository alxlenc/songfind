package alx.music.songfind.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationEventListener {

  @EventListener
  public void onSuccess(AuthenticationSuccessEvent success) {
    log.info(String.valueOf(success));
  }

  @EventListener
  public void onFailure(AbstractAuthenticationFailureEvent failure) {
    log.info(String.valueOf(failure));
  }

}
