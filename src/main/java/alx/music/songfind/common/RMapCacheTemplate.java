package alx.music.songfind.common;


import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import org.redisson.api.RMapCacheReactive;
import reactor.core.publisher.Mono;


public class RMapCacheTemplate<K, V> implements ReactiveCacheTemplate<K, V> {

  private final RMapCacheReactive<K, V> cacheMap;
  private final Long expiration;

  public RMapCacheTemplate(RMapCacheReactive<K, V> cacheMap, Long expiration) {
    this.cacheMap = cacheMap;
    this.expiration = expiration;
  }

  @Override
  public Mono<V> getOrFetch(K key, Supplier<Mono<V>> source) {
    return this.cacheMap.get(key).switchIfEmpty(source.get())
        .flatMap(
            v -> this.cacheMap.fastPut(key, v, this.expiration, TimeUnit.SECONDS).thenReturn(v));
  }

}
