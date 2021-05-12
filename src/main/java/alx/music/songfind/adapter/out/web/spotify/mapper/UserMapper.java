package alx.music.songfind.adapter.out.web.spotify.mapper;

import alx.music.songfind.domain.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

  User toDomain(alx.music.songfind.adapter.out.web.spotify.model.User user);

}
