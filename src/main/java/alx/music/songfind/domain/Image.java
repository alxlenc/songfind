package alx.music.songfind.domain;


import lombok.Getter;

@Getter
public class Image {

  private final String url;
  private Integer width;
  private Integer height;

  public Image(String url) {
    this.url = url;
  }

  public Image(String url, Integer width, Integer height) {
    this.url = url;
    this.width = width;
    this.height = height;
  }
}
