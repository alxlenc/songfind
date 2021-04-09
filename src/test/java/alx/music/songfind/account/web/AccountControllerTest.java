package alx.music.songfind.account.web;

import static java.util.stream.Collectors.toSet;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import alx.music.songfind.account.AccountService;
import alx.music.songfind.account.Authority;
import alx.music.songfind.account.User;
import alx.music.songfind.config.TestSecurityConfiguration;
import alx.music.songfind.config.WithLoggedInUser;
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

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AccountService accountService;

  ArgumentCaptor<Principal> principalCaptor;


  @Test
  @WithLoggedInUser
  void logout() throws Exception {

    this.mockMvc.perform(post("/api/logout")
                          .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.logoutUrl").value(TestSecurityConfiguration.END_SESSION_ENDPOINT))
        .andExpect(jsonPath("$.idToken").isString());
  }

  @Test
  @WithMockUser
  void authorizedRequestIsOk() throws Exception {

    User user = new User();
    user.setLogin("alice01");
    user.setId("00011");
    user.setFirstName("Alice");
    user.setLastName("LastName");
    user.setEmail("alice@alice.com");
    user.setActivated(false);
    user.setLangKey("EN");
    user.setImageUrl("https://image.url/alice");
    Set<Authority> authorities = Set.of("USER", "ADMIN").stream().map(Authority::new)
        .collect(toSet());
    user.setAuthorities(authorities);

    when(this.accountService.getUserFromAuthentication(any(AbstractAuthenticationToken.class))).thenReturn(user);

    this.mockMvc.perform(get("/api/account"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Alice"));
  }

  @Test
  void unauthorizedRequestReturnsUnauthorized() throws Exception {
    this.mockMvc.perform(get("/api/account"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void loginEndPointReturnsRedirect() throws Exception {
    this.mockMvc.perform(get("/login/oauth2/authorization/songfind"))
        .andExpect(status().is3xxRedirection());
  }
}