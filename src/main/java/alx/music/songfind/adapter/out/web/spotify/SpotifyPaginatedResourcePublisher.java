package alx.music.songfind.adapter.out.web.spotify;

import alx.music.songfind.adapter.out.web.spotify.model.Paginated;
import java.util.function.Function;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
class SpotifyPaginatedResourcePublisher {

  public SpotifyPaginatedResourcePublisher() {
  }

  <T> Flux<T> publish(Function<Integer, Mono<Paginated<T>>> itemProvider) {
    return itemProvider.apply(0).flatMapMany(playlistPaginated -> {
      Flux<T> firstResults = Flux.fromIterable(playlistPaginated.getItems());
      Integer pageSize = playlistPaginated.getLimit();
      Integer totalItems = playlistPaginated.getTotal();
      if (totalItems <= pageSize) {
        return firstResults;
      } else {
        // Total of pages minus the first page already requested
        int pendingRequests = (totalItems - 1) / pageSize;
        Flux<T> pendingResults = Flux.range(1, pendingRequests)
            .flatMap(pageNumber -> {
              int offset = pageNumber * pageSize;
              return itemProvider.apply(offset);
            })
            .flatMapIterable(Paginated::getItems);
        return Flux.concat(firstResults, pendingResults);
      }
    });
  }
}