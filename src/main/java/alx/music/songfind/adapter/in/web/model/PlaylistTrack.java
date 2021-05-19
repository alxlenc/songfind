package alx.music.songfind.adapter.in.web.model;

import java.util.List;
import lombok.Data;

@Data
public class PlaylistTrack {

  private String id;
  private String name;
  private String added;
  private String addedBy;
  private Integer duration;
  private Integer popularity;
  private List<Artists> artists;
  private String previewUrl;
  private String externalUrl;

}
