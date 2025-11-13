package br.thelokys.loaders;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

public class LoaderFont {

  public static void register(String path, float size) {
    try {
      var fontStream = ResourceHelper.getResourceStream(path);
      if (fontStream == null) {
        throw new RuntimeException("Font resource not found: " + path);
      }

      var font = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(size);
      var ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      ge.registerFont(font);
      fontStream.close();
    } catch (Exception e) {
      System.out.println("Não foi possivel carregar a fonte: " + e.getMessage());
      e.printStackTrace();
    }
  }
}