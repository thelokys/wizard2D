package br.thelokys.entities.stats;

public class Stat {
  protected int current;
  protected int max;

  public Stat(int current, int max) {
    if (current > max) {
      throw new Error(String.format("O current: %s nao pode ser maior max: %s", current, max));
    }
    this.current = current;
    this.max = max;
  }

  public int getCurrent() {
    return current;
  }

  public int getMax() {
    return max;
  }

  public float getPercent() {
    return (float) current / max;
  }

  public void set(int value) {
    this.current = Math.max(0, Math.min(value, max));
  }

  public void add(int amount) {
    set(current + amount);
  }

  public void remove(int amount) {
    set(current - amount);
  }

  public boolean isEmpty() {
    return current <= 0;
  }

  public boolean isFull() {
    return current >= max;
  }
}
