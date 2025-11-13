package br.thelokys.core;

import javax.swing.JFrame;
import java.awt.Component;

public class GameWindow extends JFrame {

  public GameWindow(Component game) {
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Wizard 2D");

    add(game);

    pack();

    setLocationRelativeTo(null);
    setVisible(true);
  }
}
