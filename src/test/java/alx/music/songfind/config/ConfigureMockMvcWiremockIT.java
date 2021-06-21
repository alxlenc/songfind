package alx.music.songfind.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(properties = "application.spotify-api.base-url=http://localhost:8765")
@Import({SpotifyTestWebClient.class, TestSecurityConfiguration.class})
@AutoConfigureWireMock(port = 8765)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public @interface ConfigureMockMvcWiremockIT {

}
