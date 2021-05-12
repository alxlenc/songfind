package alx.music.songfind.adapter.out.web.spotify.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class SpotifyConfiguration {

  private static final String CLIENT_REGISTRATION_ID = "spotify";

  @Bean
  public WebClient spotifyWebClient(
      ClientRegistrationRepository registrationRepository,
      OAuth2AuthorizedClientRepository authorizedClientRepository,
      @Value("${application.spotify-api.base-url}") String baseUrl) {
    ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 = new ServletOAuth2AuthorizedClientExchangeFilterFunction(
        registrationRepository,
        authorizedClientRepository);
    oauth2.setDefaultClientRegistrationId(CLIENT_REGISTRATION_ID);
    return WebClient.builder()
        .baseUrl(baseUrl)
        .apply(oauth2.oauth2Configuration())
        .build();
  }

}
