package alx.music.songfind.spotify.model;

import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.model_objects.specification.Image;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class User {

    private  String birthdate;
    private  CountryCode country;
    private  String displayName;
    private  String email;
//    privatal ExternalUrl externalUrls;
    private  Followers followers;
    private  String href;
    private  String id;
    private  Image[] images;
    private  ProductType product;
//    privatal ModelObjectType type;
    private  String uri;
}
