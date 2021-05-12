package alx.music.songfind.adapter.out.web.spotify;

import alx.music.songfind.adapter.out.web.spotify.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/spotify")
class SpotifyAccountController {

  private final SpotifyClient spotifyClient;

  public SpotifyAccountController(
      SpotifyClient spotifyClient) {
    this.spotifyClient = spotifyClient;
  }

  @GetMapping("/account")
  public User getSpotifyAccount() {
    return this.spotifyClient.getCurrentAccount().block();
  }

}