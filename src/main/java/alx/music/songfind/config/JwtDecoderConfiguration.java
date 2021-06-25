package alx.music.songfind.config;

import alx.music.songfind.security.oauth2.AudienceValidator;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
@Profile("!test")
public class JwtDecoderConfiguration {

  @Bean
  JwtDecoder jwtDecoder(
      @Value("${spring.security.oauth2.client.provider.songfind.issuer-uri}") String issuerUri,
      @Value("${application.auth.audience}") String applicationAudience) {
    NimbusJwtDecoder jwtDecoder = JwtDecoders
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
