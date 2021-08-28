package alx.music.songfind.config;

import java.io.IOException;
import org.redisson.Redisson;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.config.Config;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import redis.embedded.RedisServer;

@TestConfiguration
public class IntegrationCacheConfiguration {

  @Bean(destroyMethod = "shutdown")
  public RedissonReactiveClient redisson(RedisServer myRedisServer) throws IOException {
    Config config = new Config();
    config.useSingleServer()
        .setAddress("redis://127.0.0.1:6379")
        .setConnectTimeout(1000)
        .setDatabase(0)
        .setPassword("password");
    return Redisson.create(config).reactive();
  }


  @Bean
  RedisServer myRedisServer() {
    RedisServer redisServer = RedisServer.builder()
        .port(6379)
        .setting("bind 127.0.0.1")
        .setting("maxmemory 128M")
        .setting("requirepass password")
        .build();
    redisServer.start();
    return redisServer;
  }

}
