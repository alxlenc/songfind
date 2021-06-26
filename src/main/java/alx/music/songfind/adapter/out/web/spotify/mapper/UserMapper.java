package alx.music.songfind.adapter.out.web.spotify.mapper;

import alx.music.songfind.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;

@Mapper(uses = ImageMapper.class)
public interface UserMapper {

  User toDomain(alx.music.songfind.adapter.out.web.spotify.model.User user);

  @ObjectFactory
  default User create(alx.music.songfind.adapter.out.web.spotify.model.User user) {
    return new User(user.getId(), user.getName(), user.getEmail());
  }

}
