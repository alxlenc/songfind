package alx.music.songfind.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import alx.music.songfind.application.port.out.GetPLaylistPort;
import alx.music.songfind.domain.Image;
import alx.music.songfind.domain.Playlist;
import alx.music.songfind.domain.Playlist.TracksInfo;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class PlaylistServiceTest {


  @Mock
  GetPLaylistPort getPLaylistPort;

  @InjectMocks
  PlaylistService sut;

  @Test
  void given_playlists_with_unsorted_sorted_image_sizes_then_sort_with_success() {

    List<Image> images = List.of(
        new Image("url", 1, 1),
        new Image("url", 3, 1),
        new Image("url", 2, 1),
        new Image("url")
    );
    Playlist pl = new Playlist("pl1", "my playlist", new TracksInfo(10));
    pl.setImages(images);

    when(this.getPLaylistPort.getCurrentUserPlaylists()).thenReturn(Flux.just(pl));

    Flux<alx.music.songfind.domain.Playlist> actual = this.sut
        .getCurrentUserPlaylists();

    StepVerifier.create(actual)
        .expectSubscription()
        .assertNext(
            actualPl -> assertThat(actualPl.getImages())
                .extracting(Image::getWidth).containsSequence(0, 1, 2, 3))
        .verifyComplete();

  }

}