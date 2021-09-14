package alx.music.songfind.application.port.out;

import alx.music.songfind.domain.PlaylistTrack;
import reactor.core.publisher.Flux;

public interface GetPlaylistTracksPort {

  Flux<PlaylistTrack> getPlaylistTracks(final String playlistId);


}
