package alx.music.songfind.adapter.out.web.spotify.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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

  @JsonProperty("added_at")
  private Date added;
  @JsonProperty("added_by")
  private PlaylistTrackUser addedBy;
  private Track track;


  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class PlaylistTrackUser {
    private String id;
    @JsonProperty("external_urls")
    private ExternalUrl externalUrl;
  }

}
