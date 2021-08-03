package alx.music.songfind.application.port.in;

import alx.music.songfind.domain.User;
import reactor.core.publisher.Mono;

public interface GetSpotifyUserQuery {

  Mono<User> getCurrentAccount();
}
