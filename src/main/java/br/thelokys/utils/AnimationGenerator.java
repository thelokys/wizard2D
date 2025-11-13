package br.thelokys.utils;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Gerador de animações que cria frames animados reais a partir dos sprites base.
 * Cria variações visuais para cada tipo de animação.
 */
public class AnimationGenerator {
  
  public static void main(String[] args) {
    try {
      String baseDir = "src/main/resources/entities/player/sprites";
      
      // Carrega o primeiro frame do sprite original (se for spritesheet, pega o primeiro frame)
      BufferedImage idleRightSheet = ImageIO.read(new File(baseDir + "/idle-right.png"));
      BufferedImage idleLeftSheet = ImageIO.read(new File(baseDir + "/idle-left.png"));
      
      // Tamanho do sprite no jogo (MAX_TILE_SIZE = 48)
      int spriteSize = 48;
      int frames = 4;
      
      // Extrai o primeiro frame se for spritesheet
      BufferedImage idleRight = extractFirstFrame(idleRightSheet, spriteSize);
      BufferedImage idleLeft = extractFirstFrame(idleLeftSheet, spriteSize);
      
      // Gera animações com variações reais
      System.out.println("Gerando animações com variações reais...\n");
      
      generateIdleAnimation(idleRight, baseDir + "/idle-right.png", spriteSize, frames);
      generateIdleAnimation(idleLeft, baseDir + "/idle-left.png", spriteSize, frames);
      
      generateWalkAnimation(idleRight, baseDir + "/walk-right.png", spriteSize, frames);
      generateWalkAnimation(idleLeft, baseDir + "/walk-left.png", spriteSize, frames);
      
      generateRunAnimation(idleRight, baseDir + "/run-right.png", spriteSize, frames);
      generateRunAnimation(idleLeft, baseDir + "/run-left.png", spriteSize, frames);
      
      generateDashAnimation(idleRight, baseDir + "/dash-right.png", spriteSize, frames);
      generateDashAnimation(idleLeft, baseDir + "/dash-left.png", spriteSize, frames);
      
      System.out.println("\n✓ Todas as animações foram geradas com sucesso!");
      
    } catch (IOException e) {
      System.err.println("Erro ao gerar animações: " + e.getMessage());
      e.printStackTrace();
    }
  }
  
  private static BufferedImage extractFirstFrame(BufferedImage sheet, int spriteSize) {
    // Se a imagem é um spritesheet horizontal, extrai o primeiro frame
    int originalFrameWidth = sheet.getHeight(); // Assume frames quadrados baseados na altura
    if (sheet.getWidth() > originalFrameWidth) {
      // É um spritesheet - extrai o primeiro frame e redimensiona
      BufferedImage firstFrame = sheet.getSubimage(0, 0, originalFrameWidth, originalFrameWidth);
      BufferedImage frame = new BufferedImage(spriteSize, spriteSize, BufferedImage.TYPE_INT_ARGB);
      Graphics2D g = frame.createGraphics();
      g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
      g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
      g.drawImage(firstFrame, 0, 0, spriteSize, spriteSize, null);
      g.dispose();
      return frame;
    }
    // Se não, redimensiona para spriteSize x spriteSize
    BufferedImage frame = new BufferedImage(spriteSize, spriteSize, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = frame.createGraphics();
    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g.drawImage(sheet, 0, 0, spriteSize, spriteSize, null);
    g.dispose();
    return frame;
  }
  
  /**
   * Gera animação idle com pequenas variações sutis (respiração)
   */
  private static void generateIdleAnimation(BufferedImage base, String outputPath, int spriteSize, int frames) throws IOException {
    BufferedImage sheet = new BufferedImage(spriteSize * frames, spriteSize, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = sheet.createGraphics();
    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
    
    for (int i = 0; i < frames; i++) {
      BufferedImage frame = new BufferedImage(spriteSize, spriteSize, BufferedImage.TYPE_INT_ARGB);
      Graphics2D frameG = frame.createGraphics();
      frameG.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
      
      // Variação sutil: movimento vertical pequeno (respiração)
      double offsetY = Math.sin(i * Math.PI * 2 / frames) * 1.5;
      frameG.translate(0, offsetY);
      frameG.drawImage(base, 0, 0, null);
      
      frameG.dispose();
      g.drawImage(frame, i * spriteSize, 0, null);
    }
    
    g.dispose();
    saveImage(sheet, outputPath);
    System.out.println("✓ Criado: " + outputPath + " (idle com respiração)");
  }
  
  /**
   * Gera animação de caminhada com movimento de pernas alternando
   */
  private static void generateWalkAnimation(BufferedImage base, String outputPath, int spriteSize, int frames) throws IOException {
    BufferedImage sheet = new BufferedImage(spriteSize * frames, spriteSize, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = sheet.createGraphics();
    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
    
    for (int i = 0; i < frames; i++) {
      BufferedImage frame = new BufferedImage(spriteSize, spriteSize, BufferedImage.TYPE_INT_ARGB);
      Graphics2D frameG = frame.createGraphics();
      frameG.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
      
      // Movimento de caminhada: alterna entre posições
      double cycle = i * Math.PI * 2 / frames;
      
      // Movimento vertical (passos)
      double offsetY = Math.sin(cycle) * 2.0;
      
      // Rotação sutil do corpo (balanço)
      double rotation = Math.sin(cycle) * 0.05;
      
      // Transformação combinada
      AffineTransform transform = new AffineTransform();
      transform.translate(spriteSize / 2.0, spriteSize / 2.0);
      transform.rotate(rotation);
      transform.translate(-spriteSize / 2.0, -spriteSize / 2.0);
      transform.translate(0, offsetY);
      
      frameG.setTransform(transform);
      frameG.drawImage(base, 0, 0, null);
      
      frameG.dispose();
      g.drawImage(frame, i * spriteSize, 0, null);
    }
    
    g.dispose();
    saveImage(sheet, outputPath);
    System.out.println("✓ Criado: " + outputPath + " (walk com movimento de pernas)");
  }
  
  /**
   * Gera animação de corrida com movimento mais rápido e dinâmico
   */
  private static void generateRunAnimation(BufferedImage base, String outputPath, int spriteSize, int frames) throws IOException {
    BufferedImage sheet = new BufferedImage(spriteSize * frames, spriteSize, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = sheet.createGraphics();
    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
    
    for (int i = 0; i < frames; i++) {
      BufferedImage frame = new BufferedImage(spriteSize, spriteSize, BufferedImage.TYPE_INT_ARGB);
      Graphics2D frameG = frame.createGraphics();
      frameG.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
      
      // Movimento de corrida: mais rápido e exagerado
      double cycle = i * Math.PI * 2 / frames;
      
      // Movimento vertical mais pronunciado
      double offsetY = Math.sin(cycle * 2) * 3.0;
      
      // Rotação mais acentuada (corpo inclinado)
      double rotation = Math.sin(cycle * 2) * 0.1;
      
      // Leve compressão horizontal (efeito de velocidade)
      double scaleX = 1.0 - Math.abs(Math.sin(cycle * 2)) * 0.1;
      
      AffineTransform transform = new AffineTransform();
      transform.translate(spriteSize / 2.0, spriteSize / 2.0);
      transform.scale(scaleX, 1.0);
      transform.rotate(rotation);
      transform.translate(-spriteSize / 2.0, -spriteSize / 2.0);
      transform.translate(0, offsetY);
      
      frameG.setTransform(transform);
      frameG.drawImage(base, 0, 0, null);
      
      frameG.dispose();
      g.drawImage(frame, i * spriteSize, 0, null);
    }
    
    g.dispose();
    saveImage(sheet, outputPath);
    System.out.println("✓ Criado: " + outputPath + " (run com movimento dinâmico)");
  }
  
  /**
   * Gera animação de dash com efeito de movimento rápido/borrado
   */
  private static void generateDashAnimation(BufferedImage base, String outputPath, int spriteSize, int frames) throws IOException {
    BufferedImage sheet = new BufferedImage(spriteSize * frames, spriteSize, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = sheet.createGraphics();
    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
    
    for (int i = 0; i < frames; i++) {
      BufferedImage frame = new BufferedImage(spriteSize, spriteSize, BufferedImage.TYPE_INT_ARGB);
      Graphics2D frameG = frame.createGraphics();
      frameG.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
      
      // Efeito de dash: movimento horizontal rápido com estiramento
      double progress = (double) i / frames;
      
      // Estiramento horizontal (efeito de velocidade)
      double stretchX = 1.0 + Math.sin(progress * Math.PI) * 0.3;
      double compressY = 1.0 - Math.sin(progress * Math.PI) * 0.15;
      
      // Movimento horizontal
      double offsetX = progress * 8.0 - 4.0;
      
      // Transparência variável (efeito de movimento)
      float alpha = 0.7f + (float)(Math.sin(progress * Math.PI) * 0.3f);
      frameG.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
      
      AffineTransform transform = new AffineTransform();
      transform.translate(spriteSize / 2.0, spriteSize / 2.0);
      transform.scale(stretchX, compressY);
      transform.translate(-spriteSize / 2.0, -spriteSize / 2.0);
      transform.translate(offsetX, 0);
      
      frameG.setTransform(transform);
      frameG.drawImage(base, 0, 0, null);
      
      frameG.dispose();
      g.drawImage(frame, i * spriteSize, 0, null);
    }
    
    g.dispose();
    saveImage(sheet, outputPath);
    System.out.println("✓ Criado: " + outputPath + " (dash com efeito de velocidade)");
  }
  
  private static void saveImage(BufferedImage image, String path) throws IOException {
    File outputFile = new File(path);
    outputFile.getParentFile().mkdirs();
    ImageIO.write(image, "png", outputFile);
  }
}

