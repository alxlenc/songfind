package alx.music.songfind.adapter.in.web;

import alx.music.songfind.application.port.in.GetPlaylistQuery;
import alx.music.songfind.application.port.in.GetPlaylistTracksQuery;
import alx.music.songfind.domain.Playlist;
import alx.music.songfind.domain.PlaylistTrack;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/playlist")
@RequiredArgsConstructor
public class PlaylistController {

  private final GetPlaylistQuery playlistQuery;
  private final GetPlaylistTracksQuery playlistTracksQuery;

  @GetMapping("/loggedUser")
  public List<Playlist> getLoggedUserPlaylists() {
    return this.playlistQuery.getCurrentUserPlaylists()
        .collectList()
        .block();
  }

  @GetMapping("/{playlistId}/tracks")
  public List<PlaylistTrack> getPlaylistTracks(@PathVariable String playlistId) {
    return this.playlistTracksQuery.getPlaylistTracks(playlistId)
        .collectList()
        .block();
  }

}
