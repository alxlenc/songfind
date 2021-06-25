package alx.music.songfind.adapter.out.web.spotify;

import alx.music.songfind.adapter.out.web.spotify.mapper.UserMapper;
import alx.music.songfind.application.port.out.GetUserPort;
import alx.music.songfind.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
class UserAdapter implements GetUserPort {

  private final SpotifyClient spotifyClient;
  private final UserMapper userMapper;

  @Override
  public Mono<User> getCurrentAccount() {
    return this.spotifyClient.getCurrentAccount().map(this.userMapper::toDomain);
  }
}
