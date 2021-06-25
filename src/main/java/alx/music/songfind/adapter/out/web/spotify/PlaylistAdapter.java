package alx.music.songfind.adapter.out.web.spotify;

import alx.music.songfind.adapter.out.web.spotify.mapper.PlaylistMapper;
import alx.music.songfind.adapter.out.web.spotify.mapper.PlaylistTrackMapper;
import alx.music.songfind.application.port.out.GetPLaylistPort;
import alx.music.songfind.domain.Playlist;
import alx.music.songfind.domain.PlaylistTrack;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
class PlaylistAdapter implements GetPLaylistPort {

  private final SpotifyClient spotifyClient;
  private final SpotifyPaginatedResourcePublisher paginatedPublisher;
  private final PlaylistMapper playlistMapper;
  private final PlaylistTrackMapper playlistTrackMapper;

  @Override
  public Flux<Playlist> getCurrentUserPlaylists() {
    return this.paginatedPublisher
        .publish(this.spotifyClient::getCurrentUserPlaylists)
        .map(this.playlistMapper::toDomain);
  }

  @Override
  public Flux<PlaylistTrack> getPlaylistTracks(String playlistId) {
    return this.paginatedPublisher
        .publish((offset) -> this.spotifyClient.getPlaylistTracks(offset, playlistId))
        .map(this.playlistTrackMapper::toDomain);

  }

}
