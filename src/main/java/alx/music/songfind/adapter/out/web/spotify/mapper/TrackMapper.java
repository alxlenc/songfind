package alx.music.songfind.adapter.out.web.spotify.mapper;

import alx.music.songfind.domain.Artist;
import alx.music.songfind.domain.Track;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ObjectFactory;

@Mapper(config = MapperConfiguration.class)
public interface TrackMapper {

  Track toDomain(alx.music.songfind.adapter.out.web.spotify.model.Track track);

  @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
  List<Artist> toDomain(List<alx.music.songfind.adapter.out.web.spotify.model.Artist> artists);

  @ObjectFactory
  default Track create(alx.music.songfind.adapter.out.web.spotify.model.Track track) {
    return new Track(
        track.getId(),
        track.getName(),
        track.getDuration(),
        this.toDomain(track.getArtists())
    );
  }

}
