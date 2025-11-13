package br.thelokys.loaders;

import java.io.InputStream;

public class ResourceHelper {

  public static InputStream getResourceStream(String path) {
    if (path == null || path.trim().isEmpty()) {
      return null;
    }

    var normalizedPath = path.startsWith("/") ? path.substring(1) : path;
    var stream = ResourceHelper.class.getClassLoader().getResourceAsStream(normalizedPath);

    if (stream == null) {
      stream = ResourceHelper.class.getResourceAsStream("/" + normalizedPath);
    }

    if (stream == null) {
      stream = ResourceHelper.class.getResourceAsStream(path);
    }

    return stream;
  }

  public static boolean resourceExists(String path) {
    return getResourceStream(path) != null;
  }
}