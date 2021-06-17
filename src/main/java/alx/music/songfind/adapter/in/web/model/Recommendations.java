package alx.music.songfind.adapter.in.web.model;

import alx.music.songfind.domain.Track;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recommendations {

  List<Seed> seeds;
  List<Track> tracks;

}
