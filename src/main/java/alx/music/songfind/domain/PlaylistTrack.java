package alx.music.songfind.domain;

import java.time.Instant;
import lombok.Getter;

@Getter
public class PlaylistTrack {


  private final Track track;
  private final Instant added;
  private final PlaylistTrackUser addedBy;

  public PlaylistTrack(Track track, Instant added,
      PlaylistTrackUser addedBy) {
    this.track = track;
    this.added = added;
    this.addedBy = addedBy;
  }

  @Getter
  public static class PlaylistTrackUser {

    private final String id;
    private final ExternalUrl externalUrl;

    public PlaylistTrackUser(String id, ExternalUrl externalUrl) {
      this.id = id;
      this.externalUrl = externalUrl;
    }
  }

}
