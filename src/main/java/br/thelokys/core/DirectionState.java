package br.thelokys.core;

public enum DirectionState {
  UP("up"),
  DOWN("down"),
  LEFT("left"),
  RIGHT("right");

  private String text;

  private DirectionState(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }
}
