package alx.music.songfind.application.port.out;

import alx.music.songfind.domain.User;
import reactor.core.publisher.Mono;

public interface GetUserPort {

  Mono<User> getCurrentAccount();
}
