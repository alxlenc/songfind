package alx.music.songfind.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Artist {
  private final String id;
  private final String name;
}
