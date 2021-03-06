package alx.music.songfind.adapter.in.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import alx.music.songfind.adapter.in.web.mapper.ArtistViewModelMapper;
import alx.music.songfind.adapter.in.web.mapper.ArtistViewModelMapperImpl;
import alx.music.songfind.application.port.in.SearchArtistQuery;
import alx.music.songfind.config.MockCacheConfiguration;
import alx.music.songfind.config.TestSecurityConfiguration;
import alx.music.songfind.config.WithLoggedUser;
import alx.music.songfind.domain.Artist;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Flux;

@WebMvcTest(ArtistController.class)
@Import({TestSecurityConfiguration.class, MockCacheConfiguration.class})
public class ArtistControllerTest {

  @TestConfiguration
  static class ArtistControllerTestConfiguration {

    @Bean
    ArtistViewModelMapper artistViewModelMapper() {
      return new ArtistViewModelMapperImpl();
    }

  }

  private final String ARTIST_RESOURCE = "/api/artist";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private SearchArtistQuery searchArtistQuery;

  @Test
  void unauthorizedGetArtistReturnsUnauthorized() throws Exception {
    this.mockMvc.perform(get(this.ARTIST_RESOURCE).param("query", "anything"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithLoggedUser
  void getArtistsReturnsOk() throws Exception {

    Artist artist1 = new Artist("id1", "Smashing Pumpkins");
    Artist artist2 = new Artist("id2", "Smash Mouth");
    final String query = "Smash";
    when(this.searchArtistQuery.searchArtist(query)).thenReturn(Flux.just(artist1, artist2));

    this.mockMvc.perform(get(this.ARTIST_RESOURCE)
            .param("query", query))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(artist1.getId()))
        .andExpect(jsonPath("$[0].name").value(artist1.getName()))
        .andExpect(jsonPath("$[1].id").value(artist2.getId()))
        .andExpect(jsonPath("$[1].name").value(artist2.getName()))
    ;
  }
}