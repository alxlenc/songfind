package alx.music.songfind.application.usecase;

import alx.music.songfind.application.port.in.GetSpotifyUserQuery;
import alx.music.songfind.application.port.out.GetSpotifyUserPort;
import alx.music.songfind.common.UseCase;
import alx.music.songfind.domain.User;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@UseCase
@RequiredArgsConstructor
class GetSpotifyUserUseCase implements GetSpotifyUserQuery {

  private final GetSpotifyUserPort getSpotifyUserPort;

  @Override
  public Mono<User> getCurrentAccount() {
    return this.getSpotifyUserPort.getCurrentAccount();
  }
}
