package alx.music.songfind.config.cache;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("application.cache")
public class CacheDefinitions {

  private CacheDefinition recommendations = new CacheDefinition("songfind:recommendations:results");
  private CacheDefinition artists = new CacheDefinition("songfind:artists:results");

  @Data
  public static class CacheDefinition {

    private final String key;
    private Long ttlSeconds;
  }
}
