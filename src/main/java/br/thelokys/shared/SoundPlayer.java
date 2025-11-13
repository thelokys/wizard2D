package br.thelokys.shared;

import br.thelokys.loaders.LoaderSound;
import javax.sound.sampled.Clip;
import java.util.HashMap;
import java.util.Map;

public class SoundPlayer {
    private static final Map<String, Clip> clipCache = new HashMap<>();
    
    public static void play(String path) {
        try {
            // Verifica se já está no cache
            Clip clip = clipCache.get(path);
            
            if (clip == null) {
                // Carrega o clip
                clip = LoaderSound.loadClip(path);
                if (clip != null) {
                    clipCache.put(path, clip);
                } else {
                    System.err.println("Erro ao carregar som: " + path);
                    return;
                }
            }
            
            // Para o som se já estiver tocando e reinicia
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.setFramePosition(0);
            clip.start();
            
        } catch (Exception e) {
            System.err.println("Erro ao tocar som: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Método para tocar som em loop
    public static void playLoop(String path) {
        try {
            Clip clip = clipCache.get(path);
            if (clip == null) {
                clip = LoaderSound.loadClip(path);
                if (clip != null) {
                    clipCache.put(path, clip);
                } else {
                    return;
                }
            }
            
            if (!clip.isRunning()) {
                clip.setFramePosition(0);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            
        } catch (Exception e) {
            System.err.println("Erro ao tocar som em loop: " + e.getMessage());
        }
    }
    
    // Método para parar som
    public static void stop(String path) {
        try {
            Clip clip = clipCache.get(path);
            if (clip != null && clip.isRunning()) {
                clip.stop();
            }
        } catch (Exception e) {
            System.err.println("Erro ao parar som: " + e.getMessage());
        }
    }
    
    // Limpa o cache de sons
    public static void cleanup() {
        for (Clip clip : clipCache.values()) {
            if (clip != null) {
                clip.stop();
                clip.close();
            }
        }
        clipCache.clear();
    }
}