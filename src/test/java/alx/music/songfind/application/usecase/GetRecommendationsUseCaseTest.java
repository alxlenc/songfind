package alx.music.songfind.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import alx.music.songfind.application.port.in.GetRecommendationsQueryParam;
import alx.music.songfind.application.port.out.RecommendationsPort;
import alx.music.songfind.domain.Artist;
import alx.music.songfind.domain.Recommendations;
import alx.music.songfind.domain.Track;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetRecommendationsUseCaseTest {

  @InjectMocks
  GetRecommendationsUseCase sut;
  @Mock
  private RecommendationsPort port;
  @Captor
  private ArgumentCaptor<alx.music.songfind.application.port.out.GetRecommendationsQueryParam> outputParamCaptor;
  @Spy
  private GetRecommendationsQueryParamMapper paramMapper = new GetRecommendationsQueryParamMapperImpl();

  @Test
  public void getRecommendationsUseCaseReturnsRecommendationsSorted() {
    // Arrange
    GetRecommendationsQueryParam inputParam = GetRecommendationsQueryParam.builder().build();
    Recommendations recommendations = this.createRecommendations();
    when(this.port.getRecommendations(any())).thenReturn(recommendations);
    // Act
    Recommendations actual = this.sut.getRecommendations(inputParam);
    // Assert
    assertThat(actual.getTracks()).extracting(Track::getPopularity).containsSequence(2, 1);
  }

  @Test
  public void getRecommendationsUseCaseMapsParametersOk() {
    // Arrange
    GetRecommendationsQueryParam inputQueryParam = GetRecommendationsQueryParam.builder()
        .artistIds(List.of("1", "2"))
        .minPopularity(50)
        .maxPopularity(70)
        .build();
    when(this.port.getRecommendations(any())).thenReturn(this.createRecommendations());
    // Act
    this.sut.getRecommendations(inputQueryParam);
    // Assert
    verify(this.port).getRecommendations(this.outputParamCaptor.capture());
    var outputQueryParam = this.outputParamCaptor
        .getValue();
    assertThat(outputQueryParam.getArtistIds()).containsSequence("1", "2");
    assertThat(outputQueryParam.getMinPopularity()).isEqualTo(50);
    assertThat(outputQueryParam.getMaxPopularity()).isEqualTo(70);
    assertThat(outputQueryParam.getTargetPopularity()).isEqualTo(70);

  }

  private Recommendations createRecommendations() {
    List<Track> tracks = List.of(this.createTrack(1), this.createTrack(2));
    return new Recommendations(Collections.emptyList(), tracks);
  }

  private Track createTrack(int popularity) {
    Track track = new Track("id", "name", 123, List.of(new Artist("id", "name")));
    track.setPopularity(popularity);
    return track;
  }
}