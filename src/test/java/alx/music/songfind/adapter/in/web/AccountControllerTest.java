package alx.music.songfind.adapter.in.web;

import static java.util.stream.Collectors.toSet;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import alx.music.songfind.adapter.in.web.mapper.SecurityMapper;
import alx.music.songfind.adapter.in.web.model.Authority;
import alx.music.songfind.adapter.in.web.model.User;
import alx.music.songfind.config.TestSecurityConfiguration;
import alx.music.songfind.config.WithLoggedUser;
import java.security.Principal;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AccountController.class)
@Import(TestSecurityConfiguration.class)
public class AccountControllerTest {

  private static final String ACCOUNT_RESOURCE = "/api/account";
  private ArgumentCaptor<Principal> principalCaptor;
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private SecurityMapper securityMapper;

  @Test
  @WithMockUser
  void getAccountReturnsAccount() throws Exception {
    // Arrange
    String alice = "Alice";
    User user = this.createUser(alice);

    when(this.securityMapper.getUserFromAuthentication(any(AbstractAuthenticationToken.class)))
        .thenReturn(user);

    // Act
    this.mockMvc.perform(get(ACCOUNT_RESOURCE))
        //Assert
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName").value(alice));
  }

  private User createUser(String firstName) {
    User user = new User();
    user.setLogin("alice01");
    user.setId("00011");
    user.setFirstName(firstName);
    user.setLastName("LastName");
    user.setEmail("alice@alice.com");
    user.setActivated(false);
    user.setLangKey("EN");
    user.setImageUrl("https://image.url/alice");
    Set<Authority> authorities = Set.of("USER", "ADMIN").stream().map(Authority::new)
        .collect(toSet());
    user.setAuthorities(authorities);
    return user;
  }

  @Test
  void unauthorizedGetAccountReturnsUnauthorized() throws Exception {
    // Act
    this.mockMvc.perform(get(ACCOUNT_RESOURCE))
        // Assert
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithLoggedUser
  void logoutReturnsIdProviderLogoutUrlAndIdToken() throws Exception {
    // Act
    this.mockMvc.perform(post("/api/logout")
        .with(csrf()))
        // Assert
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.logoutUrl").value(TestSecurityConfiguration.END_SESSION_ENDPOINT))
        .andExpect(jsonPath("$.idToken").isString());
  }

  @Test
  void loginReturnsRedirect() throws Exception {
    // Act
    this.mockMvc.perform(get("/login/oauth2/authorization/songfind"))
        // Assert
        .andExpect(status().is3xxRedirection());
  }
}