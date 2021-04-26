package alx.music.songfind.config;

import alx.music.songfind.security.SecurityUtils;
import alx.music.songfind.security.oauth2.OAuth2AuthenticationExceptionEvent;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.boot.actuate.audit.InMemoryAuditEventRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration {

  @Bean
  public InMemoryAuditEventRepository repository() {
    return new InMemoryAuditEventRepository();
  }

  @Bean
  AuthenticationEventPublisher authenticationEventPublisher
      (ApplicationEventPublisher applicationEventPublisher) {
    Map<Class<? extends AuthenticationException>,
        Class<? extends AbstractAuthenticationFailureEvent>> mapping =
        Collections
            .singletonMap(OAuth2AuthenticationException.class,
                OAuth2AuthenticationExceptionEvent.class);
    var authenticationEventPublisher =
        new DefaultAuthenticationEventPublisher(applicationEventPublisher);
    authenticationEventPublisher.setAdditionalExceptionMappings(mapping);
    return authenticationEventPublisher;
  }

  @Bean
  OAuth2AuthorizedClientRepository oauth2AuthorizedClientRepository() {
    return new HttpSessionOAuth2AuthorizedClientRepository();
  }

  @Bean
  GrantedAuthoritiesMapper userAuthoritiesMapper() {
    return authorities -> {
      Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

      authorities.forEach(
          authority -> {
            // Check for OidcUserAuthority because Spring Security 5.2 returns
            // each scope as a GrantedAuthority, which we don't care about.
            if (authority instanceof OidcUserAuthority oidcUserAuthority) {
              mappedAuthorities.addAll(
                  SecurityUtils
                      .extractAuthorityFromClaims(oidcUserAuthority.getUserInfo().getClaims()));
            }
          }
      );
      return mappedAuthorities;
    };
  }

  @Configuration
  @Order(2)
  public static class ActuatorConfigurationAdapter extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.requestMatchers()
          .antMatchers("/actuator/health/liveness",
              "/actuator/health/readiness",
              "/actuator/auditevents",
              "/actuator/mappings")
          .and()
          .authorizeRequests().anyRequest().permitAll();
    }
  }

}