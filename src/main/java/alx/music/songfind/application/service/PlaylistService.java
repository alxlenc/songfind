package alx.music.songfind.application.service;

import static java.util.stream.Collectors.toList;

import alx.music.songfind.application.port.in.GetPlaylistQuery;
import alx.music.songfind.application.port.in.GetPlaylistTracksQuery;
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
class PlaylistService implements GetPlaylistQuery, GetPlaylistTracksQuery {

  private final GetPLaylistPort getPLaylistPort;

  private static final Comparator<Image> byImgWidthAsc = Comparator.comparingInt(Image::getWidth);

  @Override
  public Flux<Playlist> getCurrentUserPlaylists() {
    return withSortedImages(this.getPLaylistPort.getCurrentUserPlaylists());
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
    return getPLaylistPort.getPlaylistTracks(playlistId);
  }
}
