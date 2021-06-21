package alx.music.songfind.application.port.out;

import alx.music.songfind.domain.Artist;
import reactor.core.publisher.Flux;

public interface SearchArtistPort {

  Flux<Artist> searchArtists(String name, int limit);

}
