package br.thelokys.skills;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;

import br.thelokys.constants.Tiles;
import br.thelokys.core.DirectionState;
import br.thelokys.loaders.LoaderImage;
import br.thelokys.shared.Animator;
import br.thelokys.shared.CollisionBox2D;
import br.thelokys.shared.GameContext;
import br.thelokys.shared.SoundPlayer;
import br.thelokys.shared.SpriteSheet;

public class FireballProjectile extends CollisionBox2D {

  private Map<String, BufferedImage> sprites;
  private int speed;
  private int currentLifetime;
  private SpriteSheet sheet;
  private Animator animator;
  private int damage;

  public FireballProjectile(int x, int y, DirectionState directionState, int speed, int lifetime, int damage) {
    super(x, y, Tiles.MAX_TILE_SIZE, Tiles.MAX_TILE_SIZE);
    this.damage = damage;

    this.currentLifetime = lifetime * 60;
    this.speed = directionState == DirectionState.LEFT ? -speed : speed;

    this.sprites = Map.of(
        DirectionState.LEFT.name(), LoaderImage.from("/skills/fireball-left.png"),
        DirectionState.RIGHT.name(), LoaderImage.from("/skills/fireball-right.png"));

    this.sheet = new SpriteSheet(this.sprites, Tiles.TILE_SIZE, Tiles.TILE_SIZE);
    this.animator = new Animator(sheet, 10);
    this.animator.playAnimation(directionState.name());
  }

  public void update(double deltaTime) {
    x += speed;
    currentLifetime--;
    animator.update();

    checkCollisionEnemies();
  }

  public void render(Graphics2D g) {
    var camera = GameContext.get().getCamera();
    var screenX = (this.x - camera.getX());
    var screenY = (this.y - camera.getY());

    var frame = animator.getCurrentFrame();
    g.drawImage(frame, screenX, screenY, width, height, null);
  }

  public boolean shouldRemove() {
    return currentLifetime <= 0;
  }

  private void checkCollisionEnemies() {
    var enemies = GameContext.get().getEnemiesSystem().getEnemies();

    enemies.forEach(enemy -> {
      if (enemy.collide(this)) {
        enemy.getHealth().takeDamage(damage);
        currentLifetime = 0;
        playFireballHitTargetSound();
      }
    });
  }

  private void playFireballHitTargetSound() {
    SoundPlayer.play("/entities/player/sfx/fireball-damage-received.wav");
  }

}
