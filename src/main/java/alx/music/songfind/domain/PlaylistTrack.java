package alx.music.songfind.domain;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaylistTrack {

  private Date added;
  private PlaylistTrackUser addedBy;
  private Track track;


  @Data
  @NoArgsConstructor
  public static class PlaylistTrackUser {
    private String id;
    private ExternalUrl externalUrl;
  }

}
