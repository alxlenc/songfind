package alx.music.songfind.config;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import alx.music.songfind.config.cache.CacheConfiguration;
import alx.music.songfind.config.cache.CacheDefinitions;
import org.redisson.api.RMapCacheReactive;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.Codec;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import reactor.core.publisher.Mono;

@TestConfiguration
@Import({CacheConfiguration.class, CacheDefinitions.class})
public class MockCacheConfiguration {

  @Bean
  RedissonReactiveClient redissonReactiveClient(RMapReactive emptyMap,
      RMapCacheReactive emptyMapCache) {
    RedissonReactiveClient mock = mock(RedissonReactiveClient.class);
    when(mock.getMap(any(), any(Codec.class))).thenReturn(emptyMap);
    when(mock.getMapCache(any(), any(Codec.class))).thenReturn(emptyMapCache);
    return mock;
  }

  @Bean
  public RMapReactive emptyMap() {
    RMapReactive emptyMap = mock(RMapReactive.class);
    when(emptyMap.get(any())).thenReturn(Mono.empty());
    when(emptyMap.fastPut(any(), any())).thenReturn(Mono.just(Boolean.TRUE));
    return emptyMap;
  }

  @Bean
  public RMapCacheReactive emptyMapCache() {
    RMapCacheReactive emptyMap = mock(RMapCacheReactive.class);
    when(emptyMap.get(any())).thenReturn(Mono.empty());
    when(emptyMap.fastPut(any(), any(), anyLong(), any())).thenReturn(Mono.just(Boolean.TRUE));
    return emptyMap;
  }


}
