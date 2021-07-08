package alx.music.songfind.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import alx.music.songfind.application.port.out.GetPLaylistPort;
import alx.music.songfind.domain.Image;
import alx.music.songfind.domain.Playlist;
import alx.music.songfind.domain.Playlist.TracksInfo;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class GetPlaylistUseCaseTest {

  @InjectMocks
  GetPlaylistUseCase sut;

  @Mock
  GetPLaylistPort getPLaylistPort;

  @Test
  void getPlaylistWithUnsortedImagesReturnsPlaylistWithSortedImages() {

    // Arrange
    List<Image> images = this.getImagesWithOrderByWidth(1, 3, 2, null);
    Playlist pl = new Playlist("pl1", "my playlist", new TracksInfo(10));
    pl.setImages(images);

    when(this.getPLaylistPort.getCurrentUserPlaylists()).thenReturn(Flux.just(pl));

    // Act
    Flux<alx.music.songfind.domain.Playlist> actual = this.sut
        .getCurrentUserPlaylists();

    StepVerifier.create(actual)
        // Assert
        .expectSubscription()
        .assertNext(
            actualPl -> assertThat(actualPl.getImages())
                .extracting(Image::getWidth).containsSequence(1, 2, 3, null))
        .verifyComplete();

  }

  private List<Image> getImagesWithOrderByWidth(Integer... widths) {
    return Arrays.stream(widths).map(width -> new Image("url", width, 1)).toList();
  }

}