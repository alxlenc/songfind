package alx.music.songfind.adapter.out.web.spotify;

import alx.music.songfind.adapter.out.web.spotify.mapper.PlaylistMapper;
import alx.music.songfind.application.port.out.GetCurrentUserPlaylistPort;
import alx.music.songfind.domain.Playlist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
class CurrentUserPlaylistAdapter implements GetCurrentUserPlaylistPort {

  private final SpotifyClient spotifyClient;
  private final SpotifyPaginatedResourcePublisher paginatedPublisher;
  private final PlaylistMapper playlistMapper;


  @Override
  public Flux<Playlist> getCurrentUserPlaylists() {
    return this.paginatedPublisher.publish(
            this.spotifyClient::getCurrentUserPlaylists)
        .map(this.playlistMapper::toDomain);
  }
}
