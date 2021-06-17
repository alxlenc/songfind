package alx.music.songfind.application.port.in;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetRecommendationsCommand {

  private final List<String> artistIds;

}
