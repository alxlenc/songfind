package alx.music.songfind.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

/**
 * This class allows you to run unit and integration tests without an IdP. Otherwise it will try to
 * call IdP/.well-known/openid-configuration endpoint
 */
@TestConfiguration
public class TestSecurityConfiguration {

  public static final String END_SESSION_ENDPOINT = "https://songfind/logout";
  private final List<ClientRegistration> clientRegistration;

  public TestSecurityConfiguration() {
    this.clientRegistration = List.of(
        this.clientRegistration().registrationId("spotify").build(),
        this.clientRegistration().registrationId("songfind").build()
    );
  }

  @Bean
  ClientRegistrationRepository clientRegistrationRepository() {
    return new InMemoryClientRegistrationRepository(this.clientRegistration);
  }

  private ClientRegistration.Builder clientRegistration() {
    Map<String, Object> metadata = new HashMap<>();
    metadata
        .put("end_session_endpoint", END_SESSION_ENDPOINT);

    return ClientRegistration
        .withRegistrationId("spotify")
        .redirectUri("{baseUrl}/{action}/oauth2/code/{registrationId}")
        .clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .scope("read:user")
        .authorizationUri("https://songfind/login/oauth/authorize")
        .tokenUri("https://songfind/login/oauth/access_token")
        .jwkSetUri("https://songfind/oauth/jwk")
        .userInfoUri("https://api.songfind/user")
        .providerConfigurationMetadata(metadata)
        .userNameAttributeName("id")
        .clientName("Client Name")
        .clientId("client-id")
        .clientSecret("client-secret");

  }
}
