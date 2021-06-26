package alx.music.songfind.domain;

import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Playlist {


  private final String id;
  private final String name;
  private final TracksInfo tracks;
  @Setter
  private List<Image> images = Collections.emptyList();

  public Playlist(String id, String name, TracksInfo tracks) {
    this.id = id;
    this.name = name;
    this.tracks = tracks;
  }

  @Getter
  public static class TracksInfo {

    private final Integer total;

    public TracksInfo(Integer total) {
      this.total = total;
    }
  }
}
