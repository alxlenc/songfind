package alx.music.songfind.adapter.in.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seed {

  private String id;
  private String href;
  private String type;
  private Integer initialPoolSize;

}
