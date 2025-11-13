package br.thelokys.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;

import br.thelokys.core.GameRunner;
import br.thelokys.core.GameSettings;
import br.thelokys.core.GameWindow;
import br.thelokys.core.MenuInput;

public class GameOverMenu extends GameRunner {
    private GameWindow window;
    private MenuInput menuInput;
    private int selectedOption = 0;
    private final String[] options = {"Tentar Novamente", "Menu Principal"};
    private GameSettings settings;
    
    public GameOverMenu(GameWindow window) {
        this.window = window;
        this.menuInput = new MenuInput();
        this.settings = GameSettings.get();
        
        setPreferredSize(new Dimension(settings.getWidth(), settings.getHeight()));
        setFocusable(true);
        addKeyListener(menuInput);
        
        setupInputHandlers();
        startGame();
    }
    
    private void setupInputHandlers() {
        menuInput.setOnUpPressed(keyCode -> {
            selectedOption = (selectedOption - 1 + options.length) % options.length;
        });
        
        menuInput.setOnDownPressed(keyCode -> {
            selectedOption = (selectedOption + 1) % options.length;
        });
        
        menuInput.setOnEnterPressed(keyCode -> {
            handleOptionSelected();
        });
    }
    
    private void handleOptionSelected() {
        switch (selectedOption) {
            case 0: // Tentar Novamente
                restartGame();
                break;
            case 1: // Menu Principal
                window.switchToMenu();
                break;
        }
    }
    
    private void restartGame() {
        // Reseta o jogo e inicia novamente
        var context = br.thelokys.shared.GameContext.get();
        context.resetGame();
        window.switchToGame();
    }
    
    @Override
    public void update(double deltaTime) {
        // Menu não precisa de update contínuo
    }
    
    @Override
    public void render(Graphics2D g) {
        // Fundo escuro com transparência
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, getWidth(), getHeight());
        
        int centerX = settings.getWidth() / 2;
        int startY = settings.getHeight() / 3;
        
        // Título GAME OVER
        g.setColor(new Color(220, 38, 38));
        g.setFont(new Font("Arial", Font.BOLD, 64));
        String title = "GAME OVER";
        int titleWidth = g.getFontMetrics().stringWidth(title);
        g.drawString(title, centerX - titleWidth / 2, startY);
        
        // Mensagem
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        String message = "Você foi derrotado!";
        int msgWidth = g.getFontMetrics().stringWidth(message);
        g.drawString(message, centerX - msgWidth / 2, startY + 60);
        
        // Opções do menu
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        int optionSpacing = 70;
        
        for (int i = 0; i < options.length; i++) {
            if (i == selectedOption) {
                g.setColor(new Color(100, 200, 255));
                g.setFont(new Font("Arial", Font.BOLD, 36));
            } else {
                g.setColor(Color.GRAY);
                g.setFont(new Font("Arial", Font.PLAIN, 32));
            }
            
            String option = options[i];
            int optionWidth = g.getFontMetrics().stringWidth(option);
            int y = startY + 150 + (i * optionSpacing);
            
            if (i == selectedOption) {
                // Desenha seta de seleção
                g.drawString(">", centerX - optionWidth / 2 - 40, y);
            }
            
            g.drawString(option, centerX - optionWidth / 2, y);
        }
        
        // Instruções
        g.setColor(new Color(150, 150, 150));
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        String instructions = "Use ↑↓ ou W/S para navegar, Enter para selecionar";
        int instWidth = g.getFontMetrics().stringWidth(instructions);
        g.drawString(instructions, centerX - instWidth / 2, settings.getHeight() - 30);
    }
}

