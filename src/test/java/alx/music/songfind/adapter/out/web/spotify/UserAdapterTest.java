package alx.music.songfind.adapter.out.web.spotify;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import alx.music.songfind.adapter.out.web.spotify.mapper.UserMapper;
import alx.music.songfind.adapter.out.web.spotify.mapper.UserMapperImpl;
import alx.music.songfind.adapter.out.web.spotify.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class UserAdapterTest {

  @InjectMocks
  private UserAdapter sut;
  @Spy
  private final UserMapper userMapper = new UserMapperImpl();
  @Mock
  private SpotifyClient spotifyClient;

  @Test
  void getCurrentAccount() {
    // Arrange
    User user = User.builder().id("uid").name("spotify-username").build();
    when(this.spotifyClient.getCurrentAccount()).thenReturn(Mono.just(user));
    // Act
    Mono<alx.music.songfind.domain.User> actual = sut.getCurrentAccount();
    // Assert
    StepVerifier.create(actual)
        .expectSubscription()
        .assertNext(u -> assertThat(u.getName()).isEqualTo("spotify-username"))
        .verifyComplete();
  }

}