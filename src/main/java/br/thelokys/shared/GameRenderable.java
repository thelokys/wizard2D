package br.thelokys.shared;

import java.awt.Graphics2D;

public interface GameRenderable {
  public void render(Graphics2D g, GameContext context);
}
