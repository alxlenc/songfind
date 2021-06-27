package alx.music.songfind;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import alx.music.songfind.config.ConfigureMockMvcWiremockIT;
import alx.music.songfind.config.WithLoggedUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@ConfigureMockMvcWiremockIT
public class RecommendationsIT {

  @Autowired
  private MockMvc mockMvc;

  @Test
  @WithLoggedUser
  public void getRecommendations() throws Exception {
    this.mockMvc.perform(get("/api/recommendations")
        .param("seed_artists", "5bHjVR4F2Tfq4Ha6x7K6wU")
    ).andExpect(status().isOk());
  }

}
