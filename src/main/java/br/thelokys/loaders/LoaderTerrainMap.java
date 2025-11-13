package br.thelokys.loaders;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LoaderTerrainMap {
  public static char[][] fromFile(String filePath) {
    try {
      var file = ResourceHelper.getResourceStream(filePath);
      if (file == null) {
        throw new IllegalArgumentException("Arquivo não encontrado: " + filePath);
      }

      var reader = new BufferedReader(new InputStreamReader(file));
      var lines = reader.lines().toList();
      int rows = lines.size();
      int cols = lines.get(0).length();

      var map = new char[rows][cols];

      for (int i = 0; i < rows; i++) {
        map[i] = lines.get(i).toCharArray();
      }

      reader.close();
      return map;
    } catch (Exception e) {
      throw new RuntimeException("Erro ao ler o mapa: " + filePath, e);
    }
  }
}