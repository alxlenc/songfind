package alx.music.songfind.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.RequestMatcher;
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
    RequestMatcher matcher;
    http.requestMatchers()
        .antMatchers(SPOTIFY_AUTH_PATHS)
        .and()
        .securityContext().disable()
        .sessionManagement().sessionFixation().none().and()
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
        .authenticationEntryPoint(problemSupport)
        .accessDeniedHandler(problemSupport);
    ;
  }
}
