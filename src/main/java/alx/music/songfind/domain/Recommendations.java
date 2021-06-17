package alx.music.songfind.domain;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Builder
@RequiredArgsConstructor
@Getter
public class Recommendations {

  private final List<Seed> seeds;
  private final List<Track> tracks;

}
