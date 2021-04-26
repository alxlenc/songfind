package alx.music.songfind.security.oauth2;

import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class OAuth2AuthenticationExceptionEvent extends AbstractAuthenticationFailureEvent {

  public OAuth2AuthenticationExceptionEvent(Authentication authentication, AuthenticationException exception) {
    super(authentication, exception);
  }

}
