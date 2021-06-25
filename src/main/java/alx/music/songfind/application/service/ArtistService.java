package alx.music.songfind.application.service;

import alx.music.songfind.application.port.in.SearchArtistQuery;
import alx.music.songfind.application.port.out.SearchArtistPort;
import alx.music.songfind.domain.Artist;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
class ArtistService implements SearchArtistQuery {

  private final SearchArtistPort searchArtistPort;
  @Value("${application.artist.max-search-results}")
  private Integer maxSearchResults;

  @Override
  public Flux<Artist> searchArtist(String query) {
    return searchArtistPort.searchArtists(query, maxSearchResults);
  }
}
