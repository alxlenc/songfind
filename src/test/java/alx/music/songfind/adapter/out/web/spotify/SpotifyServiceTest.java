package alx.music.songfind.adapter.out.web.spotify;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import alx.music.songfind.adapter.out.web.spotify.mapper.PlaylistMapper;
import alx.music.songfind.adapter.out.web.spotify.mapper.PlaylistMapperImpl;
import alx.music.songfind.adapter.out.web.spotify.mapper.PlaylistTrackMapper;
import alx.music.songfind.adapter.out.web.spotify.mapper.PlaylistTrackMapperImpl;
import alx.music.songfind.adapter.out.web.spotify.mapper.UserMapper;
import alx.music.songfind.adapter.out.web.spotify.mapper.UserMapperImpl;
import alx.music.songfind.adapter.out.web.spotify.model.ExternalUrl;
import alx.music.songfind.adapter.out.web.spotify.model.PlaylistTrack;
import alx.music.songfind.adapter.out.web.spotify.model.PlaylistTrack.PlaylistTrackUser;
import alx.music.songfind.adapter.out.web.spotify.model.Track;
import alx.music.songfind.adapter.out.web.spotify.model.User;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class SpotifyServiceTest {

  @Spy
  private final PlaylistMapper playlistMapper = new PlaylistMapperImpl();
  @Spy
  private final PlaylistTrackMapper playlistTrackMapper = new PlaylistTrackMapperImpl();
  @Spy
  private final UserMapper userMapper = new UserMapperImpl();

  @Mock
  private SpotifyClient spotifyClient;
  @Mock
  private SpotifyPaginatedResourcePublisher publisher;

  @InjectMocks
  private SpotifyService sut;

  @Test
  void getCurrentAccount() {
    // Arrange
    User user = User.builder().id("uid").name("spotify-username").build();
    when(spotifyClient.getCurrentAccount()).thenReturn(Mono.just(user));
    // Act
    Mono<alx.music.songfind.domain.User> actual = sut.getCurrentAccount();
    // Assert
    StepVerifier.create(actual)
        .expectSubscription()
        .assertNext(u -> assertThat(u.getName()).isEqualTo("spotify-username"))
        .verifyComplete();
  }

  @Test
  void getPlaylistTracks() {
    // Arrange
    Date added = new Date();
    String userid = "userid";
    String externalUrl = "spotifyUrl";
    PlaylistTrack plTrack = PlaylistTrack.builder()
        .track(
            Track.builder().name("track1").build()
        ).added(added)
        .addedBy(new PlaylistTrackUser(userid, new ExternalUrl(externalUrl)))
        .build();

    when(publisher.publish(any())).thenReturn(Flux.just(plTrack));

    // Act
    Flux<alx.music.songfind.domain.PlaylistTrack> actual = sut.getPlaylistTracks("playlistId");

    //Assert
   StepVerifier.create(actual)
       .expectSubscription()
       .assertNext(playlistTrack -> {
         assertThat(playlistTrack.getAdded().getTime()).isEqualTo(added.getTime());
         alx.music.songfind.domain.PlaylistTrack.PlaylistTrackUser addedBy = playlistTrack
             .getAddedBy();
         assertThat(addedBy.getId()).isEqualTo(userid);
         assertThat(addedBy.getExternalUrl().getSpotify()).isEqualTo(externalUrl);
       })
       .verifyComplete();
  }
}