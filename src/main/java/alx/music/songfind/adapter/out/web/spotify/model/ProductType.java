package alx.music.songfind.adapter.out.web.spotify.model;

import java.util.HashMap;
import java.util.Map;


public enum ProductType {
  BASIC_DESKTOP("basic-desktop"),
  DAYPASS("daypass"),
  FREE("free"),
  OPEN("open"),
  PREMIUM("premium");

  private static final Map<String, ProductType> map = new HashMap<>();

  static {
    ProductType[] productTypes = values();

    for (ProductType productType : productTypes) {
      map.put(productType.type, productType);
    }

  }

  public final String type;

  ProductType(String type) {
    this.type = type;
  }

  public static ProductType keyOf(String type) {
    return map.get(type);
  }

  public String getType() {
    return this.type;
  }
}



