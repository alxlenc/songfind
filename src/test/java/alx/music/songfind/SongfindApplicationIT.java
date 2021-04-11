package alx.music.songfind;

import java.io.File;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.ContainerState;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.MountableFile;

@SpringBootTest
//@Testcontainers
class SongfindApplicationIT {

  @Container
  private final static DockerComposeContainer keycloak;


  public static final String DOCKER_COMPOSE_YML = "./src/main/docker/keycloak/docker-compose.yml";

  static {
    keycloak = new DockerComposeContainer(
        new File(DOCKER_COMPOSE_YML))
        .withExposedService("keycloak", 9080, Wait.forHttp("/auth").forPort(9080).forStatusCode(200))
        .withLocalCompose(false);
    keycloak.start();
    ContainerState  cs = (ContainerState) keycloak.getContainerByServiceName("keycloak_1").get();
    cs.copyFileToContainer(MountableFile.forHostPath(DOCKER_COMPOSE_YML, 0744),  DOCKER_COMPOSE_YML);
  }

  @Test
  void contextLoads() {
  }

}
