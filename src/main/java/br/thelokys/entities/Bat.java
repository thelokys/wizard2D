package br.thelokys.entities;

import java.awt.Graphics2D;
import java.util.Map;

import br.thelokys.constants.Tiles;
import br.thelokys.entities.stats.Health;
import br.thelokys.loaders.LoaderImage;
import br.thelokys.shared.Animator;
import br.thelokys.shared.GameContext;
import br.thelokys.shared.SpriteSheet;

enum BatSprite {
  FLY_LEFT, FLY_RIGHT;
}

public class Bat extends Enemy {
  private final String ASSETS_PATH = "/entities/bat/sprites/";
  private final int SPRITE_SIZE = 32;

  private Animator animator;
  private BatSprite currentSprite;

  public Bat(int x, int y) {
    super(x, y, new Health(10, 10), Tiles.MAX_TILE_SIZE, Tiles.MAX_TILE_SIZE);

    var sprites = Map.of(
        BatSprite.FLY_LEFT.name(), LoaderImage.from(ASSETS_PATH + "fly-left.png"),
        BatSprite.FLY_RIGHT.name(), LoaderImage.from(ASSETS_PATH + "fly-right.png"));

    this.currentSprite = BatSprite.FLY_RIGHT;
    var loaderSprite = new SpriteSheet(sprites, SPRITE_SIZE, SPRITE_SIZE);
    this.animator = new Animator(loaderSprite, 10);
    this.animator.playAnimation(this.currentSprite.name());
  }

  @Override
  public int getXpDrop() {
    return 10;
  }

  @Override
  public int getAmountDamage() {
    return 10;
  }

  @Override
  public void update() {
    var player = GameContext.get().getPlayer();
    var enemies = GameContext.get().getEnemiesSystem().getEnemies();

    // Movimentação
    follow(player);
    avoidOtherEnemies(enemies);

    // Ataque
    updateCooldownAttack();
    tryAttack();

    // Animação
    animator.update();
  }

  @Override
  public void render(Graphics2D g) {
    var camera = GameContext.get().getCamera();

    var posX = (this.x - camera.getX());
    var posY = (this.y - camera.getY());

    var frame = animator.getCurrentFrame();
    g.drawImage(frame, posX, posY, width, height, null);
  }

  @Override
  public Boolean onEnemyAttack() {
    var player = GameContext.get().getPlayer();

    if (this.collide(player)) {
      player.takeDamage(getAmountDamage());
      return true;
    }

    return false;
  }

}
