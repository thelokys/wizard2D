package br.thelokys.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Utilitário para gerar spritesheets básicos de animação.
 * Este é um utilitário temporário para criar spritesheets a partir dos sprites atuais.
 */
public class SpriteSheetGenerator {
  
  public static void main(String[] args) {
    try {
      String baseDir = "src/main/resources/entities/player/sprites";
      
      // Carrega sprites base
      BufferedImage idleRight = ImageIO.read(new File(baseDir + "/idle-right.png"));
      BufferedImage idleLeft = ImageIO.read(new File(baseDir + "/idle-left.png"));
      
      int spriteSize = idleRight.getWidth();
      int frames = 4;
      
      // Gera spritesheets para todas as animações
      // Nota: Os sprites idle já existem, mas precisam ser spritesheets também
      generateSpriteSheet(idleRight, baseDir + "/idle-right.png", spriteSize, frames);
      generateSpriteSheet(idleLeft, baseDir + "/idle-left.png", spriteSize, frames);
      generateSpriteSheet(idleRight, baseDir + "/walk-right.png", spriteSize, frames);
      generateSpriteSheet(idleLeft, baseDir + "/walk-left.png", spriteSize, frames);
      generateSpriteSheet(idleRight, baseDir + "/run-right.png", spriteSize, frames);
      generateSpriteSheet(idleLeft, baseDir + "/run-left.png", spriteSize, frames);
      generateSpriteSheet(idleRight, baseDir + "/dash-right.png", spriteSize, frames);
      generateSpriteSheet(idleLeft, baseDir + "/dash-left.png", spriteSize, frames);
      
      System.out.println("✓ Todos os spritesheets foram criados!");
      
    } catch (IOException e) {
      System.err.println("Erro ao gerar spritesheets: " + e.getMessage());
      e.printStackTrace();
    }
  }
  
  private static void generateSpriteSheet(BufferedImage baseSprite, String outputPath, int spriteSize, int frames) throws IOException {
    BufferedImage sheet = new BufferedImage(spriteSize * frames, spriteSize, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = sheet.createGraphics();
    
    for (int i = 0; i < frames; i++) {
      g.drawImage(baseSprite, i * spriteSize, 0, null);
    }
    
    g.dispose();
    
    File outputFile = new File(outputPath);
    outputFile.getParentFile().mkdirs();
    ImageIO.write(sheet, "png", outputFile);
    System.out.println("✓ Criado: " + outputPath);
  }
}

