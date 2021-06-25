package alx.music.songfind.adapter.out.web.spotify;

import alx.music.songfind.adapter.out.web.spotify.mapper.ArtistMapper;
import alx.music.songfind.adapter.out.web.spotify.model.Paginated;
import alx.music.songfind.adapter.out.web.spotify.model.SearchResults;
import alx.music.songfind.application.port.out.SearchArtistPort;
import alx.music.songfind.domain.Artist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ArtistAdapter implements SearchArtistPort {

  private final SpotifyClient spotifyClient;
  private final ArtistMapper artistMapper;

  @Override
  public Flux<Artist> searchArtists(String name, int limit) {
    return this.spotifyClient.searchArtists(name, limit)
        .map(SearchResults::getArtists)
        .flatMapIterable(Paginated::getItems)
        .map(this.artistMapper::toDomain);

  }
}
