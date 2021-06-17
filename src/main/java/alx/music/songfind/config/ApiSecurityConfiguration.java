package alx.music.songfind.config;

import static alx.music.songfind.config.SpotifySecurityConfigurerAdapter.SPOTIFY_AUTH_PATHS;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Stream;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

@Configuration
public class ApiSecurityConfiguration {

  @Configuration
  @Order(3)
  @Import(SecurityProblemSupport.class)
  public static class ApiConfigurerAdapter extends WebSecurityConfigurerAdapter {

    private static final List<RequestMatcher> ALLOWED_MATCHERS = Stream
        .of(new String[]{"/actuator/**"}, SPOTIFY_AUTH_PATHS)
        .flatMap(Stream::of)
        .map(AntPathRequestMatcher::new)
        .collect(toList());

    private static final String HOME = "/home";
    private final SecurityProblemSupport problemSupport;

    public ApiConfigurerAdapter(
        SecurityProblemSupport problemSupport) {
      this.problemSupport = problemSupport;
    }

    private static Customizer<CorsConfigurer<HttpSecurity>> getCorsConfigurerCustomizer() {
      return c -> {
        CorsConfigurationSource source = request -> {
          CorsConfiguration config = new CorsConfiguration();
          config.setAllowedOrigins(List.of("http://localhost:9000"));
          config.setAllowedMethods(List.of("*"));
          return config;
        };
        c.configurationSource(source);
      };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { // @formatter:off

      RequestMatcher allowedOrMatcher = new OrRequestMatcher(ALLOWED_MATCHERS);
      http.requestMatcher(new NegatedRequestMatcher(allowedOrMatcher)).authorizeRequests()
          .antMatchers("/")
          .permitAll()
          .antMatchers("/api/public/**")
          .permitAll()
          .anyRequest()
          .authenticated()
          .and()
          .csrf()
          .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
          .and()
          .oauth2Login(customizer ->
              customizer
                  .defaultSuccessUrl(HOME)
                  .authorizationEndpoint()
                  .baseUri("/login/oauth2/authorization")
                  .and()
                  .failureUrl(HOME)
          )
          .exceptionHandling()
          .authenticationEntryPoint(problemSupport)
          .accessDeniedHandler(problemSupport)
          .and()
          .oauth2Client()
      ;

      http.cors(getCorsConfigurerCustomizer());
    } // @formatter:on
  }
}
