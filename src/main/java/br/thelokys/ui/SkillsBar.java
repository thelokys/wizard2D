package br.thelokys.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import br.thelokys.shared.GameContext;
import br.thelokys.skills.Skill;

public class SkillsBar extends UI {

  private List<Skill> skills;
  private int gapX;
  private int iconSize;

  public SkillsBar(int startX, int startY, int widthBar, int heightBar, int iconSize, int gapX) {
    super(startX, startY, widthBar, heightBar);
    this.skills = GameContext.get().getPlayer().getSkills();
    this.iconSize = iconSize;
    this.gapX = gapX;
  }

  @Override
  public void render(Graphics2D g) {
    g.setColor(Color.WHITE);

    for (int i = 0; i < skills.size(); i++) {
      var skill = skills.get(i);
      int iconPosX = x + i * (iconSize + gapX);
      skill.renderIcon(g, iconPosX, y, iconSize);
    }
  }
}
