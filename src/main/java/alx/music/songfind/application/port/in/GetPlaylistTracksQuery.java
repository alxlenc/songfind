package alx.music.songfind.application.port.in;

import alx.music.songfind.domain.PlaylistTrack;
import reactor.core.publisher.Flux;

public interface GetPlaylistTracksQuery {

  Flux<PlaylistTrack> getPlaylistTracks(String playlistId);
}
