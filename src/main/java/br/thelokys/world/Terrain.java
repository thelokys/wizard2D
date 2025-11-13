package br.thelokys.world;

import java.awt.Graphics2D;

import br.thelokys.constants.Tiles;
import br.thelokys.shared.GameContext;
import br.thelokys.shared.GameObject;

public class Terrain extends GameObject {

  public TileSet tileSet;
  public char[][] terrainMap;

  public Terrain(char[][] terrainMap) {
    this.terrainMap = terrainMap;
    this.width = terrainMap[0].length;
    this.height = terrainMap.length;
    this.tileSet = new TileSet();
  }

  public int[] findSpawnOf(char target) {
    for (var y = 0; y < terrainMap.length; y++) {
      for (var x = 0; x < terrainMap[0].length; x++) {
        if (terrainMap[y][x] == target) {
          return new int[] { x, y };
        }
      }
    }
    return new int[] { 0, 0 };
  }

  public void render(Graphics2D g) {
    var camera = GameContext.get().getCamera();

    // normaliza os limites da camera
    int startCol = Math.max(0, camera.getX() / Tiles.MAX_TILE_SIZE);
    int startRow = Math.max(0, camera.getY() / Tiles.MAX_TILE_SIZE);

    int endCol = Math.min(this.width, (camera.getX() + camera.getWidth()) / Tiles.MAX_TILE_SIZE + 1);
    int endRow = Math.min(this.height, (camera.getY() + camera.getHeight()) / Tiles.MAX_TILE_SIZE + 1);

    // renderiza apenas dentro dos limites e renderiza apenas o que camera visualiza
    for (int y = startRow; y < endRow; y++) {
      for (int x = startCol; x < endCol; x++) {
        var tileChar = this.terrainMap[y][x];
        var tileImage = this.tileSet.getTile(tileChar);

        g.drawImage(
            tileImage,
            x * Tiles.MAX_TILE_SIZE - camera.getX(),
            y * Tiles.MAX_TILE_SIZE - camera.getY(),
            Tiles.MAX_TILE_SIZE,
            Tiles.MAX_TILE_SIZE,
            null);
      }
    }
  }

}
