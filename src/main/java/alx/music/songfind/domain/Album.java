package alx.music.songfind.domain;


import lombok.Getter;

@Getter
public class Album {

  private final AlbumType type;

  public Album(AlbumType type) {
    this.type = type;
  }
}
