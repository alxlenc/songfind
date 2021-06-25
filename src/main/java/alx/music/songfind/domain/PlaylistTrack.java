package alx.music.songfind.domain;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaylistTrack {

  private Instant added;
  private PlaylistTrackUser addedBy;
  private Track track;


  @Data
  @NoArgsConstructor
  public static class PlaylistTrackUser {

    private String id;
    private ExternalUrl externalUrl;
  }

}
