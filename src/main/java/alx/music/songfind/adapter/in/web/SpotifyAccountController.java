package alx.music.songfind.adapter.in.web;

import alx.music.songfind.adapter.in.web.mapper.SpotifyUserMapper;
import alx.music.songfind.adapter.in.web.model.SpotifyUser;
import alx.music.songfind.application.port.in.GetSpotifyUserQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/spotify")
@RequiredArgsConstructor
class SpotifyAccountController {

  private final GetSpotifyUserQuery userQuery;
  private final SpotifyUserMapper spotifyUserMapper;

  @GetMapping("/account")
  public SpotifyUser getSpotifyAccount() {
    return this.userQuery.getCurrentAccount()
        .map(this.spotifyUserMapper::toViewModel).block();
  }

}