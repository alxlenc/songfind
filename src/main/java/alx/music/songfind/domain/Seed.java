package alx.music.songfind.domain;


import lombok.Getter;

@Getter
public class Seed {

  private final String id;
  private final String href;
  private final SeedType type;
  private final Integer initialPoolSize;

  public Seed(String id, String href, SeedType type, Integer initialPoolSize) {
    this.id = id;
    this.href = href;
    this.type = type;
    this.initialPoolSize = initialPoolSize;
  }
}
