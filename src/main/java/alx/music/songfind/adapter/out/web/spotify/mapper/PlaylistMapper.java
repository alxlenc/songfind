package alx.music.songfind.adapter.out.web.spotify.mapper;

import alx.music.songfind.domain.Playlist;
import org.mapstruct.Mapper;

@Mapper
public interface PlaylistMapper {

  Playlist toDomain(alx.music.songfind.adapter.out.web.spotify.model.Playlist playlist);

}
