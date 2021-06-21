package alx.music.songfind.adapter.out.web.spotify.mapper;

import alx.music.songfind.domain.Artist;
import org.mapstruct.Mapper;

@Mapper
public interface ArtistMapper {

  Artist toDomain(alx.music.songfind.adapter.out.web.spotify.model.Artist artist);

}
