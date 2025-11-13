package br.thelokys;

import br.thelokys.core.Game2D;
import br.thelokys.core.GameWindow;
import br.thelokys.shared.GameContext;

public class Main {
  public static void main(String[] args) {
    new GameWindow(new Game2D(GameContext.get()));
  }
}