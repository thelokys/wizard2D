package br.thelokys.shared;

import br.thelokys.constants.Tiles;

public class GameObject {
  protected int x, y, width, height;

  public GameObject() {
    this(0, 0);
  }

  public GameObject(int x, int y) {
    this(x, y, 0);
  }

  public GameObject(int x, int y, int size) {
    this(x, y, size, size);
  }

  public GameObject(GameObject object, int width, int height) {
    this(object.x, object.y, width, height);
  }

  public GameObject(GameObject object) {
    this(object.x, object.y, object.width, object.height);
  }

  public GameObject(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }

  public void setPositionFromTiles(int tileX, int tileY) {
    this.x = tileX * Tiles.MAX_TILE_SIZE;
    this.y = tileY * Tiles.MAX_TILE_SIZE;
  }

  @Override
  public String toString() {
    return "GameObject [x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + "]";
  }
}
