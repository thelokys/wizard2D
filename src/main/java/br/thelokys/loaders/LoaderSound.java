package br.thelokys.loaders;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class LoaderSound {
  public static AudioInputStream from(String path) {
    try {
      var stream = ResourceHelper.getResourceStream(path);
      if (stream == null) {
        throw new RuntimeException("Audio resource not found: " + path);
      }

      var audio = AudioSystem.getAudioInputStream(stream);
      return audio;
    } catch (Exception e) {
      System.err.println("Não foi possível carregar o audio: " + path);
      e.printStackTrace();
      return null;
    }
  }
}