package alx.music.songfind.adapter.out.web.spotify.mapper;

import alx.music.songfind.domain.Recommendations;
import org.mapstruct.Mapper;

@Mapper
public interface RecommendationsMapper {

  Recommendations toDomain(
      alx.music.songfind.adapter.out.web.spotify.model.Recommendations recommendations);

}
