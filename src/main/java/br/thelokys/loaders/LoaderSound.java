package br.thelokys.loaders;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
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
      
      // Tenta obter um formato suportado pelo sistema
      AudioFormat format = audioStream.getFormat();
      AudioFormat targetFormat = getSupportedFormat(format);
      
      // Se o formato precisa ser convertido, converte
      if (!format.matches(targetFormat)) {
        audioStream = AudioSystem.getAudioInputStream(targetFormat, audioStream);
      }
      
      // Tenta criar o clip com o formato suportado
      DataLine.Info info = new DataLine.Info(Clip.class, audioStream.getFormat());
      if (!AudioSystem.isLineSupported(info)) {
        System.err.println("Formato de áudio não suportado: " + path);
        System.err.println("Formato: " + audioStream.getFormat());
        stream.close();
        return null;
      }
      
      Clip clip = (Clip) AudioSystem.getLine(info);
      clip.open(audioStream);

      stream.close();
      return clip;

    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
      System.err.println("Não foi possível carregar o audio: " + path);
      System.err.println("Erro: " + e.getMessage());
      // Não imprime stack trace completo para não poluir o console
      return null;
    } catch (IllegalArgumentException e) {
      // Erro comum no WSL quando o formato não é suportado
      System.err.println("Formato de áudio não suportado no sistema: " + path);
      System.err.println("Erro: " + e.getMessage());
      return null;
    } catch (Exception e) {
      System.err.println("Erro inesperado ao carregar audio: " + path);
      System.err.println("Erro: " + e.getMessage());
      return null;
    }
  }
  
  /**
   * Tenta obter um formato de áudio suportado pelo sistema
   */
  private static AudioFormat getSupportedFormat(AudioFormat format) {
    // Se já é PCM_SIGNED com sample rate conhecido e válido, tenta usar
    if (format.getEncoding() == AudioFormat.Encoding.PCM_SIGNED) {
      float sampleRate = format.getSampleRate();
      // Se o sample rate é válido e conhecido, mantém o formato
      if (sampleRate > 0 && sampleRate != AudioSystem.NOT_SPECIFIED && 
          !Float.isNaN(sampleRate) && !Float.isInfinite(sampleRate)) {
        return format;
      }
    }
    
    // Tenta converter para formatos padrão suportados
    // Lista de formatos para tentar, do mais comum ao menos comum
    AudioFormat[] targetFormats = {
      // PCM_SIGNED, 44100 Hz, 16 bit, mono, little-endian
      new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100.0f, 16, 1, 2, 44100.0f, false),
      // PCM_SIGNED, 22050 Hz, 16 bit, mono, little-endian
      new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 22050.0f, 16, 1, 2, 22050.0f, false),
      // PCM_SIGNED, 44100 Hz, 16 bit, stereo, little-endian
      new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100.0f, 16, 2, 4, 44100.0f, false),
      // PCM_SIGNED, 22050 Hz, 16 bit, stereo, little-endian
      new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 22050.0f, 16, 2, 4, 22050.0f, false)
    };
    
    // Tenta cada formato até encontrar um suportado
    for (AudioFormat targetFormat : targetFormats) {
      if (AudioSystem.isConversionSupported(targetFormat, format)) {
        return targetFormat;
      }
    }
    
    // Se nenhum formato de conversão é suportado, retorna o formato original
    // O erro será tratado no catch
    return format;
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