package alx.music.songfind.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class RecommendationsTest {

  @Test
  public void withTracksSortedByPopularityDescReturnsRecommendationsWithSortedTracks() {
    // Arrange
    List<Track> unsortedTracks = List.of(
        this.createTrack(2),
        this.createTrack(1),
        this.createTrack(3));
    // Act
    Recommendations recommendations = new Recommendations(Collections.emptyList(), unsortedTracks)
        .withTracksSortedByPopularityDesc();
    // Assert
    List<Track> actualTracks = recommendations.getTracks();
    assertThat(actualTracks).extracting(Track::getPopularity).containsSequence(3, 2, 1);
  }

  private Track createTrack(int popularity) {
    Artist artist1 = new Artist("ArtistId" + popularity, "Artist Name" + popularity);
    Track track1 = new Track("TrackId" + popularity, "Track Name" + popularity, 123,
        List.of(artist1));
    track1.setPopularity(popularity);
    return track1;
  }

}