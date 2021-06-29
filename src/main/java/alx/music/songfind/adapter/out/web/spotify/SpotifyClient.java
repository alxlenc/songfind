package alx.music.songfind.adapter.out.web.spotify;


import static alx.music.songfind.adapter.out.web.spotify.model.SearchType.ARTIST;

import alx.music.songfind.adapter.out.web.spotify.model.Paginated;
import alx.music.songfind.adapter.out.web.spotify.model.Playlist;
import alx.music.songfind.adapter.out.web.spotify.model.PlaylistTrack;
import alx.music.songfind.adapter.out.web.spotify.model.Recommendations;
import alx.music.songfind.adapter.out.web.spotify.model.SearchResults;
import alx.music.songfind.adapter.out.web.spotify.model.User;
import java.util.List;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Slf4j
@Component
public class SpotifyClient {

  @Getter
  private final int pageSize = 50;
  private final WebClient webClient;

  public SpotifyClient(WebClient webClient) {
    this.webClient = webClient;
  }

  public Mono<User> getCurrentAccount() {
    return this.webClient.get()
        .uri("/v1/me")
        .retrieve().bodyToMono(
            new ParameterizedTypeReference<User>() {
            });
  }

  public Mono<Paginated<Playlist>> getCurrentUserPlaylists(int offset) {
    return this.webClient.get().uri(uriBuilder -> uriBuilder.path("/v1/me/playlists")
        .queryParam("offset", String.valueOf(offset))
        .queryParam("limit", String.valueOf(this.pageSize)).build())
        .retrieve().bodyToMono(new ParameterizedTypeReference<Paginated<Playlist>>() {
        });
  }

  public Mono<Paginated<PlaylistTrack>> getPlaylistTracks(int offset, String playlistId) {
    return this.webClient.get().uri(uriBuilder ->
        uriBuilder.path("/v1/playlists/{playlistId}/tracks")
            .queryParam("offset", String.valueOf(offset))
            .build(playlistId))
        .retrieve().bodyToMono(new ParameterizedTypeReference<Paginated<PlaylistTrack>>() {
        });
  }

  public Mono<Recommendations> getRecommendations(List<String> artistIds) {
    return this.webClient.get().uri(uriBuilder -> uriBuilder.path("/v1/recommendations")
        .queryParam("seed_artists", artistIds)
        .build()).retrieve().bodyToMono(Recommendations.class);
  }

  public Mono<SearchResults> searchArtists(String name, int limit) {
    return this.webClient.get().uri(uriBuilder -> uriBuilder.path("/v1/search")
        .queryParam("q", name)
        .queryParam("type", ARTIST.getType())
        .queryParam("limit", limit)
        .build()).retrieve().bodyToMono(SearchResults.class);
  }
}
