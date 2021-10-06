package alx.music.songfind.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("application.artist")
@Data
public class SearchArtistConfiguration {

  private Integer maxSearchResults;

}