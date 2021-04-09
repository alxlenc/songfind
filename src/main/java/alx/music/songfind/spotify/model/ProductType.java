package alx.music.songfind.spotify.model;

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
    ProductType[] var0 = values();
    int var1 = var0.length;

    for (int var2 = 0; var2 < var1; ++var2) {
      ProductType productType = var0[var2];
      map.put(productType.type, productType);
    }

  }

  public final String type;

  private ProductType(String type) {
    this.type = type;
  }

  public static ProductType keyOf(String type) {
    return (ProductType) map.get(type);
  }

  public String getType() {
    return this.type;
  }
}



