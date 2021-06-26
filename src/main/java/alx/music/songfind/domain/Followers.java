package alx.music.songfind.domain;

import lombok.Getter;

@Getter
public class Followers {

  private final String href;
  private final Integer total;

  public Followers(String href, Integer total) {
    this.href = href;
    this.total = total;
  }
}
