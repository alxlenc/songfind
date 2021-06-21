package alx.music.songfind.adapter.out.web.spotify.model;

import lombok.Data;

@Data
public class SearchResults {

  public Paginated<Artist> artists;
}
