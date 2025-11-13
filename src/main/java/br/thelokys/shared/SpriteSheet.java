package br.thelokys.shared;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpriteSheet {
  private int spriteWidth;
  private int spriteHeight;
  private Map<String, List<BufferedImage>> animations;
  private List<BufferedImage> frames;

  public SpriteSheet(Map<String, BufferedImage> sheet, int spriteWidth, int spriteHeight) {
    this.spriteWidth = spriteWidth;
    this.spriteHeight = spriteHeight;

    this.animations = new HashMap<>();
    this.frames = new ArrayList<>();

    initializeAnimations(sheet);
  }

  private void initializeAnimations(Map<String, BufferedImage> animationFiles) {
    for (var entry : animationFiles.entrySet()) {
      var animationName = entry.getKey();
      var image = entry.getValue();

      var frames = extractFramesFromImage(image);
      if (!frames.isEmpty()) {
        animations.put(animationName, frames);
      }
    }
  }

  private List<BufferedImage> extractFramesFromImage(BufferedImage image) {
    List<BufferedImage> frames = new ArrayList<>();
    var aFrameCols = image.getWidth() / spriteWidth;

    for (var col = 0; col < aFrameCols; col++) {
      var x = col * spriteWidth;

      // nao deixa passar da largura da imagem
      if (x + spriteWidth <= image.getWidth()) {
        var frame = image.getSubimage(x, 0, spriteWidth, spriteHeight);
        frames.add(frame);
      }
    }

    return frames;
  }

  public List<BufferedImage> getAnimation(String animationName) {
    return animations.getOrDefault(animationName, new ArrayList<>());
  }

  public BufferedImage getFrame(String animationName, int frameIndex) {
    var frames = getAnimation(animationName);
    if (frames.isEmpty() || frameIndex >= frames.size()) {
      return null;
    }
    return frames.get(frameIndex);
  }

  public List<BufferedImage> getFrames() {
    return new ArrayList<>(frames);
  }

  public BufferedImage getFrame(int index) {
    if (index < 0 || index >= frames.size()) {
      return null;
    }
    return frames.get(index);
  }

  public Map<String, List<BufferedImage>> getAnimations() {
    return new HashMap<>(animations);
  }

}
