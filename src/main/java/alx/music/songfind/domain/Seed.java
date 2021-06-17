package alx.music.songfind.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Builder
@RequiredArgsConstructor
@Getter
public class Seed {

  private final String id;
  private final String href;
  private final String type;
  private final Integer initialPoolSize;

}
