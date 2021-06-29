package alx.music.songfind.adapter.out.web.spotify;

import alx.music.songfind.adapter.out.web.spotify.mapper.RecommendationsMapper;
import alx.music.songfind.application.port.out.RecommendationsPort;
import alx.music.songfind.domain.Recommendations;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class RecommendationsAdapter implements RecommendationsPort {

  private final SpotifyClient spotifyClient;
  private final RecommendationsMapper recommendationsMapper;

  @Override
  public Recommendations getRecommendations(List<String> artistIds) {
    return this.recommendationsMapper
        .toDomain(this.spotifyClient.getRecommendations(artistIds).block());
  }
}
