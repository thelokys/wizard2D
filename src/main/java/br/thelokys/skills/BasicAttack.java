package br.thelokys.skills;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import br.thelokys.constants.Tiles;
import br.thelokys.core.DirectionState;
import br.thelokys.entities.Player;
import br.thelokys.loaders.LoaderImage;
import br.thelokys.shared.GameObject;
import br.thelokys.shared.SoundPlayer;

public class BasicAttack extends Skill {
  private int damage;
  private int speed;
  private int lifetime = 0;
  private List<FireballProjectile> fireballs;

  public BasicAttack(GameObject owner) {
    super(owner, "FireBall", "Ataque básico", LoaderImage.from("/skills/hud/fireball.png"), 2);

    this.damage = 10;
    this.speed = 4;
    this.lifetime = 4;

    this.fireballs = new ArrayList<>();
  }

  @Override
  public void onActiveSkill(double deltaTime) {
    var player = (Player) getOwner();

    var offset = Tiles.MAX_TILE_SIZE;
    var offsetX = player.getDirection() == DirectionState.LEFT ? -offset * 2 : offset;

    var fireballProjectile = new FireballProjectile(
        player.getX() + player.getWidth() / 2 + offsetX,
        player.getY() + player.getHeight() / 2 - player.getHeight() / 2,
        player.getDirection(),
        speed,
        lifetime,
        damage);

    fireballs.add(fireballProjectile);
    playFireBallSound();
    System.out.println("skill activated - " + this.getName() + " - size: " + fireballs.size());
  }

  @Override
  public void render(Graphics2D g) {
    fireballs.forEach(fireball -> fireball.render(g));
  }

  @Override
  public void updateActive(double deltaTime) {
    var iterator = fireballs.iterator();
    while (iterator.hasNext()) {
      var current = iterator.next();
      current.update(deltaTime);

      if (current.shouldRemove()) {
        iterator.remove();
      }
    }
  }

  @Override
  public void updateCooldownTicks(double deltaTime) {
    super.updateCooldownTicks(deltaTime);

    var iterator = fireballs.iterator();
    while (iterator.hasNext()) {
      var current = iterator.next();
      current.update(deltaTime);

      if (current.shouldRemove()) {
        iterator.remove();
      }
    }
  }

  @Override
  public int getDamage() {
    return this.damage;
  }

  public void playFireBallSound() {
    SoundPlayer.play("/skills/sfx/fireball-woosh.wav");
  }

}
