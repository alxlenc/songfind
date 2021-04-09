package alx.music.songfind.spotify.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Followers {

  private  String href;
  private  Integer total;
}
