package alx.music.songfind.adapter.in.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import alx.music.songfind.application.port.in.GetUserQuery;
import alx.music.songfind.config.TestSecurityConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(SpotifyAccountController.class)
@Import({TestSecurityConfiguration.class})
public class SpotifyAccountControllerTest {

  private static final String SPOTIFY_ACCOUNT_RESOURCE = "/api/spotify/account";
  
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private GetUserQuery getUserQuery;

  @Test
  public void unauthorizedRequestReturnsUnauthorized() throws Exception {
    this.mockMvc.perform(get(SPOTIFY_ACCOUNT_RESOURCE))
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void loginEndpointReturnsRedirect() throws Exception {
    this.mockMvc.perform(get("/login/oauth2/authorization/spotify"))
        .andExpect(status().is3xxRedirection());
  }

}