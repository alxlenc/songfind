package alx.music.songfind.spotify.model;

import com.wrapper.spotify.model_objects.AbstractModelObject;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Image {
  private Integer height;
  private String url;
  private Integer width;
}
