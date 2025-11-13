package br.thelokys.core;

import javax.swing.JFrame;
import java.awt.Component;
import java.awt.Container;

import br.thelokys.menu.GameOverMenu;
import br.thelokys.menu.MainMenu;
import br.thelokys.menu.OptionsMenu;
import br.thelokys.shared.GameContext;

public class GameWindow extends JFrame {
  private MainMenu mainMenu;
  private OptionsMenu optionsMenu;
  private GameOverMenu gameOverMenu;
  private Game2D game;
  private Component currentComponent;

  public GameWindow() {
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Wizard 2D");

    // Inicializa os componentes
    this.mainMenu = new MainMenu(this);
    this.optionsMenu = new OptionsMenu(this);
    this.game = new Game2D(GameContext.get());
    this.game.setWindow(this);
    this.gameOverMenu = new GameOverMenu(this);
    
    // Inicia com o menu principal
    switchToMenu();
    
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  public void switchToMenu() {
    switchComponent(mainMenu);
  }

  public void switchToOptions() {
    switchComponent(optionsMenu);
  }

  public void switchToGame() {
    // Reseta o estado do jogo antes de iniciar
    game.resetGameState();
    switchComponent(game);
    game.initGame();
  }

  public void switchToGameOver() {
    switchComponent(gameOverMenu);
  }

  private void switchComponent(Component component) {
    Container contentPane = getContentPane();
    if (currentComponent != null) {
      contentPane.remove(currentComponent);
    }
    currentComponent = component;
    contentPane.add(component);
    component.requestFocusInWindow();
    pack();
    repaint();
  }

  public void updateSize(int width, int height) {
    // O tamanho será atualizado quando pack() for chamado
    // Os componentes já têm seus preferredSize configurados
  }
}
