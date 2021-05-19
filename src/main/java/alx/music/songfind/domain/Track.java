package alx.music.songfind.domain;


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
  private Integer duration;
  private Integer trackNumber;
  private List<Artist> artists;
  private Integer popularity;
  private ExternalIds externalIds;
  private ExternalUrl externalUrl;
  private String previewUrl;

  private Album album;

  @Data
  public static class ExternalIds {
    private String isrc;
  }

}

