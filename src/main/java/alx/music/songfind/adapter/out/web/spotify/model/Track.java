package alx.music.songfind.adapter.out.web.spotify.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Track {

  private String id;
  private String name;
  @JsonProperty("duration_ms")
  private Integer duration;
  @JsonProperty("track_number")
  private Integer trackNumber;
  private List<Artist> artists;
  private Integer popularity;
  @JsonProperty("external_ids")
  private ExternalIds externalIds;
  private Album album;

  @Data
  public static class ExternalIds {
    private String isrc;
  }

}

