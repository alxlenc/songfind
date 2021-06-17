package alx.music.songfind.adapter.in.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import alx.music.songfind.adapter.in.web.mapper.RecommendationsViewModelMapper;
import alx.music.songfind.application.port.in.GetRecommendationsCommand;
import alx.music.songfind.application.port.in.GetRecommendationsQuery;
import alx.music.songfind.config.TestSecurityConfiguration;
import alx.music.songfind.config.WithLoggedUser;
import alx.music.songfind.domain.Recommendations;
import java.util.Collections;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RecommendationsController.class)
@Import(TestSecurityConfiguration.class)
class RecommendationsControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private GetRecommendationsQuery getRecommendationsQuery;

  @MockBean
  private RecommendationsViewModelMapper recommendationsMapper;

  @Captor
  private ArgumentCaptor<GetRecommendationsCommand> commandCaptor;

  @Test
  void getRecommendationsByPlaylistWithUnauthorizedUser() throws Exception {
    this.mockMvc.perform(get("/api/recommendations"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithLoggedUser
  void getRecommendationsForArtistsWithIds() throws Exception {

    when(getRecommendationsQuery.getRecommendations(any())).thenReturn(new Recommendations(
        Collections.emptyList(), Collections.emptyList()));

    this.mockMvc.perform(get("/api/recommendations")
        .param("seed_artists", "1", "2", "3"))
        .andExpect(status().isOk());

    verify(getRecommendationsQuery).getRecommendations(commandCaptor.capture());

    Assertions.assertThat(commandCaptor.getValue().getArtistIds()).contains("1", "2", "3");

  }

}