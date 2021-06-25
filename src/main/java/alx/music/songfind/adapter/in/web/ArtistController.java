package alx.music.songfind.adapter.in.web;

import alx.music.songfind.application.port.in.SearchArtistQuery;
import alx.music.songfind.domain.Artist;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/artist")
class ArtistController {

  private final SearchArtistQuery searchArtistQuery;

  @GetMapping
  public List<Artist> searchArtist(@RequestParam String query) {
    return this.searchArtistQuery.searchArtist(query).collectList().block();
  }

}
