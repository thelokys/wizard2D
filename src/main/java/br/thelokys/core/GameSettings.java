package br.thelokys.core;

public class GameSettings {
    private static GameSettings instance;
    
    // Resoluções disponíveis
    public static final int[][] RESOLUTIONS = {
        {960, 720},   // 20x15 tiles (padrão)
        {1280, 960},  // ~26x20 tiles
        {1600, 1200}, // ~33x25 tiles
        {1920, 1080}  // 40x22.5 tiles (16:9)
    };
    
    public static final String[] RESOLUTION_NAMES = {
        "960x720 (Padrão)",
        "1280x960",
        "1600x1200",
        "1920x1080 (Full HD)"
    };
    
    private int currentResolutionIndex = 0;
    
    private GameSettings() {
    }
    
    public static GameSettings get() {
        if (instance == null) {
            instance = new GameSettings();
        }
        return instance;
    }
    
    public int getWidth() {
        return RESOLUTIONS[currentResolutionIndex][0];
    }
    
    public int getHeight() {
        return RESOLUTIONS[currentResolutionIndex][1];
    }
    
    public int getCurrentResolutionIndex() {
        return currentResolutionIndex;
    }
    
    public void setResolutionIndex(int index) {
        if (index >= 0 && index < RESOLUTIONS.length) {
            this.currentResolutionIndex = index;
        }
    }
    
    public void nextResolution() {
        currentResolutionIndex = (currentResolutionIndex + 1) % RESOLUTIONS.length;
    }
    
    public void previousResolution() {
        currentResolutionIndex = (currentResolutionIndex - 1 + RESOLUTIONS.length) % RESOLUTIONS.length;
    }
}

