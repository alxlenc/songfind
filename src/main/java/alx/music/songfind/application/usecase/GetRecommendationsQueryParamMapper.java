package alx.music.songfind.application.usecase;

import alx.music.songfind.application.port.out.GetRecommendationsQueryParam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface GetRecommendationsQueryParamMapper {

  @Mapping(target = "targetPopularity", source = "maxPopularity")
  GetRecommendationsQueryParam map(
      alx.music.songfind.application.port.in.GetRecommendationsQueryParam inputParam);
}
