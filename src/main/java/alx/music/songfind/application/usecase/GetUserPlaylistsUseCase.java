package alx.music.songfind.application.usecase;

import static java.util.stream.Collectors.toList;

import alx.music.songfind.application.port.in.GetPlaylistTracksQuery;
import alx.music.songfind.application.port.in.GetUserPlaylistsQuery;
import alx.music.songfind.application.port.out.GetPLaylistPort;
import alx.music.songfind.domain.Image;
import alx.music.songfind.domain.Playlist;
import alx.music.songfind.domain.PlaylistTrack;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
class GetUserPlaylistsUseCase implements GetUserPlaylistsQuery, GetPlaylistTracksQuery {

  private static final Comparator<Image> byImgWidthAsc = Comparator
      .comparingInt(image -> image.getWidth() != null ? image.getWidth() : Integer.MAX_VALUE);
  private final GetPLaylistPort getPLaylistPort;

  @Override
  public Flux<Playlist> getCurrentUserPlaylists() {
    return this.withSortedImages(this.getPLaylistPort.getCurrentUserPlaylists());
  }

  private Flux<Playlist> withSortedImages(Flux<Playlist> publish) {
    return publish.doOnNext(pl -> {
      List<Image> sortedImages = pl.getImages().stream().sorted(byImgWidthAsc)
          .collect(toList());
      pl.setImages(sortedImages);
    });
  }

  @Override
  public Flux<PlaylistTrack> getPlaylistTracks(String playlistId) {
    return this.getPLaylistPort.getPlaylistTracks(playlistId);
  }
}