package br.thelokys.loaders;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class LoaderImage {

  private static final Map<String, BufferedImage> cache = new HashMap<>();

  public static BufferedImage from(String path) {
    if (cache.containsKey(path)) {
      return cache.get(path);
    }

    try {
      var stream = ResourceHelper.getResourceStream(path);
      if (stream == null) {
        throw new IOException("Image resource not found: " + path);
      }

      BufferedImage img = ImageIO.read(stream);
      cache.put(path, img);
      return img;
    } catch (IOException e) {
      System.err.println("Não foi possível carregar a imagem: " + path);
      e.printStackTrace();
      return null;
    }
  }
}