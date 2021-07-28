package alx.music.songfind.application.usecase;

import alx.music.songfind.application.port.in.GetRecommendationsQuery;
import alx.music.songfind.application.port.in.GetRecommendationsQueryParam;
import alx.music.songfind.application.port.out.RecommendationsPort;
import alx.music.songfind.domain.Recommendations;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class GetRecommendationsUseCase implements GetRecommendationsQuery {

  private final RecommendationsPort recommendationsPort;
  private final GetRecommendationsQueryParamMapper paramMapper;

  @Override
  public Recommendations getRecommendations(
      GetRecommendationsQueryParam getRecommendationsQueryParam) {
    var queryParam = this.paramMapper.map(getRecommendationsQueryParam);
    return this.recommendationsPort
        .getRecommendations(queryParam)
        .withTracksSortedByPopularityDesc();
  }

}
