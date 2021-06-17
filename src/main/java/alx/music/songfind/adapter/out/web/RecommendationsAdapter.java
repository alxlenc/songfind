package alx.music.songfind.adapter.out.web;

import alx.music.songfind.adapter.out.web.spotify.SpotifyClient;
import alx.music.songfind.adapter.out.web.spotify.mapper.RecommendationsMapper;
import alx.music.songfind.application.port.in.GetRecommendationsCommand;
import alx.music.songfind.application.port.out.RecommendationsPort;
import alx.music.songfind.domain.Recommendations;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecommendationsAdapter implements RecommendationsPort {

  private final SpotifyClient spotifyClient;
  private final RecommendationsMapper recommendationsMapper;

  @Override
  public Recommendations getRecoRecommendations(GetRecommendationsCommand params) {
    return recommendationsMapper.toDomain(this.spotifyClient.getRecommendations(params).block());
  }
}
