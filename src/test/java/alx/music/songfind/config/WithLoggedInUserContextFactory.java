package alx.music.songfind.config;

import alx.music.songfind.security.AuthoritiesConstants;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithLoggedInUserContextFactory implements WithSecurityContextFactory<WithLoggedUser> {

  @Override
  public SecurityContext createSecurityContext(WithLoggedUser withLoggedUser) {
    Collection<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(
        AuthoritiesConstants.ADMIN));
    Instant issuedAt = Instant.now();
    Instant expiresAt = Instant.now().plusSeconds(300);
    Map<String, Object> claims = Map.of("roles", List.of("ROLE_USER"), "sub", "songfind");
    OidcIdToken idToken = new OidcIdToken("fake", issuedAt, expiresAt, claims);
    OAuth2User principal = new DefaultOidcUser(authorities, idToken);
    Authentication authentication = new OAuth2AuthenticationToken(principal, authorities, "songfind");
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(authentication);
    return context;
  }
}
