package alx.music.songfind;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SongfindApplication {

  public static void main(String[] args) {
    SpringApplication.run(SongfindApplication.class, args);
  }

}


