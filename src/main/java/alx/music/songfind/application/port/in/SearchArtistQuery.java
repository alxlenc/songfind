package alx.music.songfind.application.port.in;

import alx.music.songfind.domain.Artist;
import reactor.core.publisher.Flux;

public interface SearchArtistQuery {

  Flux<Artist> searchArtist(String query);

}
