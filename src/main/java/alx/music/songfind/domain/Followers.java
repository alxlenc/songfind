package alx.music.songfind.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Followers {

  private String href;
  private Integer total;
}
