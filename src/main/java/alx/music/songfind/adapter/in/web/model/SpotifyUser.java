package alx.music.songfind.adapter.in.web.model;

import alx.music.songfind.domain.Followers;
import alx.music.songfind.domain.Image;
import java.util.Collections;
import java.util.List;
import lombok.Data;

@Data
public class SpotifyUser {

  private final String id;
  private final String name;
  private final String email;
  private String birthdate;
  private String country;
  private Followers followers;
  private String href;
  private List<Image> images = Collections.emptyList();
  private String uri;

}
