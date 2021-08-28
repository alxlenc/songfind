package alx.music.songfind.application.port.in;

import alx.music.songfind.domain.Playlist;
import reactor.core.publisher.Flux;

public interface GetUserPlaylistsQuery {

  Flux<Playlist> getCurrentUserPlaylists();

}
