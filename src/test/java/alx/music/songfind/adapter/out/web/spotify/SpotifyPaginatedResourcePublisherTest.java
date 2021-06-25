package alx.music.songfind.adapter.out.web.spotify;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import alx.music.songfind.adapter.out.web.spotify.model.Paginated;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class SpotifyPaginatedResourcePublisherTest {


  @Mock
  Function<Integer, Mono<Paginated<Integer>>> integerProvider;

  @InjectMocks
  SpotifyPaginatedResourcePublisher sut;

  @Test
  void when_publish_needs_one_incomplete_page() {

  }

  @Test
  void when_publish_needs_one_complete_page() {

  }

  @Test
  void when_publish_needs_more_than_one_complete_pages() {

  }

  @Test
  void when_publish_needs_more_than_one_page_and_another_incomplete_one() {
    // Arrange
    int totalElements = 7;
    int pageSize = 3;
    var p1 = Paginated.<Integer>builder()
        .items(List.of(1, 2, 3)).limit(pageSize).total(totalElements).build();
    var p2 = Paginated.<Integer>builder()
        .items(List.of(4, 5, 6)).limit(pageSize).total(totalElements).build();
    var p3 = Paginated.<Integer>builder()
        .items(List.of(7)).limit(pageSize).total(totalElements).build();

    when(this.integerProvider.apply(0)).thenReturn(Mono.just(p1));
    when(this.integerProvider.apply(3)).thenReturn(Mono.just(p2));
    when(this.integerProvider.apply(6)).thenReturn(Mono.just(p3));

    // Act
    Flux<Integer> integerFlux = this.sut.publish(this.integerProvider);

    // Assert
    StepVerifier.create(integerFlux)
        .expectSubscription()
        .recordWith(ArrayList::new)
        .expectNextCount(7)
        .consumeRecordedWith(items ->
            assertThat(items).containsSequence(1, 2, 3, 4, 5, 6, 7))
        .verifyComplete();

  }
}