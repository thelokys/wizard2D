package br.thelokys.ui;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import br.thelokys.shared.GameContext;

public class Hud {
  private List<UI> UIStats;

  public Hud() {
    this.UIStats = new ArrayList<>();

    var player = GameContext.get().getPlayer();

    var healtBar = new HealthBar(
        UIAttributes.BAR_START_X,
        UIAttributes.BAR_START_Y,
        player.getHealth());

    var expBar = new ExpBar(UIAttributes.BAR_START_X,
        healtBar.getHeight() + UIAttributes.BAR_START_Y + UIAttributes.GAP_BETWEEN_BAR,
        player.getExp());

    var skillsBar = new SkillsBar(expBar.getX(),
        expBar.getY() + UIAttributes.BAR_START_Y + UIAttributes.GAP_BETWEEN_BAR,
        UIAttributes.MAX_BAR_WIDTH, UIAttributes.MAX_BAR_HEIGHT,
        UIAttributes.SKILL_ICON_SIZE, UIAttributes.GAP_BETWEEN_ICON_SKILLS);

    this.UIStats.add(healtBar);
    this.UIStats.add(expBar);
    this.UIStats.add(skillsBar);

  }

  public void update() {
    this.UIStats.forEach(ui -> ui.update());
  }

  public void render(Graphics2D g) {
    this.UIStats.forEach(ui -> ui.render(g));
  }
}
