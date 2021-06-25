package alx.music.songfind.application.port.out;

import alx.music.songfind.application.port.in.GetRecommendationsCommand;
import alx.music.songfind.domain.Recommendations;

public interface RecommendationsPort {

  Recommendations getRecoRecommendations(GetRecommendationsCommand params);

}
