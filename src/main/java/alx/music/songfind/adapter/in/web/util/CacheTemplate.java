package alx.music.songfind.adapter.in.web.util;


import java.util.function.Supplier;
import org.redisson.api.RMapReactive;
import reactor.core.publisher.Mono;


public class CacheTemplate<K, V> {

  private final RMapReactive<K, V> cacheMap;

  public CacheTemplate(RMapReactive<K, V> cacheMap) {
    this.cacheMap = cacheMap;
  }

  public Mono<V> getOrFetch(K key, Supplier<Mono<V>> source) {
    return this.cacheMap.get(key).switchIfEmpty(source.get())
        .flatMap(v -> this.cacheMap.fastPut(key, v).thenReturn(v));
  }

}
