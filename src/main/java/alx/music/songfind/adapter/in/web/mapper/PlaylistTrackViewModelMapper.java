package alx.music.songfind.adapter.in.web.mapper;

import alx.music.songfind.adapter.in.web.model.PlaylistTrack;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PlaylistTrackViewModelMapper {

  @Mapping(target = ".", source = "track")
  @Mapping(target = "addedBy", source = "addedBy.id")
  @Mapping(target = "externalUrl", source = "track.externalUrl.spotify")
  PlaylistTrack toViewModel(alx.music.songfind.domain.PlaylistTrack playlistTrack);

  // TODO: Add to a default mapper configuration
  default String toUtcString(Instant source) {
    if (source == null) {
      return null;
    }
    return source.atZone(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
  }
  
}
