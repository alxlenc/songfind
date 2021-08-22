package alx.music.songfind.config;

import alx.music.songfind.adapter.in.web.model.Recommendations;
import alx.music.songfind.adapter.in.web.util.CacheTemplate;
import alx.music.songfind.application.port.in.GetRecommendationsQueryParam;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfiguration {

  @Bean
  public CacheTemplate<GetRecommendationsQueryParam, Recommendations> recommendationsCache(
      RedissonReactiveClient client) {
    return new CacheTemplate<>(client.getMap("songfind:recommendations:results",
        new TypedJsonJacksonCodec(GetRecommendationsQueryParam.class, Recommendations.class)));
  }

}
