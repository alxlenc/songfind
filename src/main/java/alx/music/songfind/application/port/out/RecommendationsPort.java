package alx.music.songfind.application.port.out;

import alx.music.songfind.domain.Recommendations;
import reactor.core.publisher.Mono;

public interface RecommendationsPort {

  Mono<Recommendations> getRecommendations(GetRecommendationsQueryParam queryParam);

}
