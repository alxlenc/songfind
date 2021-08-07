package alx.music.songfind.adapter.in.web;

import alx.music.songfind.adapter.in.web.mapper.RecommendationsViewModelMapper;
import alx.music.songfind.adapter.in.web.model.Recommendations;
import alx.music.songfind.application.port.in.GetRecommendationsQuery;
import alx.music.songfind.application.port.in.GetRecommendationsQueryParam;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
@Slf4j
class RecommendationsController {

  private final GetRecommendationsQuery getRecommendationsQuery;
  private final RecommendationsViewModelMapper mapper;

  @GetMapping
  public Recommendations getRecommendations(
      @RequestParam(name = "seed_artists", required = false) List<String> artistIds,
      @RequestParam(name = "min_popularity", required = false) Integer minPopularity,
      @RequestParam(name = "max_popularity", required = false) Integer maxPopularity) {

    log.info("Requesting recommendations for artists: {}", artistIds);

    GetRecommendationsQueryParam getRecommendationsQueryParam = GetRecommendationsQueryParam
        .builder()
        .artistIds(artistIds)
        .minPopularity(minPopularity)
        .maxPopularity(maxPopularity)
        .build();
    
    return this.getRecommendationsQuery
        .getRecommendations(getRecommendationsQueryParam)
        .map(this.mapper::toViewModel)
        .block();
  }


}
