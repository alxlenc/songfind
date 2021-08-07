package alx.music.songfind.application.port.in;

import alx.music.songfind.domain.Recommendations;
import reactor.core.publisher.Mono;

public interface GetRecommendationsQuery {

  Mono<Recommendations> getRecommendations(
      GetRecommendationsQueryParam getRecommendationsQueryParam);

}
