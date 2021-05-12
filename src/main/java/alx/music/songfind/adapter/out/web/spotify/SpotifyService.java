package alx.music.songfind.adapter.out.web.spotify;

import alx.music.songfind.adapter.out.web.spotify.mapper.PlaylistMapper;
import alx.music.songfind.adapter.out.web.spotify.mapper.PlaylistTrackMapper;
import alx.music.songfind.adapter.out.web.spotify.mapper.UserMapper;
import alx.music.songfind.application.port.out.GetPLaylistPort;
import alx.music.songfind.application.port.out.GetUserPort;
import alx.music.songfind.domain.Playlist;
import alx.music.songfind.domain.PlaylistTrack;
import alx.music.songfind.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
class SpotifyService implements GetPLaylistPort, GetUserPort {

  private final SpotifyClient spotifyClient;
  private final SpotifyPaginatedResourcePublisher paginatedPublisher;
  private final PlaylistMapper playlistMapper;
  private final PlaylistTrackMapper playlistTrackMapper;
  private final UserMapper userMapper;

  @Override
  public Mono<User> getCurrentAccount() {
    return this.spotifyClient.getCurrentAccount().map(this.userMapper::toDomain);
  }

  @Override
  public Flux<Playlist> getCurrentUserPlaylists() {
    return paginatedPublisher
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
