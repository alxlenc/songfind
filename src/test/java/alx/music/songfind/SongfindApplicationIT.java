package alx.music.songfind;

import alx.music.songfind.config.SpotifyTestWebClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@Import(SpotifyTestWebClient.class)
class SongfindApplicationIT {

  public static final int INTERNAL_HTTP_PORT = 9080;
  @Container
  private final static GenericContainer<?> keycloak =
      KeycloakTestContainer.getWithContainerWithDevConfig(INTERNAL_HTTP_PORT);

  @DynamicPropertySource
  static void authServer(DynamicPropertyRegistry registry) {
    Integer mappedPort = keycloak.getMappedPort(INTERNAL_HTTP_PORT);
    registry.add("application.auth.server", () -> "localhost:" + mappedPort);
    registry.add("spring.security.oauth2.client.provider.songfind.issuer-uri",
        () -> "http://localhost:" + mappedPort + "/auth/realms/music");
  }


  @Test
  void contextLoads() {
  }

}
