package alx.music.songfind.application.usecase;

import alx.music.songfind.application.port.in.GetRecommendationsCommand;
import alx.music.songfind.application.port.in.GetRecommendationsQuery;
import alx.music.songfind.application.port.out.RecommendationsPort;
import alx.music.songfind.domain.Recommendations;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class GetRecommendationsUseCase implements GetRecommendationsQuery {

  private final RecommendationsPort recommendationsPort;

  @Override
  public Recommendations getRecommendations(GetRecommendationsCommand getRecommendationsCommand) {
    return this.recommendationsPort
        .getRecommendations(getRecommendationsCommand.getArtistIds())
        .withTracksSortedByPopularityDesc();
  }

}
