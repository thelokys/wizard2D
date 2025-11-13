package br.thelokys.core;

import java.awt.Dimension;
import java.awt.Graphics2D;

import br.thelokys.constants.Screen;
import br.thelokys.shared.GameContext;
import br.thelokys.shared.SoundPlayer;

public class Game2D extends GameRunner {
  private GameContext context;

  public Game2D(GameContext context) {
    this.context = context;
    setPreferredSize(new Dimension(Screen.MAX_SCREEN_WIDTH, Screen.MAX_SCREEN_HEIGHT));
    setFocusable(true);
    addKeyListener(context.getDirectionInput());
    this.startGame();
    SoundPlayer.play("/musics/backgrounds/background4.wav");
  }

  @Override
  public void update(double deltaTime) {
    context.getCamera().update(deltaTime);
    context.getCamera().follow(context.getPlayer());
    context.getEnemiesSystem().update();
    context.getGemDropsSystem().update();
    context.getPlayer().update(deltaTime);
    context.getHud().update();
    context.getSpawnerSystem().update();
  }

  @Override
  public void render(Graphics2D g) {
    context.getTerrain().render(g);
    context.getEnemiesSystem().render(g);
    context.getGemDropsSystem().render(g);
    context.getPlayer().render(g);
    context.getHud().render(g);
  }
}
