package alx.music.songfind;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import alx.music.songfind.config.SpotifyTestWebClient;
import alx.music.songfind.config.TestSecurityConfiguration;
import alx.music.songfind.config.WithLoggedUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = "application.spotify-api.base-url=http://localhost:8765")
@Import({SpotifyTestWebClient.class, TestSecurityConfiguration.class})
@AutoConfigureWireMock(port = 8765)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RecommendationsIT {


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
