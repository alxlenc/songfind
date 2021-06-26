package alx.music.songfind.adapter.out.web.spotify.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Paginated<T> {

  private String href;
  private List<T> items;
  private Integer limit;
  private String previous;
  private String next;
  private Integer total;

}
