package alx.music.songfind.application.service;

import alx.music.songfind.application.port.in.GetUserQuery;
import alx.music.songfind.application.port.out.GetUserPort;
import alx.music.songfind.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService implements GetUserQuery {

  private final GetUserPort getUserPort;

  @Override
  public Mono<User> getCurrentAccount() {
    return getUserPort.getCurrentAccount();
  }
}
