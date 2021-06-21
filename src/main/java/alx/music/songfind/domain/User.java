package alx.music.songfind.domain;

import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

  private String birthdate;
  private String country;
  private String name;
  private String email;
  private Followers followers;
  private String href;
  private String id;
  private List<Image> images = Collections.emptyList();
  private String uri;
}
