package alx.music.songfind.adapter.in.web;

import alx.music.songfind.adapter.in.web.mapper.RecommendationsViewModelMapper;
import alx.music.songfind.adapter.in.web.model.Recommendations;
import alx.music.songfind.application.port.in.GetRecommendationsCommand;
import alx.music.songfind.application.port.in.GetRecommendationsQuery;
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
      @RequestParam(value = "seed_artists", required = false) List<String> artistIds) {

    log.info("Requesting recommendations for artists: {}", artistIds);

    GetRecommendationsCommand getRecommendationsCommand = new GetRecommendationsCommand(artistIds);
    alx.music.songfind.domain.Recommendations recommendations = this.getRecommendationsQuery
        .getRecommendations(getRecommendationsCommand);
    return this.mapper.toViewModel(recommendations);
  }


}
