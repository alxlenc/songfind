package alx.music.songfind.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("application.artist")
@Getter
@Setter
public class SearchArtistConfiguration {

  private Integer maxSearchResults;

}