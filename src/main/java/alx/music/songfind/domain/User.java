package alx.music.songfind.domain;

import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {


  private final String id;
  private final String name;
  private final String email;
  private String birthdate;
  private String country;
  private Followers followers;
  private String href;
  private List<Image> images = Collections.emptyList();
  private String uri;

  public User(String id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }

  public void setImages(List<Image> images) {
    this.images = images != null ? images : Collections.emptyList();
  }
}
