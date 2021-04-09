package alx.music.songfind;

import java.io.File;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
class SongfindApplicationIT {

  @Container
  private final static DockerComposeContainer keycloak = new DockerComposeContainer(
      new File("src/main/docker/keycloak/docker-compose.yml"))
      .withExposedService("keycloak", 9080, Wait.forHttp("/auth").forPort(9080).forStatusCode(200));

  @Test
  void contextLoads() {
  }

}
