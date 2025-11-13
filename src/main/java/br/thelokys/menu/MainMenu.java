package br.thelokys.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;

import br.thelokys.core.GameRunner;
import br.thelokys.core.GameSettings;
import br.thelokys.core.GameWindow;
import br.thelokys.core.MenuInput;

public class MainMenu extends GameRunner {
    private GameWindow window;
    private MenuInput menuInput;
    private int selectedOption = 0;
    private final String[] options = {"Iniciar", "Opções", "Sair"};
    
    public MainMenu(GameWindow window) {
        this.window = window;
        this.menuInput = new MenuInput();
        
        var settings = GameSettings.get();
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
            case 0: // Iniciar
                launchGame();
                break;
            case 1: // Opções
                openOptions();
                break;
            case 2: // Sair
                System.exit(0);
                break;
        }
    }
    
    private void launchGame() {
        window.switchToGame();
    }
    
    private void openOptions() {
        window.switchToOptions();
    }
    
    @Override
    public void update(double deltaTime) {
        // Menu não precisa de update contínuo
    }
    
    @Override
    public void render(Graphics2D g) {
        // Fundo escuro
        g.setColor(new Color(20, 20, 30));
        g.fillRect(0, 0, getWidth(), getHeight());
        
        var settings = GameSettings.get();
        int centerX = settings.getWidth() / 2;
        int startY = settings.getHeight() / 3;
        
        // Título
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        String title = "WIZARD 2D";
        int titleWidth = g.getFontMetrics().stringWidth(title);
        g.drawString(title, centerX - titleWidth / 2, startY);
        
        // Opções do menu
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        int optionSpacing = 60;
        
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
            int y = startY + 100 + (i * optionSpacing);
            
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

