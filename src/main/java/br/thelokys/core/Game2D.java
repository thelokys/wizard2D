package br.thelokys.core;

import java.awt.Dimension;
import java.awt.Graphics2D;

import br.thelokys.shared.GameContext;
import br.thelokys.shared.SoundPlayer;

public class Game2D extends GameRunner {
  private GameContext context;
  private boolean gameStarted = false;
  private GameWindow window;
  private boolean gameOverTriggered = false;

  public Game2D(GameContext context) {
    this.context = context;
    var settings = GameSettings.get();
    setPreferredSize(new Dimension(settings.getWidth(), settings.getHeight()));
    setFocusable(true);
    addKeyListener(context.getDirectionInput());
  }

  public void setWindow(GameWindow window) {
    this.window = window;
  }

  public void initGame() {
    // Reseta o estado do jogo
    gameOverTriggered = false;
    
    if (!gameStarted) {
      super.startGame();
      SoundPlayer.play("/musics/backgrounds/background4.wav");
      gameStarted = true;
    }
  }
  
  public void resetGameState() {
    // Para o jogo atual antes de reiniciar
    stopGame();
    // Permite reiniciar o jogo após game over
    gameStarted = false;
    gameOverTriggered = false;
  }

  @Override
  public void update(double deltaTime) {
    // Verifica se o player está morto
    if (context.getPlayer().getHealth().isDead() && !gameOverTriggered) {
      gameOverTriggered = true;
      if (window != null) {
        window.switchToGameOver();
      }
      return;
    }

    // Se o player está morto, não atualiza o jogo
    if (context.getPlayer().getHealth().isDead()) {
      return;
    }

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
