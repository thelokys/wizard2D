package br.thelokys.loaders;

import java.io.InputStream;

public class ResourceHelper {

  public static InputStream getResourceStream(String path) {
    var stream = ResourceHelper.class.getResourceAsStream(path);

    if (stream == null) {
      if (path.startsWith("/")) {
        stream = ResourceHelper.class.getResourceAsStream(path.substring(1));
      } else {
        stream = ResourceHelper.class.getResourceAsStream("/" + path);
      }
    }

    return stream;
  }
}