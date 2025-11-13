package br.thelokys.shared;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import br.thelokys.loaders.LoaderSound;

public class SoundPlayer {
  public static void play(String path) {
    try {
      var sfx = LoaderSound.from(path);
      Clip clip = AudioSystem.getClip();
      clip.open(sfx);
      clip.start();
    } catch (Exception e) {
      System.err.println("Erro ao tocar som: " + e.getMessage());
    }
  }
}
