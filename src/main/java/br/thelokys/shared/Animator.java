package br.thelokys.shared;

import java.awt.image.BufferedImage;
import java.util.Objects;

public class Animator {
  private SpriteSheet spriteSheet;
  private String currentAnimation;

  private Integer frameTime;
  private Integer currentFrame;
  private int accumulatedTime;
  private Boolean looping;
  private Boolean paused;

  public Animator(SpriteSheet spriteSheet, Integer frameDuration) {
    this.spriteSheet = spriteSheet;
    this.frameTime = frameDuration;
    this.currentFrame = 0;
    this.accumulatedTime = 0;
    this.looping = true;
    this.paused = true;
  }

  public void playAnimation(String animationName) {
    if (animationName.equals(currentAnimation)) {
      return;
    }

    this.currentAnimation = animationName;
    this.currentFrame = 0;
    this.accumulatedTime = 0;
    this.paused = false;
  }

  public void update() {
    accumulatedTime += 1;
    if (accumulatedTime >= frameTime) {
      accumulatedTime = 0;
      nextFrame();
    }
  }

  private void nextFrame() {
    if (paused || Objects.isNull(currentAnimation)) {
      return;
    }

    var frames = spriteSheet.getAnimation(currentAnimation);
    if (frames.isEmpty()) {
      return;
    }

    currentFrame++;

    if (currentFrame >= frames.size()) {
      if (looping) {
        currentFrame = 0;
      } else {
        currentFrame = frames.size() - 1;
        paused = true;
      }
    }
  }

  public BufferedImage getCurrentFrame() {
    if (currentAnimation == null) {
      // Se não há animação definida, retorna a primeira imagem única se existir
      if (!spriteSheet.getFrames().isEmpty()) {
        return spriteSheet.getFrame(0);
      }
      return null;
    }

    return spriteSheet.getFrame(currentAnimation, currentFrame);
  }

  public boolean isAnimationFinished() {
    if (currentAnimation == null || looping)
      return false;

    var frames = spriteSheet.getAnimation(currentAnimation);
    return currentFrame >= frames.size() - 1;
  }

  public int getCurrentFrameIndex() {
    return currentFrame;
  }

}
