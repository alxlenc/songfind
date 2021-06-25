package alx.music.songfind.adapter.in.web.mapper;

import alx.music.songfind.adapter.in.web.model.Authority;
import alx.music.songfind.adapter.in.web.model.User;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class SecurityMapper {

  public User getUserFromAuthentication(AbstractAuthenticationToken authToken) {
    Map<String, Object> attributes = this.getAttributes(authToken);
    User user = this.parseUser(attributes);
    user.setAuthorities(this.extractAuthorities(authToken));
    return user;
  }

  private User parseUser(Map<String, Object> details) {
    User user = new User();
    // handle resource server JWT, where sub claim is email and uid is ID
    if (details.get("uid") != null) {
      user.setId((String) details.get("uid"));
      user.setLogin((String) details.get("sub"));
    } else {
      user.setId((String) details.get("sub"));
    }
    if (details.get("preferred_username") != null) {
      user.setLogin(((String) details.get("preferred_username")).toLowerCase());
    } else if (user.getLogin() == null) {
      user.setLogin(user.getId());
    }
    if (details.get("given_name") != null) {
      user.setFirstName((String) details.get("given_name"));
    }
    if (details.get("family_name") != null) {
      user.setLastName((String) details.get("family_name"));
    }
    if (details.get("email_verified") != null) {
      user.setActivated((Boolean) details.get("email_verified"));
    }
    if (details.get("email") != null) {
      user.setEmail(((String) details.get("email")).toLowerCase());
    } else {
      user.setEmail((String) details.get("sub"));
    }
    if (details.get("langKey") != null) {
      user.setLangKey((String) details.get("langKey"));
    } else if (details.get("locale") != null) {
      // trim off country code if it exists
      String locale = (String) details.get("locale");
      if (locale.contains("_")) {
        locale = locale.substring(0, locale.indexOf('_'));
      } else if (locale.contains("-")) {
        locale = locale.substring(0, locale.indexOf('-'));
      }
      user.setLangKey(locale.toLowerCase());
    } else {
      // set langKey to default if not specified by IdP
      user.setLangKey("en");
    }
    if (details.get("picture") != null) {
      user.setImageUrl((String) details.get("picture"));
    }
    user.setActivated(true);
    return user;
  }


  private Set<Authority> extractAuthorities(AbstractAuthenticationToken authToken) {
    return authToken
        .getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .map(Authority::new)
        .collect(Collectors.toSet());
  }

  private Map<String, Object> getAttributes(AbstractAuthenticationToken authToken) {
    Map<String, Object> attributes;
    if (authToken instanceof OAuth2AuthenticationToken) {
      attributes = ((OAuth2AuthenticationToken) authToken).getPrincipal().getAttributes();
    } else if (authToken instanceof JwtAuthenticationToken) {
      attributes = ((JwtAuthenticationToken) authToken).getTokenAttributes();
    } else {
      throw new IllegalArgumentException("AuthenticationToken is not OAuth2 or JWT!");
    }
    return attributes;
  }

}
