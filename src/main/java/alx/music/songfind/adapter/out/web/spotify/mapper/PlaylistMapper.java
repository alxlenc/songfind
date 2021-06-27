package alx.music.songfind.adapter.out.web.spotify.mapper;

import alx.music.songfind.domain.Playlist;
import alx.music.songfind.domain.Playlist.TracksInfo;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;

@Mapper(config = MapperConfiguration.class, uses = ImageMapper.class)
public interface PlaylistMapper {

  Playlist toDomain(alx.music.songfind.adapter.out.web.spotify.model.Playlist playlist);

  TracksInfo mapTracksInfo(
      alx.music.songfind.adapter.out.web.spotify.model.Playlist.TracksInfo tracks);

  @ObjectFactory
  default Playlist create(alx.music.songfind.adapter.out.web.spotify.model.Playlist playlist) {
    return new Playlist(
        playlist.getId(),
        playlist.getName(),
        this.mapTracksInfo(playlist.getTracks())
    );
  }

}
