package br.thelokys.entities.stats;

public class Health extends Stat {
  private Boolean isAlive;

  public Health(int initHealth, int maxHealth) {
    super(initHealth, maxHealth);
    this.isAlive = true;
  }

  public void takeDamage(int damage) {
    if (damage < 0) {
      damage = 0;
    }

    remove(damage);
    this.isAlive = !this.isEmpty();
  }

  public void heal(int amount) {
    add(amount);
  }

  public Boolean isAlive() {
    return isAlive;
  }

  public Boolean isDead() {
    return !isAlive;
  }
}
