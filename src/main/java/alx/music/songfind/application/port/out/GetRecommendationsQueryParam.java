package alx.music.songfind.application.port.out;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class GetRecommendationsQueryParam {

  private final List<String> artistIds;
  private final Integer minPopularity;
  private final Integer maxPopularity;
  private final Integer targetPopularity;

}
