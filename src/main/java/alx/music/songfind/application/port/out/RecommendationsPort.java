package alx.music.songfind.application.port.out;

import alx.music.songfind.domain.Recommendations;

public interface RecommendationsPort {

  Recommendations getRecommendations(GetRecommendationsQueryParam queryParam);

}
