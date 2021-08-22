package alx.music.songfind.adapter.in.web.mapper;

import alx.music.songfind.adapter.in.web.model.Artist;
import org.mapstruct.Mapper;

@Mapper
public interface ArtistViewModelMapper {

  Artist toViewModel(alx.music.songfind.domain.Artist domain);

}
