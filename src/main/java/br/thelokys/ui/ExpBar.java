package br.thelokys.ui;

import java.awt.Color;
import java.awt.Graphics2D;

import br.thelokys.entities.stats.Exp;

public class ExpBar extends UI {
  private Exp exp;

  public ExpBar(int x, int y, Exp exp) {
    super(x, y, UIAttributes.MAX_BAR_WIDTH, UIAttributes.MAX_BAR_HEIGHT);
    this.exp = exp;
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

    var widthBar = (int) (exp.getPercent() * (this.width - 10));

    g.setColor(new Color(59, 130, 246));
    g.fillRect(x + 5, y + 5, widthBar, height - 10);
  }
}
