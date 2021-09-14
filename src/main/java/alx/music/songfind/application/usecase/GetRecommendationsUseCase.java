package alx.music.songfind.application.usecase;

import alx.music.songfind.application.port.in.GetRecommendationsQuery;
import alx.music.songfind.application.port.in.GetRecommendationsQueryParam;
import alx.music.songfind.application.port.out.RecommendationsPort;
import alx.music.songfind.common.UseCase;
import alx.music.songfind.domain.Recommendations;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@UseCase
@RequiredArgsConstructor
class GetRecommendationsUseCase implements GetRecommendationsQuery {

  private final RecommendationsPort recommendationsPort;
  private final GetRecommendationsQueryParamMapper paramMapper;

  @Override
  public Mono<Recommendations> getRecommendations(
      GetRecommendationsQueryParam getRecommendationsQueryParam) {
    var queryParam = this.paramMapper.map(getRecommendationsQueryParam);
    return this.recommendationsPort
        .getRecommendations(queryParam)
        .map(Recommendations::withTracksSortedByPopularityDesc);

  }

}
