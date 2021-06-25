package alx.music.songfind.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

@Configuration
@Import(SecurityProblemSupport.class)
@Order(1)
public class SpotifySecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

  public static final String[] SPOTIFY_AUTH_PATHS = {"/login/oauth2/code/spotify",
      "/login/oauth2/authorization/spotify"};

  private final SecurityProblemSupport problemSupport;

  public SpotifySecurityConfigurerAdapter(
      SecurityProblemSupport problemSupport) {
    this.problemSupport = problemSupport;
  }

  protected void configure(HttpSecurity http) throws Exception {// @formatter:off

    http.requestMatchers()
        .antMatchers(SPOTIFY_AUTH_PATHS)
        .and()
        // Disable SecurityContextPersistenceFilter which persists and restores the SecurityContext
        // to avoid overwriting Songfind principal with Soptify's principal
        .securityContext().disable()
        .sessionManagement()
        // Don't create a new session, this would invalidate the oauth client associated to "songfind" (keycloak)
        .sessionFixation().none()
        .and()
        .oauth2Client()
        .and()
        .oauth2Login(customizer ->
            customizer
                .defaultSuccessUrl("/home")
                .authorizationEndpoint()
                .baseUri("/login/oauth2/authorization")
                .and()
                .failureUrl("/login/oauth2/authorization/spotify")
        )
        .exceptionHandling()
        // Use problem support to return 401 codes instead of triggering 302
        .authenticationEntryPoint(problemSupport)
        .accessDeniedHandler(problemSupport);
  }
}
