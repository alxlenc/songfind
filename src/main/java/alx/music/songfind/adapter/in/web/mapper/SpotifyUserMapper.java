package alx.music.songfind.adapter.in.web.mapper;

import alx.music.songfind.adapter.in.web.model.SpotifyUser;
import org.mapstruct.Mapper;

@Mapper
public interface SpotifyUserMapper {

  SpotifyUser toViewModel(alx.music.songfind.domain.User user);

}
