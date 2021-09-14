package alx.music.songfind.adapter.out.web.spotify;

import alx.music.songfind.adapter.out.web.spotify.mapper.PlaylistTrackMapper;
import alx.music.songfind.application.port.out.GetPlaylistTracksPort;
import alx.music.songfind.domain.PlaylistTrack;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class PlaylistAdapter implements GetPlaylistTracksPort {

  private final SpotifyPaginatedResourcePublisher paginatedResourcePublisher;
  private final SpotifyClient spotifyClient;
  private final PlaylistTrackMapper playlistTrackMapper;

  @Override
  public Flux<PlaylistTrack> getPlaylistTracks(String playlistId) {
    return this.paginatedResourcePublisher
        .publish((offset) -> this.spotifyClient.getPlaylistTracks(offset, playlistId))
        .map(this.playlistTrackMapper::toDomain);
  }
}
