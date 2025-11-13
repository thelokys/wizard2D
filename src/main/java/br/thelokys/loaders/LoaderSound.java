package br.thelokys.loaders;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class LoaderSound {
  public static AudioInputStream from(String path) {

    try {
      var audio = AudioSystem.getAudioInputStream(LoaderSound.class.getResourceAsStream(path));
      return audio;
    } catch (Exception e) {
      System.err.println("Não foi possível carregar a audio: " + path);
      e.printStackTrace();
      return null;
    }
  }
}
