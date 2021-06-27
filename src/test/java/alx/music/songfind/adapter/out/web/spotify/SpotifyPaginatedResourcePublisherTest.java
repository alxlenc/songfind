package alx.music.songfind.adapter.out.web.spotify;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import alx.music.songfind.adapter.out.web.spotify.model.Paginated;
import java.util.ArrayList;
import java.util.Arrays;
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
public class SpotifyPaginatedResourcePublisherTest {

  @InjectMocks
  SpotifyPaginatedResourcePublisher sut;

  @Mock
  Function<Integer, Mono<Paginated<Integer>>> integerProvider;

  @Test
  void publishWithOneIncompletePageReturnsOk() {
    // Arrange
    int totalElements = 2;
    int pageSize = 3;
    var p1 = this.createPage(totalElements, pageSize, 1, 2);

    when(this.integerProvider.apply(this.getOffset(pageSize, 0))).thenReturn(Mono.just(p1));

    // Act
    Flux<Integer> integerFlux = this.sut.publish(this.integerProvider);

    // Assert
    StepVerifier.create(integerFlux)
        .expectSubscription()
        .recordWith(ArrayList::new)
        .expectNextCount(totalElements)
        .consumeRecordedWith(items ->
            assertThat(items).containsSequence(1, 2))
        .verifyComplete();
  }

  @Test
  void publishWithOneCompletePageReturnsOk() {
    // Arrange
    int totalElements = 3;
    int pageSize = 3;
    var p1 = this.createPage(totalElements, pageSize, 1, 2, 3);

    when(this.integerProvider.apply(this.getOffset(pageSize, 0))).thenReturn(Mono.just(p1));

    // Act
    Flux<Integer> integerFlux = this.sut.publish(this.integerProvider);

    // Assert
    StepVerifier.create(integerFlux)
        .expectSubscription()
        .recordWith(ArrayList::new)
        .expectNextCount(totalElements)
        .consumeRecordedWith(items ->
            assertThat(items).containsSequence(1, 2, 3))
        .verifyComplete();
  }

  @Test
  void publishWithMoreThanOneCompletePagesReturnsOk() {
    // Arrange
    int totalElements = 6;
    int pageSize = 3;
    var p1 = this.createPage(totalElements, pageSize, 1, 2, 3);
    var p2 = this.createPage(totalElements, pageSize, 4, 5, 6);

    when(this.integerProvider.apply(this.getOffset(pageSize, 0))).thenReturn(Mono.just(p1));
    when(this.integerProvider.apply(this.getOffset(pageSize, 1))).thenReturn(Mono.just(p2));

    // Act
    Flux<Integer> integerFlux = this.sut.publish(this.integerProvider);

    StepVerifier.create(integerFlux)
        // Assert
        .expectSubscription()
        .recordWith(ArrayList::new)
        .expectNextCount(totalElements)
        .consumeRecordedWith(items ->
            assertThat(items).containsSequence(1, 2, 3, 4, 5, 6))
        .verifyComplete();

  }

  @Test
  void publishWithMoreThanOneCompletePageAndAnIncompleteOneReturnsOk() {
    // Arrange
    int totalElements = 7;
    int pageSize = 3;
    var p1 = this.createPage(totalElements, pageSize, 1, 2, 3);
    var p2 = this.createPage(totalElements, pageSize, 4, 5, 6);
    var p3 = this.createPage(totalElements, pageSize, 7);

    when(this.integerProvider.apply(this.getOffset(pageSize, 0))).thenReturn(Mono.just(p1));
    when(this.integerProvider.apply(this.getOffset(pageSize, 1))).thenReturn(Mono.just(p2));
    when(this.integerProvider.apply(this.getOffset(pageSize, 2))).thenReturn(Mono.just(p3));

    // Act
    Flux<Integer> integerFlux = this.sut.publish(this.integerProvider);

    StepVerifier.create(integerFlux)
        // Assert
        .expectSubscription()
        .recordWith(ArrayList::new)
        .expectNextCount(totalElements)
        .consumeRecordedWith(items ->
            assertThat(items).containsSequence(1, 2, 3, 4, 5, 6, 7))
        .verifyComplete();

  }

  private int getOffset(int pageSize, int pageNumber) {
    return pageSize * pageNumber;
  }

  private Paginated<Integer> createPage(int totalElements, int pageSize, Integer... i) {
    return Paginated.<Integer>builder()
        .items(Arrays.stream(i).toList()).limit(pageSize).total(totalElements).build();
  }
}