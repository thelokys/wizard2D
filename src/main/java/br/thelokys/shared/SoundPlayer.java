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
                    // Se não conseguiu carregar, apenas retorna silenciosamente
                    // Isso permite que o jogo continue funcionando sem áudio
                    return;
                }
            }
            
            // Para o som se já estiver tocando e reinicia
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.setFramePosition(0);
            clip.start();
            
        } catch (IllegalStateException e) {
            // Clip pode ter sido fechado ou não está mais disponível
            // Remove do cache e tenta novamente na próxima vez
            clipCache.remove(path);
        } catch (Exception e) {
            // Erro ao tocar som - apenas loga e continua
            // Não quebra o jogo se o áudio falhar
            System.err.println("Erro ao tocar som: " + path + " - " + e.getMessage());
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