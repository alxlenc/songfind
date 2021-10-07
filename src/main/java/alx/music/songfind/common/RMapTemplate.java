package alx.music.songfind.common;


import java.util.function.Supplier;
import org.redisson.api.RMapReactive;
import reactor.core.publisher.Mono;


public class RMapTemplate<K, V> implements ReactiveCacheTemplate<K, V> {

  private final RMapReactive<K, V> cacheMap;

  public RMapTemplate(RMapReactive<K, V> cacheMap) {
    this.cacheMap = cacheMap;
  }

  @Override
  public Mono<V> getOrFetch(K key, Supplier<Mono<V>> source) {
    return this.cacheMap.get(key).switchIfEmpty(source.get())
        .flatMap(v -> this.cacheMap.fastPut(key, v).thenReturn(v));
  }

}
