package alx.music.songfind.adapter.in.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import alx.music.songfind.adapter.in.web.mapper.SpotifyUserMapperImpl;
import alx.music.songfind.application.port.in.GetSpotifyUserQuery;
import alx.music.songfind.config.TestSecurityConfiguration;
import alx.music.songfind.config.WithLoggedUser;
import alx.music.songfind.domain.Followers;
import alx.music.songfind.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Mono;


@WebMvcTest(SpotifyAccountController.class)
@Import({TestSecurityConfiguration.class})
public class SpotifyAccountControllerTest {

  private static final String SPOTIFY_ACCOUNT_RESOURCE = "/api/spotify/account";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private GetSpotifyUserQuery getSpotifyUserQuery;

  @SpyBean
  private SpotifyUserMapperImpl spotifyUserMapper;

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

  @Test
  @WithLoggedUser
  public void getSpotifyAccountReturnsOk() throws Exception {
    // Arrange
    User spotifyUser = this.getUser();
    when(this.getSpotifyUserQuery.getCurrentAccount()).thenReturn(Mono.just(spotifyUser));

    // Act
    this.mockMvc.perform(get("/api/spotify/account")).andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("userId"))
        .andExpect(jsonPath("$.email").value("user@user.com"))
        .andExpect(jsonPath("$.name").value("UserName"))
        .andExpect(jsonPath("$.country").value("ES"))
        .andExpect(jsonPath("$.id").value("userId"))
        .andExpect(jsonPath("$.followers.href").value("followers_ref"))
        .andExpect(jsonPath("$.followers.total").value("20"))
    ;

  }

  private User getUser() {
    User spotifyUser = new User("userId", "UserName", "user@user.com");
    Followers followers = new Followers("followers_ref", 20);
    spotifyUser.setFollowers(followers);
    spotifyUser.setCountry("ES");
    return spotifyUser;
  }

}