package alx.music.songfind.adapter.out.web.spotify.mapper;

import alx.music.songfind.domain.PlaylistTrack;
import org.mapstruct.Mapper;

@Mapper(uses = {ImageMapper.class,
    TrackMapper.class})
public interface PlaylistTrackMapper {

  PlaylistTrack toDomain(
      alx.music.songfind.adapter.out.web.spotify.model.PlaylistTrack playlistTrack);

}
