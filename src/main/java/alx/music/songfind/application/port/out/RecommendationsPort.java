package alx.music.songfind.application.port.out;

import alx.music.songfind.domain.Recommendations;
import java.util.List;

public interface RecommendationsPort {

  Recommendations getRecommendations(List<String> artistIds);

}
