package alx.music.songfind.application.port.out;

import alx.music.songfind.adapter.out.web.spotify.model.User;
import reactor.core.publisher.Mono;

public interface GetUserPort {

  Mono<User> getCurrentAccount();
}
