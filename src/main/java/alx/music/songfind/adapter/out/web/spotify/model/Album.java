package alx.music.songfind.adapter.out.web.spotify.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Album {

  @JsonProperty("album_type")
  private AlbumType type;

}
