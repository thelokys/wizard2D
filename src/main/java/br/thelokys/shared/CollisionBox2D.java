package br.thelokys.shared;

public class CollisionBox2D extends GameObject implements GameCollisionable {

  public CollisionBox2D() {
    super();
  }

  public CollisionBox2D(int x, int y, int width, int height) {
    super(x, y, width, height);
  }

  public CollisionBox2D(GameObject from, int width, int height) {
    super(from, width, height);
  }

  /**
   * Verifica colisão com outra CollisionBox2D
   */
  public boolean collide(CollisionBox2D other) {
    return this.x < other.x + other.width &&
        this.x + this.width > other.x &&
        this.y < other.y + other.height &&
        this.y + this.height > other.y;
  }
}