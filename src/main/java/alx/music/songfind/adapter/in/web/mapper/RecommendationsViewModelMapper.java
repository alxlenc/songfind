package alx.music.songfind.adapter.in.web.mapper;

import alx.music.songfind.adapter.in.web.model.Recommendations;
import org.mapstruct.Mapper;

@Mapper
public interface RecommendationsViewModelMapper {

  Recommendations toViewModel(alx.music.songfind.domain.Recommendations recommendations);

}
