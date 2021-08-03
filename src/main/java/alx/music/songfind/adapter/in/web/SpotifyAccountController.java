package alx.music.songfind.adapter.in.web;

import alx.music.songfind.application.port.in.GetSpotifyUserQuery;
import alx.music.songfind.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/spotify")
@RequiredArgsConstructor
class SpotifyAccountController {

  private final GetSpotifyUserQuery userQuery;


  @GetMapping("/account")
  public User getSpotifyAccount() {
    return this.userQuery.getCurrentAccount().block();
  }

}