package alx.music.songfind.config;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.Codec;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

@TestConfiguration
public class TestCacheConfiguration {

  @Bean
  RedissonReactiveClient redissonReactiveClient(RMapReactive emptyMap) {
    RedissonReactiveClient mock = mock(RedissonReactiveClient.class);
    when(mock.getMap(any(), any(Codec.class))).thenReturn(emptyMap);
    return mock;
  }

  @Bean
  public RMapReactive getrMapReactive() {
    RMapReactive emptyMap = mock(RMapReactive.class);
    when(emptyMap.get(any())).thenReturn(Mono.empty());
    when(emptyMap.fastPut(any(), any())).thenReturn(Mono.just(Boolean.TRUE));
    return emptyMap;
  }

}
