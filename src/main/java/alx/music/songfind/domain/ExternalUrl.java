package alx.music.songfind.domain;

import lombok.Getter;

@Getter
public class ExternalUrl {


  private final String spotify;

  public ExternalUrl(String spotify) {
    this.spotify = spotify;
  }
}
