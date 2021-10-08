package alx.music.songfind.application.port.out;

import alx.music.songfind.domain.Playlist;
import reactor.core.publisher.Flux;

public interface GetCurrentUserPlaylistPort {

  Flux<Playlist> getCurrentUserPlaylists();


}
