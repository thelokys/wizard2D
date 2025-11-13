package br.thelokys.entities;

import java.awt.Graphics2D;
import java.util.List;

import br.thelokys.entities.stats.Health;
import br.thelokys.shared.CollisionBox2D;
import br.thelokys.shared.GameObject;
import br.thelokys.skills.Skill;

public abstract class Enemy extends CollisionBox2D {
  protected Health health;

  protected double cooldownAttack;
  protected double currentCooldownAttack;

  protected float speed;

  public Enemy(int x, int y, int width, int height) {
    this(x, y, null, width, height);
  }

  public Enemy(int x, int y, Health health, int width, int height) {
    super(x, y, width, height);
    this.health = health;
    this.cooldownAttack = 1 * 60;
    this.currentCooldownAttack = 0;
    this.speed = 2.0f;
  }

  public void defineHealth(Health health) {
    this.health = health;
  }

  public void receiveDamage(Skill skill) {
    health.takeDamage(skill.getDamage());
  }

  public abstract int getXpDrop();

  public abstract int getAmountDamage();

  public abstract void update();

  public void updateCooldownAttack() {
    if (!isReadyAttack()) {
      this.decreaseCooldownAttack();
    }

    this.tryAttack();
  }

  public abstract Boolean onEnemyAttack();

  public void tryAttack() {
    if (isReadyAttack()) {
      var hasAttack = this.onEnemyAttack();
      if (hasAttack) {
        this.resetCooldownAttack();
      }
    }
  }

  public abstract void render(Graphics2D g);

  public Boolean isReadyAttack() {
    return currentCooldownAttack <= 0;
  }

  public void resetCooldownAttack() {
    this.currentCooldownAttack = this.cooldownAttack;
  }

  public void decreaseCooldownAttack() {
    decreaseCooldownAttack(1);
  }

  public void decreaseCooldownAttack(double amount) {
    this.currentCooldownAttack -= amount;

    if (this.currentCooldownAttack < 0) {
      this.currentCooldownAttack = 0;
    }
  }

  public Health getHealth() {
    return health;
  }

  public void follow(GameObject target) {
    var dx = target.getX() - this.x;
    var dy = target.getY() - this.y;
    var distance = Math.sqrt(dx * dx + dy * dy);

    // Evita divisão por zero
    if (distance > 0) {
      this.x += (dx / distance) * speed;
      this.y += (dy / distance) * speed;
    }
  }

  public void avoidOtherEnemies(List<Enemy> others) {
    var personalSpace = 20f; // Espaço pessoal em pixels
    var maxRepulsion = 2.0f; // Repulsão máxima

    var totalRepulsionX = 0f;
    var totalRepulsionY = 0f;
    var repulsionCount = 0;

    for (var other : others) {
      if (other == this) {
        continue;
      }

      var dx = this.x - other.x;
      var dy = this.y - other.y;
      float distanceSquared = dx * dx + dy * dy;

      if (distanceSquared > 0 && distanceSquared < personalSpace * personalSpace) {
        float distance = (float) Math.sqrt(distanceSquared);
        float repulsionStrength = maxRepulsion * (1.0f - (distance / personalSpace));

        totalRepulsionX += (dx / distance) * repulsionStrength;
        totalRepulsionY += (dy / distance) * repulsionStrength;
        repulsionCount++;
      }
    }

    // Aplica a repulsão média se houver colisões
    if (repulsionCount > 0) {
      this.x += totalRepulsionX / repulsionCount;
      this.y += totalRepulsionY / repulsionCount;
    }
  }
}
