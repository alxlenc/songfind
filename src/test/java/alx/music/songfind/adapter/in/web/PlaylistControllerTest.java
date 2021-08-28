package alx.music.songfind.adapter.in.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import alx.music.songfind.adapter.in.web.mapper.PlaylistTrackViewModelMapperImpl;
import alx.music.songfind.application.port.in.GetPlaylistTracksQuery;
import alx.music.songfind.application.port.in.GetUserPlaylistsQuery;
import alx.music.songfind.config.TestSecurityConfiguration;
import alx.music.songfind.config.WithLoggedUser;
import alx.music.songfind.domain.ExternalUrl;
import alx.music.songfind.domain.Image;
import alx.music.songfind.domain.Playlist;
import alx.music.songfind.domain.Playlist.TracksInfo;
import alx.music.songfind.domain.PlaylistTrack;
import alx.music.songfind.domain.PlaylistTrack.PlaylistTrackUser;
import alx.music.songfind.domain.Track;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Flux;

@WebMvcTest(PlaylistController.class)
@Import(TestSecurityConfiguration.class)
class PlaylistControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private GetUserPlaylistsQuery getUserPlaylistsQuery;
  @MockBean
  private GetPlaylistTracksQuery playlistTracksQuery;

  @SpyBean
  private PlaylistTrackViewModelMapperImpl playlistTrackViewModelMapper;

  @Test
  void unauthenticatedCallToUserPlaylistsReturnsUnauthorized() throws Exception {
    this.mockMvc.perform(get("/api/playlist/loggedUser"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithLoggedUser
  void getUserPlaylistsReturnsOk() throws Exception {

    Playlist pl1 = this.getPlaylist(1);
    Playlist pl2 = this.getPlaylist(2);
    when(this.getUserPlaylistsQuery.getCurrentUserPlaylists()).thenReturn(Flux.just(pl1, pl2));

    // Act
    this.mockMvc.perform(get("/api/playlist/loggedUser"))
        // Assert
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value("Id1"))
        .andExpect(jsonPath("$[0].name").value("PlName1"))
        .andExpect(jsonPath("$[1].id").value("Id2"))
        .andExpect(jsonPath("$[1].name").value("PlName2"));

  }

  @Test
  @WithLoggedUser
  void getPlaylistTracksReturnsOk() throws Exception {
    // Arrange
    String playlistId = "playlistId1";
    PlaylistTrack track1 = this.getPlaylistTrack(1);
    PlaylistTrack track2 = this.getPlaylistTrack(2);
    when(this.playlistTracksQuery.getPlaylistTracks(playlistId)).thenReturn(
        Flux.just(track1, track2));

    // Act
    this.mockMvc.perform(get("/api/playlist/" + playlistId + "/tracks")).andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value("Track1"))
        .andExpect(jsonPath("$[1].id").value("Track2"))
    ;


  }

  private PlaylistTrack getPlaylistTrack(int id) {
    return new PlaylistTrack(new Track("Track" + id, "Song" + id, 112234,
        Collections.emptyList()), Instant.now(),
        new PlaylistTrackUser("User" + id, new ExternalUrl("url" + id)));
  }


  private Playlist getPlaylist(int id) {
    Playlist playlist = new Playlist("Id" + id, "PlName" + id, new TracksInfo(10));
    playlist.setImages(List.of(new Image("asdff", 12, 12)));
    return playlist;
  }
}