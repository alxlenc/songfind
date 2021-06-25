package alx.music.songfind.adapter.in.web;


import alx.music.songfind.adapter.in.web.mapper.SecurityMapper;
import alx.music.songfind.adapter.in.web.model.User;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
class AccountController {

  public static final String END_SESSION_ENDPOINT = "end_session_endpoint";
  private final SecurityMapper securityMapper;

  private final ClientRegistration registration;


  public AccountController(SecurityMapper securityMapper,
      ClientRegistrationRepository registrations) {
    this.securityMapper = securityMapper;
    this.registration = registrations.findByRegistrationId("songfind");
  }

  /**
   * {@code POST  /api/logout} : logout the current user.
   *
   * @param request the {@link HttpServletRequest}.
   * @param idToken the ID token.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and a body with a global logout
   * URL and ID token.
   */
  @PostMapping("/logout")
  public ResponseEntity<?> logout(HttpServletRequest request,
      @AuthenticationPrincipal(expression = "idToken") OidcIdToken idToken) {

    String logoutUrl = this.registration.getProviderDetails().getConfigurationMetadata().get(
        END_SESSION_ENDPOINT).toString();

    Map<String, String> logoutDetails = new HashMap<>();
    logoutDetails.put("logoutUrl", logoutUrl);
    logoutDetails.put("idToken", idToken.getTokenValue());
    request.getSession().invalidate();
    return ResponseEntity.ok().body(logoutDetails);
  }

  @GetMapping("/account")
  public User getAccount(Principal principal) {
    return this.securityMapper.getUserFromAuthentication((AbstractAuthenticationToken) principal);
  }

}
