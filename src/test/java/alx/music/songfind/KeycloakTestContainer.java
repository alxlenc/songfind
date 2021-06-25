package alx.music.songfind;

import java.io.File;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

public class KeycloakTestContainer {

  final static String KEYCLOAK_BASE_PATH = new File("src/main/docker/keycloak")
      .getAbsolutePath();
  static final Slf4jLogConsumer logConsumer =
      new Slf4jLogConsumer(LoggerFactory.getLogger(SongfindApplicationIT.class));

  static GenericContainer<?> getWithContainerWithDevConfig(int internalHttpPort) {
    GenericContainer<?> keycloakContainer = new GenericContainer<>(
        DockerImageName.parse("jboss/keycloak:12.0.4"))
        .withEnv("KEYCLOAK_USER", "admin")
        .withEnv("KEYCLOAK_PASSWORD", "admin")
        .withEnv("DB_VENDOR", "h2")
        .withCommand(
            "-b",
            "0.0.0.0",
            "-Dkeycloak.migration.action=import",
            "-Dkeycloak.migration.provider=dir",
            "-Dkeycloak.migration.dir=/opt/jboss/keycloak/realm-config",
            "-Dkeycloak.migration.strategy=OVERWRITE_EXISTING",
            "-Djboss.socket.binding.port-offset=1000",
            "-Dkeycloak.profile.feature.upload_scripts=enabled")
        .withExposedPorts(internalHttpPort, 9443, 10990)
        .withLogConsumer(logConsumer)
        .withCopyFileToContainer(MountableFile
                .forHostPath(KEYCLOAK_BASE_PATH + "/realm-config/music-realm.json", 0744),
            "/opt/jboss/keycloak/realm-config/music-realm.json")
        .withCopyFileToContainer(MountableFile
                .forHostPath(KEYCLOAK_BASE_PATH + "/realm-config/music-users-0.json", 0744),
            "/opt/jboss/keycloak/realm-config/music-users-0.json")
        .withCopyFileToContainer(
            MountableFile.forHostPath(KEYCLOAK_BASE_PATH + "/deploy/music.jar", 0744),
            "/opt/jboss/keycloak/standalone/deployments/music.jar");
    keycloakContainer
        .setWaitStrategy(Wait.forHttp("/auth").forPort(internalHttpPort).forStatusCode(200));
    return keycloakContainer;
  }
}