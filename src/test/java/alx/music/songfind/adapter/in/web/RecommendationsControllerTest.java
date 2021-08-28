package alx.music.songfind.adapter.in.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import alx.music.songfind.adapter.in.web.mapper.RecommendationsViewModelMapper;
import alx.music.songfind.adapter.in.web.mapper.RecommendationsViewModelMapperImpl;
import alx.music.songfind.adapter.in.web.util.CacheTemplate;
import alx.music.songfind.application.port.in.GetRecommendationsQuery;
import alx.music.songfind.application.port.in.GetRecommendationsQueryParam;
import alx.music.songfind.config.MockCacheConfiguration;
import alx.music.songfind.config.TestSecurityConfiguration;
import alx.music.songfind.config.WithLoggedUser;
import alx.music.songfind.domain.Recommendations;
import alx.music.songfind.domain.Track;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import reactor.core.publisher.Mono;

@WebMvcTest(RecommendationsController.class)
@Import({TestSecurityConfiguration.class, MockCacheConfiguration.class})
public class RecommendationsControllerTest {

  private static final String RECOMMENDATIONS_RESOURCE = "/api/recommendations";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private GetRecommendationsQuery getRecommendationsQuery;

  @SpyBean
  private CacheTemplate<GetRecommendationsQueryParam, Recommendations> recommendationsCache;

  @Captor
  private ArgumentCaptor<GetRecommendationsQueryParam> commandCaptor;

  @TestConfiguration
  static class RecommendationsControllerTestConfig {

    @Bean
    RecommendationsViewModelMapper recommendationsViewModelMapper() {
      return new RecommendationsViewModelMapperImpl();
    }
  }

  @Test
  void unauthorizedGetRecommendationsByPlaylistReturnsUnauthorized() throws Exception {
    this.mockMvc.perform(get(RECOMMENDATIONS_RESOURCE))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithLoggedUser
  void getRecommendationsForArtistsWithIdsReturnsOk() throws Exception {
    // Arrange
    when(this.getRecommendationsQuery.getRecommendations(any()))
        .thenReturn(Mono.just(this.createEmptyRecommendations()));

    // Act
    this.mockMvc.perform(get(RECOMMENDATIONS_RESOURCE)
            .param("seed_artists", "1", "2", "3"))
        // Assert
        .andExpect(status().isOk());

    verify(this.getRecommendationsQuery).getRecommendations(this.commandCaptor.capture());

    assertThat(this.commandCaptor.getValue().getArtistIds()).contains("1", "2", "3");

  }

  private Recommendations createEmptyRecommendations() {
    return new Recommendations(
        Collections.emptyList(), Collections.emptyList());
  }

  @Test
  @WithLoggedUser
  public void getRecommendationsForArtistsWithPopularityFiltersReturnsOk() throws Exception {
    // Arrange
    when(this.getRecommendationsQuery.getRecommendations(any()))
        .thenReturn(Mono.just(this.createEmptyRecommendations()));

    // Act
    this.mockMvc.perform(get(RECOMMENDATIONS_RESOURCE)
            .param("seed_artists", "1", "2")
            .param("min_popularity", "50")
            .param("max_popularity", "70")
        )
        // Assert
        .andExpect(status().isOk());

    verify(this.getRecommendationsQuery).getRecommendations(this.commandCaptor.capture());
    GetRecommendationsQueryParam recommendationsQueryParam = this.commandCaptor.getValue();
    assertThat(recommendationsQueryParam.getArtistIds()).contains("1", "2");
    assertThat(recommendationsQueryParam.getMinPopularity()).isEqualTo(50);
    assertThat(recommendationsQueryParam.getMaxPopularity()).isEqualTo(70);

  }

  @Test
  @WithLoggedUser
  void getRecommendationsForArtistsWithIdsReturnsRecommendations() throws Exception {
    // Arrange
    when(this.getRecommendationsQuery.getRecommendations(any()))
        .thenReturn(Mono.just(this.createRecommendations()));

    // Act
    this.mockMvc.perform(
            get(RECOMMENDATIONS_RESOURCE)
                .param("seed_artists", "1", "2", "3"))
        // Assert
        .andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print());

  }

  private Recommendations createRecommendations() {
    List<Track> tracks = List.of(new Track("t1", "First Song", 1234, Collections.emptyList()));
    return new Recommendations(Collections.emptyList(), tracks);
  }
}