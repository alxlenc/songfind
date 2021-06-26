package alx.music.songfind.domain;

import java.util.Collections;
import java.util.List;
import lombok.Getter;

@Getter
public class Recommendations {

  private final List<Seed> seeds;
  private final List<Track> tracks;

  public Recommendations(List<Seed> seeds, List<Track> tracks) {
    this.seeds = seeds != null ? seeds : Collections.emptyList();
    this.tracks = tracks != null ? tracks : Collections.emptyList();
  }
}
