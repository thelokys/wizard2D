package br.thelokys.loaders;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

public class LoaderFont {

  public static void register(String path, float size) {
    try {
      var fontStream = LoaderFont.class.getResourceAsStream(path);
      Font font = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(size);
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      ge.registerFont(font);
    } catch (Exception e) {
      System.out.println("Não foi possivel carregar a fonte: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
