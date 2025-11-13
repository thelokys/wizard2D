package br.thelokys.ui;

import java.awt.Color;
import java.awt.Graphics2D;

import br.thelokys.entities.stats.Health;

public class HealthBar extends UI {

  private Health health;

  public HealthBar(int x, int y, Health health) {
    super(x, y, UIAttributes.MAX_BAR_WIDTH, UIAttributes.MAX_BAR_HEIGHT);
    this.health = health;
  }

  @Override
  public void render(Graphics2D g) {
    renderBar(g);
  }

  private void renderBar(Graphics2D g) {
    g.setColor(Color.BLACK);
    g.fillRect(x, y, width, height);

    g.setColor(Color.DARK_GRAY);
    g.fillRect(x + 5, y + 4, width - 10, height - 10);

    var widthBar = (int) (health.getPercent() * (this.width - 10));

    g.setColor(new Color(220, 38, 38));
    g.fillRect(x + 5, y + 4, widthBar, height - 10);
  }
}
