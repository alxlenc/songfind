package alx.music.songfind.adapter.out.web.spotify.model;

public enum SearchType {
  ARTIST("artist")
  ;

  SearchType(String type) {
    this.type = type;
  }

  private final String type;

  public String getType() {
    return type;
  }
}
