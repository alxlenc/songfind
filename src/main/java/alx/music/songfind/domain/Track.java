package alx.music.songfind.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Track {

  private final String id;
  private final String name;
  private final Integer duration;
  private final List<Artist> artists;
  private Integer trackNumber;
  private Integer popularity;
  private ExternalIds externalIds;
  private ExternalUrl externalUrl;
  private String previewUrl;
  private Album album;

  public Track(String id, String name, Integer duration,
      List<Artist> artists) {
    this.id = id;
    this.name = name;
    this.duration = duration;
    this.artists = artists;
  }

  @Getter
  public static class ExternalIds {

    private final String isrc;

    public ExternalIds(String isrc) {
      this.isrc = isrc;
    }
  }

}

