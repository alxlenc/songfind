package alx.music.songfind.adapter.out.web.spotify;

import static org.assertj.core.api.Assertions.assertThat;

import alx.music.songfind.application.port.out.GetRecommendationsQueryParam;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class SpotifyClientTest {

  private SpotifyClient sut;

  @Test
  void getCurrentAccount() {
    // Arrange
    String path = "/v1/me";

    // Assert
    this.sut = this.supplySpotifyClientWithRequestAssertions(
        clientRequest -> assertThat(clientRequest.url().getPath()).isEqualTo(path));

    // Act
    StepVerifier.create(this.sut.getCurrentAccount()).expectSubscription().verifyComplete();

  }

  @Test
  void getCurrentUserPlaylistsRequestIsWellFormed() {
    // Arrange
    int offset = 20;
    String path = "/v1/me/playlists";

    // Assert
    this.sut = this.supplySpotifyClientWithRequestAssertions(clientRequest -> {
      assertThat(clientRequest.url().getPath()).isEqualTo(path);
      Map<String, String> actualParams = this.extractQueryParams(clientRequest);
      assertThat(actualParams.get("offset")).isEqualTo(Integer.toString(offset));
    });

    // Act
    StepVerifier.create(this.sut.getCurrentUserPlaylists(offset))
        .expectSubscription()
        .verifyComplete();
  }

  @Test
  void getPlaylistTracksRequestIsWellFormed() {

    // Arrange
    int offset = 10;
    String playlistId = "playlist1";

    // Assert
    this.sut = this.supplySpotifyClientWithRequestAssertions((clientRequest -> {

      assertThat(clientRequest.url().getPath()).contains(playlistId);
      Map<String, String> actualParams = this.extractQueryParams(clientRequest);
      assertThat(actualParams.get("offset")).isEqualTo(Integer.toString(offset));

    }));

    // Act
    StepVerifier.create(this.sut.getPlaylistTracks(offset, playlistId))
        .expectSubscription()
        .verifyComplete();
  }


  @Test
  void getRecommendationsSetsRequestIsWellFormed() {
    // Arrange
    GetRecommendationsQueryParam queryParams = GetRecommendationsQueryParam.builder()
        .artistIds(List.of("1A", "2B"))
        .minPopularity(50)
        .maxPopularity(70)
        .targetPopularity(70)
        .build();

    // Assert
    this.sut = this.supplySpotifyClientWithRequestAssertions((clientRequest) -> {
      Map<String, String> actualParams = this.extractQueryParams(clientRequest);
      assertThat(actualParams.get("seed_artists")).isEqualTo("1A,2B");
      assertThat(actualParams.get("min_popularity")).isEqualTo("50");
      assertThat(actualParams.get("max_popularity")).isEqualTo("70");
      assertThat(actualParams.get("target_popularity")).isEqualTo("70");
    });
    // Act
    StepVerifier.create(this.sut.getRecommendations(queryParams))
        .expectSubscription()
        .verifyComplete();
  }

  private Map<String, String> extractQueryParams(ClientRequest clientRequest) {
    List<NameValuePair> params = URLEncodedUtils
        .parse(clientRequest.url(), StandardCharsets.UTF_8);
    Map<String, String> actualParams = params.stream().collect(Collectors
        .toMap(NameValuePair::getName, nvp -> Optional.ofNullable(nvp.getValue()).orElse(""),
            (s1, s2) -> String.join(",", s1, s2)));
    return actualParams;
  }

  private SpotifyClient supplySpotifyClientWithRequestAssertions(Consumer<ClientRequest> asserter) {
    WebClient webClient = WebClient.builder()
        .exchangeFunction(clientRequest -> {
          asserter.accept(clientRequest);
          return Mono.just(ClientResponse.create(HttpStatus.OK).build());
        })
        .build();
    return new SpotifyClient(webClient);
  }
}