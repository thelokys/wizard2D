package br.thelokys.core;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DirectionInput extends KeyAdapter {
  private Map<Integer, DirectionState> keys;
  public List<DirectionState> heldDirections;
  public boolean isShiftPressed;

  public DirectionInput() {
    this.heldDirections = new ArrayList<>();
    this.isShiftPressed = false;
    this.keys = Map.of(
        KeyEvent.VK_W, DirectionState.UP,
        KeyEvent.VK_S, DirectionState.DOWN,
        KeyEvent.VK_A, DirectionState.LEFT,
        KeyEvent.VK_D, DirectionState.RIGHT);
  }


  @Override
  public void keyReleased(KeyEvent e) {
    var newDir = this.keys.get(e.getKeyCode());
    if (Objects.nonNull(newDir) && this.heldDirections.contains(newDir)) {
      this.heldDirections.remove(newDir);
    }
    
    if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
      this.isShiftPressed = false;
    }
  }
  
  @Override
  public void keyPressed(KeyEvent e) {
    var newDir = this.keys.get(e.getKeyCode());
    if (Objects.nonNull(newDir) && !this.heldDirections.contains(newDir)) {
      this.heldDirections.add(0, newDir);
    }
    
    if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
      this.isShiftPressed = true;
    }
  }
}
