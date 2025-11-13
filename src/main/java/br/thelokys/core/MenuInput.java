package br.thelokys.core;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public class MenuInput extends KeyAdapter {
    private Consumer<Integer> onEnterPressed;
    private Consumer<Integer> onUpPressed;
    private Consumer<Integer> onDownPressed;
    private Consumer<Integer> onEscapePressed;
    
    public MenuInput() {
    }
    
    public void setOnEnterPressed(Consumer<Integer> callback) {
        this.onEnterPressed = callback;
    }
    
    public void setOnUpPressed(Consumer<Integer> callback) {
        this.onUpPressed = callback;
    }
    
    public void setOnDownPressed(Consumer<Integer> callback) {
        this.onDownPressed = callback;
    }
    
    public void setOnEscapePressed(Consumer<Integer> callback) {
        this.onEscapePressed = callback;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                if (onEnterPressed != null) {
                    onEnterPressed.accept(e.getKeyCode());
                }
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                if (onUpPressed != null) {
                    onUpPressed.accept(e.getKeyCode());
                }
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                if (onDownPressed != null) {
                    onDownPressed.accept(e.getKeyCode());
                }
                break;
            case KeyEvent.VK_ESCAPE:
                if (onEscapePressed != null) {
                    onEscapePressed.accept(e.getKeyCode());
                }
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                if (onUpPressed != null) {
                    onUpPressed.accept(e.getKeyCode());
                }
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                if (onDownPressed != null) {
                    onDownPressed.accept(e.getKeyCode());
                }
                break;
        }
    }
}

