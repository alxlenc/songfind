package alx.music.songfind.config.cache;

import alx.music.songfind.adapter.in.web.model.Artist;
import alx.music.songfind.adapter.in.web.model.Recommendations;
import alx.music.songfind.application.port.in.GetRecommendationsQueryParam;
import alx.music.songfind.common.RMapCacheTemplate;
import alx.music.songfind.common.RMapTemplate;
import alx.music.songfind.config.cache.CacheDefinitions.CacheDefinition;
import java.util.List;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfiguration {

  @Bean
  public RMapCacheTemplate<GetRecommendationsQueryParam, Recommendations> recommendationsCache(
      RedissonReactiveClient client, CacheDefinitions cacheDefitinions) {
    CacheDefinition recommendations = cacheDefitinions.getRecommendations();
    return new RMapCacheTemplate<>(client.getMapCache(recommendations.getKey(),
        new TypedJsonJacksonCodec(GetRecommendationsQueryParam.class, Recommendations.class)),
        recommendations.getTtlSeconds());
  }

  @Bean
  public RMapTemplate<String, List<Artist>> artistsCache(
      RedissonReactiveClient client, CacheDefinitions cacheDefinitions) {
    CacheDefinition artists = cacheDefinitions.getArtists();
    return new RMapTemplate<>(client.getMap(artists.getKey(),
        new TypedJsonJacksonCodec(String.class, List.class)));
  }

}
