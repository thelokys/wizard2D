package br.thelokys.core;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import br.thelokys.shared.SoundPlayer;

public abstract class GameRunner extends JPanel implements Runnable {

  private Thread gameLoop;
  private Boolean running = false;
  public static final int TARGET_FPS = 60;

  public abstract void update(double deltaTime);

  public abstract void render(Graphics2D g);

  @Override
  public void run() {
    double drawInterval = 1_000_000_000 / TARGET_FPS;
    double delta = 0;
    long lastTime = System.nanoTime();
    int timer = 0;
    int fps = 0;

    while (running) {
      long currentTime = System.nanoTime();

      long elapsed = currentTime - lastTime;
      delta += elapsed / drawInterval;
      timer += elapsed;
      lastTime = currentTime;

      if (delta >= 1) {
        update(1 / TARGET_FPS);
        this.repaint();
        delta--;
        fps++;
      }

      if (timer >= 1_000_000_000) {
        System.out.println("FPS: " + fps);
        fps = 0;
        timer = 0;
      }
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    this.render((Graphics2D) g);
  }

  public void startGame() {
    if (!this.running) {
      this.running = true;
      this.gameLoop = new Thread(this);
      this.gameLoop.start();
    }
  }

  public void stopGame() {
    this.running = false;
    if (this.gameLoop != null) {
      try {
        this.gameLoop.join(100); // Espera até 100ms para a thread terminar
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }
}
