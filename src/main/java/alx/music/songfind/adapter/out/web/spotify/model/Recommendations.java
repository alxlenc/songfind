package alx.music.songfind.adapter.out.web.spotify.model;


import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class Recommendations {

  List<Seed> seeds;
  List<Track> tracks;

}
