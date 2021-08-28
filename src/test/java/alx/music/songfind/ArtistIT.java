package alx.music.songfind;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import alx.music.songfind.config.ConfigureMockMvcWiremockIT;
import alx.music.songfind.config.ConfigureRedisCacheIT;
import alx.music.songfind.config.WithLoggedUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@ConfigureMockMvcWiremockIT
@ConfigureRedisCacheIT
public class ArtistIT {

  @Autowired
  private MockMvc mockMvc;

  @Test
  @WithLoggedUser
  void searchForArtists() throws Exception {

    this.mockMvc.perform(get("/api/artist").param("query", "Smash")).andExpect(
            status().isOk())
        .andExpect(jsonPath("$[0].id").value("08td7MxkoHQkXnWAYD8d6Q"))
        .andExpect(jsonPath("$[0].name").value("Smashing Pumpkins"))
    ;
  }


}
