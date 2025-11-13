package br.thelokys.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;

import br.thelokys.core.GameRunner;
import br.thelokys.core.GameSettings;
import br.thelokys.core.GameWindow;
import br.thelokys.core.MenuInput;

public class OptionsMenu extends GameRunner {
    private GameWindow window;
    private MenuInput menuInput;
    private int selectedOption = 0;
    private final String[] options = {"Resolução", "Voltar"};
    private GameSettings settings;
    
    public OptionsMenu(GameWindow window) {
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
            if (selectedOption == 0) {
                // Muda resolução para anterior
                settings.previousResolution();
                updateWindowSize();
            } else {
                selectedOption = (selectedOption - 1 + options.length) % options.length;
            }
        });
        
        menuInput.setOnDownPressed(keyCode -> {
            if (selectedOption == 0) {
                // Muda resolução para próxima
                settings.nextResolution();
                updateWindowSize();
            } else {
                selectedOption = (selectedOption + 1) % options.length;
            }
        });
        
        menuInput.setOnEnterPressed(keyCode -> {
            handleOptionSelected();
        });
        
        menuInput.setOnEscapePressed(keyCode -> {
            window.switchToMenu();
        });
    }
    
    private void updateWindowSize() {
        window.updateSize(settings.getWidth(), settings.getHeight());
        setPreferredSize(new Dimension(settings.getWidth(), settings.getHeight()));
        // Atualiza a câmera também
        var context = br.thelokys.shared.GameContext.get();
        if (context != null) {
            context.getCamera().updateSize();
        }
        window.pack();
    }
    
    private void handleOptionSelected() {
        switch (selectedOption) {
            case 0: // Resolução
                settings.nextResolution();
                updateWindowSize();
                break;
            case 1: // Voltar
                window.switchToMenu();
                break;
        }
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
        
        int centerX = settings.getWidth() / 2;
        int startY = settings.getHeight() / 4;
        
        // Título
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        String title = "OPÇÕES";
        int titleWidth = g.getFontMetrics().stringWidth(title);
        g.drawString(title, centerX - titleWidth / 2, startY);
        
        // Opções
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        int optionSpacing = 80;
        
        // Resolução
        if (selectedOption == 0) {
            g.setColor(new Color(100, 200, 255));
            g.setFont(new Font("Arial", Font.BOLD, 36));
        } else {
            g.setColor(Color.GRAY);
            g.setFont(new Font("Arial", Font.PLAIN, 32));
        }
        
        String resolutionLabel = "Resolução: " + GameSettings.RESOLUTION_NAMES[settings.getCurrentResolutionIndex()];
        int resWidth = g.getFontMetrics().stringWidth(resolutionLabel);
        int resY = startY + 100;
        
        if (selectedOption == 0) {
            g.drawString(">", centerX - resWidth / 2 - 40, resY);
        }
        g.drawString(resolutionLabel, centerX - resWidth / 2, resY);
        
        // Instruções de resolução
        g.setColor(new Color(150, 150, 150));
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        String resInstructions = "Use ↑↓ ou W/S para mudar resolução";
        int resInstWidth = g.getFontMetrics().stringWidth(resInstructions);
        g.drawString(resInstructions, centerX - resInstWidth / 2, resY + 40);
        
        // Voltar
        if (selectedOption == 1) {
            g.setColor(new Color(100, 200, 255));
            g.setFont(new Font("Arial", Font.BOLD, 36));
        } else {
            g.setColor(Color.GRAY);
            g.setFont(new Font("Arial", Font.PLAIN, 32));
        }
        
        String backOption = options[1];
        int backWidth = g.getFontMetrics().stringWidth(backOption);
        int backY = startY + 100 + optionSpacing;
        
        if (selectedOption == 1) {
            g.drawString(">", centerX - backWidth / 2 - 40, backY);
        }
        g.drawString(backOption, centerX - backWidth / 2, backY);
        
        // Instruções gerais
        g.setColor(new Color(150, 150, 150));
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        String instructions = "Enter para confirmar, ESC para voltar";
        int instWidth = g.getFontMetrics().stringWidth(instructions);
        g.drawString(instructions, centerX - instWidth / 2, settings.getHeight() - 30);
    }
}

