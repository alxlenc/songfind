package alx.music.songfind.adapter.in.web.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Track {

  private String id;
  private String name;
  private Integer duration;
  private List<Artist> artists;
  private Integer trackNumber;
  private Integer popularity;
  private ExternalIds externalIds;
  private ExternalUrl externalUrl;
  private String previewUrl;


  @Data
  @NoArgsConstructor
  public static class ExternalIds {

    private String isrc;

  }

}

