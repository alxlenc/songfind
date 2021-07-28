package alx.music.songfind.application.port.in;

import alx.music.songfind.domain.Recommendations;

public interface GetRecommendationsQuery {

  Recommendations getRecommendations(GetRecommendationsQueryParam getRecommendationsQueryParam);

}
