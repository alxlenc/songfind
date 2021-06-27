package alx.music.songfind.adapter.out.web.spotify.mapper;

import alx.music.songfind.adapter.out.web.spotify.model.Image;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfiguration.class)
public interface ImageMapper {

  default alx.music.songfind.domain.Image toDomain(Image image) {
    return new alx.music.songfind.domain.Image(
        image.getUrl(),
        image.getHeight(),
        image.getWidth()
    );
  }

  ;

}
