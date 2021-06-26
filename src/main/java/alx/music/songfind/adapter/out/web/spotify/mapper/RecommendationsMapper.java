package alx.music.songfind.adapter.out.web.spotify.mapper;

import alx.music.songfind.domain.Recommendations;
import alx.music.songfind.domain.Seed;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(uses = TrackMapper.class)
public interface RecommendationsMapper {

  Recommendations toDomain(
      alx.music.songfind.adapter.out.web.spotify.model.Recommendations recommendations);

  List<Seed> toDomain(List<alx.music.songfind.adapter.out.web.spotify.model.Seed> seeds);
  
}
