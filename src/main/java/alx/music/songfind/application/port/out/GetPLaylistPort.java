package alx.music.songfind.application.port.out;

import alx.music.songfind.domain.Playlist;
import alx.music.songfind.domain.PlaylistTrack;
import reactor.core.publisher.Flux;

public interface GetPLaylistPort {

  Flux<Playlist> getCurrentUserPlaylists();

  Flux<PlaylistTrack> getPlaylistTracks(String playlistId);
}
