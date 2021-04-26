package alx.music.songfind.config;

import static alx.music.songfind.config.SpotifySecurityConfigurerAdapter.SPOTIFY_AUTH_PATHS;
import static java.util.stream.Collectors.toList;

import alx.music.songfind.security.oauth2.AudienceValidator;
import alx.music.songfind.security.oauth2.JwtGrantedAuthorityConverter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

@Configuration
@Order(3)
@Import(SecurityProblemSupport.class)
public class ApiSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

  private static final List<RequestMatcher> ALLOWED_MATCHERS = Stream.of(new String[]{"/actuator/**"}, SPOTIFY_AUTH_PATHS )
      .flatMap(Stream::of)
      .map(AntPathRequestMatcher::new)
      .collect(toList());

  private static final String HOME = "/home";
  private final SecurityProblemSupport problemSupport;
  private final String issuerUri;
  private final String applicationAudience;

  public ApiSecurityConfigurerAdapter(
      @Value("${spring.security.oauth2.client.provider.songfind.issuer-uri}") String issuerUri,
      @Value("${application.auth.audience}") String applicationAudience,
      SecurityProblemSupport problemSupport) {
    this.issuerUri = issuerUri;
    this.applicationAudience = applicationAudience;
    this.problemSupport = problemSupport;
  }

  private static Converter<Jwt, AbstractAuthenticationToken> authenticationConverter() {
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter
        .setJwtGrantedAuthoritiesConverter(new JwtGrantedAuthorityConverter());
    return jwtAuthenticationConverter;
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
  protected void configure(HttpSecurity http) throws Exception {// @formatter:off

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
        .and()
        .oauth2ResourceServer()
        .jwt()
        .jwtAuthenticationConverter(authenticationConverter())
    ;

    http.cors(getCorsConfigurerCustomizer());
  }// @formatter:on

  @Bean
  JwtDecoder jwtDecoder() {
    NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder) JwtDecoders
        .fromOidcIssuerLocation(issuerUri);

    OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(
        Arrays.asList(applicationAudience.split(",")));
    OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuerUri);
    OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer,
        audienceValidator);

    jwtDecoder.setJwtValidator(withAudience);

    return jwtDecoder;
  }



}
