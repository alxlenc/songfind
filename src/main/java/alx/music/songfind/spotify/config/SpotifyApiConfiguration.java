package alx.music.songfind.spotify.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "application.spotify-api", ignoreUnknownFields = false)
public class SpotifyApiConfiguration {


  @Bean
  WebClient webClient(
      ClientRegistrationRepository registrationRepository,
      OAuth2AuthorizedClientRepository authorizedClientRepository) {

    ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 = new ServletOAuth2AuthorizedClientExchangeFilterFunction(
        registrationRepository,
        authorizedClientRepository);

    oauth2.setDefaultClientRegistrationId("spotify");
    WebClient client = WebClient.builder()
        .baseUrl("https://api.spotify.com")
        .apply(oauth2.oauth2Configuration())
        .build();
    return client;
  }

}
