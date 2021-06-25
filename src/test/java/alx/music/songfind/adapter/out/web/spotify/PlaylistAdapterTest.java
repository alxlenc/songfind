package alx.music.songfind.adapter.out.web.spotify;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import alx.music.songfind.adapter.out.web.spotify.mapper.PlaylistMapper;
import alx.music.songfind.adapter.out.web.spotify.mapper.PlaylistMapperImpl;
import alx.music.songfind.adapter.out.web.spotify.mapper.PlaylistTrackMapper;
import alx.music.songfind.adapter.out.web.spotify.mapper.PlaylistTrackMapperImpl;
import alx.music.songfind.adapter.out.web.spotify.model.ExternalUrl;
import alx.music.songfind.adapter.out.web.spotify.model.PlaylistTrack;
import alx.music.songfind.adapter.out.web.spotify.model.PlaylistTrack.PlaylistTrackUser;
import alx.music.songfind.adapter.out.web.spotify.model.Track;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class PlaylistAdapterTest {

  @InjectMocks
  private PlaylistAdapter sut;

  @Spy
  private final PlaylistMapper playlistMapper = new PlaylistMapperImpl();
  @Spy
  private final PlaylistTrackMapper playlistTrackMapper = new PlaylistTrackMapperImpl();
  @Mock
  private SpotifyClient spotifyClient;
  @Mock
  private SpotifyPaginatedResourcePublisher publisher;

  @Test
  void getPlaylistTracks() {
    // Arrange
    Instant added = Instant.now();
    String userid = "userid";
    String externalUrl = "spotifyUrl";
    PlaylistTrack plTrack = PlaylistTrack.builder()
        .track(
            Track.builder().name("track1").build()
        ).added(added)
        .addedBy(new PlaylistTrackUser(userid, new ExternalUrl(externalUrl)))
        .build();

    when(this.publisher.publish(any())).thenReturn(Flux.just(plTrack));

    // Act
    Flux<alx.music.songfind.domain.PlaylistTrack> actual = this.sut.getPlaylistTracks("playlistId");

    //Assert
    StepVerifier.create(actual)
        .expectSubscription()
        .assertNext(playlistTrack -> {
          assertThat(playlistTrack.getAdded().toEpochMilli()).isEqualTo(added.toEpochMilli());
          alx.music.songfind.domain.PlaylistTrack.PlaylistTrackUser addedBy = playlistTrack
              .getAddedBy();
          assertThat(addedBy.getId()).isEqualTo(userid);
          assertThat(addedBy.getExternalUrl().getSpotify()).isEqualTo(externalUrl);
        })
        .verifyComplete();
  }
}