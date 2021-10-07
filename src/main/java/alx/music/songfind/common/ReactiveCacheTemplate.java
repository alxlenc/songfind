package alx.music.songfind.common;

import java.util.function.Supplier;
import reactor.core.publisher.Mono;

public interface ReactiveCacheTemplate<K, V> {

  Mono<V> getOrFetch(K key, Supplier<Mono<V>> source);
  
}
