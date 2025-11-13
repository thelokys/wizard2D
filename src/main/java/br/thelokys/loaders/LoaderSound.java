package br.thelokys.loaders;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LoaderSound {

  public static Clip loadClip(String path) {
    try {
      InputStream stream = ResourceHelper.getResourceStream(path);
      if (stream == null) {
        System.err.println("Audio resource not found: " + path);
        return null;
      }

      // Lê todos os bytes do stream
      byte[] audioData = stream.readAllBytes();
      ByteArrayInputStream byteStream = new ByteArrayInputStream(audioData);

      AudioInputStream audioStream = AudioSystem.getAudioInputStream(byteStream);
      Clip clip = AudioSystem.getClip();
      clip.open(audioStream);

      stream.close();
      return clip;

    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
      System.err.println("Não foi possível carregar o audio: " + path);
      e.printStackTrace();
      return null;
    } catch (Exception e) {
      System.err.println("Erro inesperado ao carregar audio: " + path);
      e.printStackTrace();
      return null;
    }
  }

  public static AudioInputStream from(String path) {
    try {
      InputStream stream = ResourceHelper.getResourceStream(path);
      if (stream == null) {
        System.err.println("Audio resource not found: " + path);
        return null;
      }

      // Lê todos os bytes e cria um novo stream
      byte[] audioData = stream.readAllBytes();
      ByteArrayInputStream byteStream = new ByteArrayInputStream(audioData);

      AudioInputStream audioStream = AudioSystem.getAudioInputStream(byteStream);
      stream.close();
      return audioStream;

    } catch (Exception e) {
      System.err.println("Não foi possível carregar o audio: " + path);
      e.printStackTrace();
      return null;
    }
  }
}