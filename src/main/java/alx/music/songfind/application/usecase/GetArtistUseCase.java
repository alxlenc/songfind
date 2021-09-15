package alx.music.songfind.application.usecase;

import alx.music.songfind.application.port.in.SearchArtistQuery;
import alx.music.songfind.application.port.out.SearchArtistPort;
import alx.music.songfind.common.UseCase;
import alx.music.songfind.common.config.SearchArtistConfiguration;
import alx.music.songfind.domain.Artist;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@UseCase
@RequiredArgsConstructor
class GetArtistUseCase implements SearchArtistQuery {

  private final SearchArtistPort searchArtistPort;
  private final SearchArtistConfiguration searchArtistConfiguration;

  @Override
  public Flux<Artist> searchArtist(String query) {
    return this.searchArtistPort.searchArtists(query, this.searchArtistConfiguration.getMaxSearchResults());
  }
}
