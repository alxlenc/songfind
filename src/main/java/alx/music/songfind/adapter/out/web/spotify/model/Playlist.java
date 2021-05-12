package alx.music.songfind.adapter.out.web.spotify.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Playlist {

  private String id;
  private String name;
  private List<Image> images;
  private TracksInfo tracks;

  @Data
  public static class TracksInfo {
     private Integer total;
  }
}
