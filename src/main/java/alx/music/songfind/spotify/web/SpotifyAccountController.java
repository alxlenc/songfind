package alx.music.songfind.spotify.web;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
@RequestMapping("/api/spotify")
public class SpotifyAccountController {

  @Autowired
  WebClient webClient;

  @GetMapping("/account")
  public ResponseEntity<Map> getSpotifyAccount() {

    Map map = this.webClient.get().uri("/v1/me")

        .retrieve().bodyToMono(Map.class)
        .block();

    return new ResponseEntity<>(map, HttpStatus.OK);
  }

}