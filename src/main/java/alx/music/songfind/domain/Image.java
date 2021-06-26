package alx.music.songfind.domain;


import lombok.Getter;

@Getter
public class Image {

  private final String url;
  private int width;
  private int height;

  public Image(String url) {
    this.url = url;
  }

  public Image(String url, int width, int height) {
    this.url = url;
    this.width = width;
    this.height = height;
  }
}
