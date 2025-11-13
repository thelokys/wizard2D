package br.thelokys.systems;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import br.thelokys.drops.GemExp;
import br.thelokys.shared.GameObject;

public class GemDropsSystem {

  private List<GemExp> gemExpDrops;

  public GemDropsSystem() {
    this.gemExpDrops = new ArrayList<>();
  }

  public void spawnGemExpDrop(GameObject droppedFrom) {

    this.gemExpDrops.add(new GemExp(droppedFrom));
  }

  public void update() {
    var iterator = gemExpDrops.iterator();
    while (iterator.hasNext()) {
      var current = iterator.next();
      current.update();

      if (current.isCollected()) {
        iterator.remove();
      }
    }
  }

  public void render(Graphics2D g) {
    this.gemExpDrops.forEach(gemExpDrop -> gemExpDrop.render(g));
  }

}
