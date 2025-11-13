package br.thelokys.entities.stats;

public class Exp extends Stat {
  private int level;

  public Exp(int max) {
    super(0, max);
    this.level = 1;
    this.max = max;
  }

  public void gain() {
    this.gain(0.1f * max);
  }

  public void gain(float amount) {
    current += amount;

    if (current >= max) {
      current = max;
    }
  }

  public int getLevel() {
    return level;
  }
}