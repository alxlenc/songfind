package alx.music.songfind.adapter.out.web.spotify.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Followers {

  private String href;
  private Integer total;
}
