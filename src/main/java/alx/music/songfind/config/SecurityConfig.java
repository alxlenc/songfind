package alx.music.songfind.config;

import alx.music.songfind.security.SecurityUtils;
import alx.music.songfind.security.oauth2.AudienceValidator;
import alx.music.songfind.security.oauth2.JwtGrantedAuthorityConverter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {


  private final String issuerUri;
  private final String applicationAudience;
  private final String providerId;

  public SecurityConfig(
      @Value("${spring.security.oauth2.client.provider.songfind.issuer-uri}") String issuerUri,
      @Value("${application.auth.audience}") String applicationAudience,
      @Value("${application.auth.provider-id}") String providerId) {
    this.issuerUri = issuerUri;
    this.applicationAudience = applicationAudience;
    this.providerId = providerId;
  }

  static Converter<Jwt, AbstractAuthenticationToken> authenticationConverter() {
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

  @Bean
  public GrantedAuthoritiesMapper userAuthoritiesMapper() {
    return authorities -> {
      Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

      authorities.forEach(
          authority -> {
            // Check for OidcUserAuthority because Spring Security 5.2 returns
            // each scope as a GrantedAuthority, which we don't care about.
            if (authority instanceof OidcUserAuthority) {
              OidcUserAuthority oidcUserAuthority = (OidcUserAuthority) authority;
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
  @Import(SecurityProblemSupport.class)
  @Order(1)
  public static class SpotifySecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    private final SecurityProblemSupport problemSupport;

    public SpotifySecurityConfigurerAdapter(
        SecurityProblemSupport problemSupport) {
      this.problemSupport = problemSupport;
    }

    protected void configure(HttpSecurity http) throws Exception {// @formatter:off
      RequestMatcher matcher;
      http.requestMatchers()
            .antMatchers("/login/oauth2/code/spotify",
                "/login/oauth2/authorization/spotify")
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

  @Bean
  OAuth2AuthorizedClientRepository oauth2AuthorizedClientRepository() {
    return new HttpSessionOAuth2AuthorizedClientRepository();
  }

  @Configuration
  @Order(2)
  @Import(SecurityProblemSupport.class)
  public static class ApiSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

    private final SecurityProblemSupport problemSupport;

    public ApiSecurityConfigurationAdapter(
        SecurityProblemSupport problemSupport) {
      this.problemSupport = problemSupport;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {// @formatter:off



      http.authorizeRequests()
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
                  .defaultSuccessUrl("/home")
                  .authorizationEndpoint()
                  .baseUri("/login/oauth2/authorization")
                  .and()
                  .failureUrl("/home")

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


  }
}