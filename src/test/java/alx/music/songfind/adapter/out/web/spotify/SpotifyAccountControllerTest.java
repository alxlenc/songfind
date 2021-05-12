package alx.music.songfind.adapter.spotify;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import alx.music.songfind.adapter.spotify.config.SpotifyConfiguration;
import alx.music.songfind.config.TestSecurityConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(SpotifyAccountController.class)
@Import({TestSecurityConfiguration.class, SpotifyClient.class, SpotifyConfiguration.class})
public class SpotifyAccountControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  @WithMockUser
  public void authorizedInAppButNotInSpotifyReturnsUnauthorized() throws Exception {
    this.mockMvc.perform(get("/api/spotify/account"))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.message").value("Unauthorized: My Client not authenticated"));
  }

  @Test
  public void unauthorizedRequestReturnsUnauthorized() throws Exception {
    this.mockMvc.perform(get("/api/spotify/account"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void loginEndpointReturnsRedirect() throws Exception {
    this.mockMvc.perform(get("/login/oauth2/authorization/spotify"))
        .andExpect(status().is3xxRedirection());
  }

}