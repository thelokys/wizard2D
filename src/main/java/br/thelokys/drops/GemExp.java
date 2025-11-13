package br.thelokys.drops;

import java.awt.Graphics2D;
import java.util.Map;

import br.thelokys.constants.Tiles;
import br.thelokys.loaders.LoaderImage;
import br.thelokys.shared.Animator;
import br.thelokys.shared.CollisionBox2D;
import br.thelokys.shared.GameContext;
import br.thelokys.shared.GameObject;
import br.thelokys.shared.SoundPlayer;
import br.thelokys.shared.SpriteSheet;

enum GemExpSprite {
  DEFAULT
}

public class GemExp extends CollisionBox2D {

  private Animator animator;
  private Boolean collected;
  private double speed;
  private int amountOfXp;

  public GemExp(GameObject droppedFrom) {
    super(
        droppedFrom.getX() + droppedFrom.getWidth() / 2 - Tiles.TILE_SIZE / 2,
        droppedFrom.getY() + droppedFrom.getHeight() / 2 - Tiles.TILE_SIZE / 2,
        Tiles.TILE_SIZE,
        Tiles.TILE_SIZE);

    this.collected = false;
    var textures = Map.of(
        GemExpSprite.DEFAULT.name(), LoaderImage.from("/misc/drop-exp.png"));

    this.speed = 1;

    var loaderSprite = new SpriteSheet(textures, 16, 16);
    this.animator = new Animator(loaderSprite, 5);
    this.animator.playAnimation(GemExpSprite.DEFAULT.name());
  }

  public void update() {
    followPlayer();
    this.speed += 0.2;

    checkPlayerCollectedGem();
    this.animator.update();
  }

  public void render(Graphics2D g) {
    var camera = GameContext.get().getCamera();

    var posX = (this.x - camera.getX());
    var posY = (this.y - camera.getY());

    var frame = animator.getCurrentFrame();
    g.drawImage(frame, posX, posY, width, height, null);
  }

  public void checkPlayerCollectedGem() {
    var player = GameContext.get().getPlayer();

    if (this.collide(player)) {
      player.getExp().gain();
      this.collected = true;
      this.playCollectedSound();
    }
  }

  public Boolean isCollected() {
    return collected;
  }

  private void playCollectedSound() {
    SoundPlayer.play("/entities/player/sfx/collect-exp.wav");
  }

  private void followPlayer() {
    var player = GameContext.get().getPlayer();

    float deltaX = player.getX() - x;
    float deltaY = player.getY() - y;

    float distancia = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

    if (distancia > 0) {
      deltaX /= distancia;
      deltaY /= distancia;

      x += deltaX * speed;
      y += deltaY * speed;
    }
  }

  public int getAmountOfXp() {
    return amountOfXp;
  }

}
