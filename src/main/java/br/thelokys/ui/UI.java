package br.thelokys.ui;

import java.awt.Graphics2D;

import br.thelokys.shared.GameObject;

public abstract class UI extends GameObject {

  public UI() {
    super();
  }

  public UI(int x, int y, int width, int height) {
    super(x, y, width, height);
  }

  public void render(Graphics2D g) {
  }

  public void update() {
  }
}
