package alx.music.songfind.adapter.out.web.spotify.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Seed {

  private String id;
  private String href;
  private String type;
  private Integer initialPoolSize;

}
