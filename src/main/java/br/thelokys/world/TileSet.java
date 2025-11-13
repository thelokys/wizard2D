package br.thelokys.world;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import br.thelokys.constants.Tiles;
import br.thelokys.loaders.LoaderImage;

public class TileSet {

  private Map<Character, BufferedImage> tiles;
  private final String ASSETS_PATH = "/terrain/sprites/";

  public TileSet() {
    this.tiles = new HashMap<>();
    this.loadTiles();
  }

  private void loadTiles() {
    try {
      tiles.put(Tiles.GRASS_SYMBOL, LoaderImage.from(ASSETS_PATH + "grass.png"));
      tiles.put(Tiles.TREE_SYMBOL, LoaderImage.from(ASSETS_PATH + "tree.png"));
      tiles.put(Tiles.WATER_SYMBOL, LoaderImage.from(ASSETS_PATH + "water.png"));
      tiles.put(Tiles.PATH_SYMBOL, LoaderImage.from(ASSETS_PATH + "path.png"));

      tiles.put(Tiles.PLAYER_SYMBOL, LoaderImage.from(ASSETS_PATH + "grass.png"));
    } catch (Exception e) {
      System.out.println("Não foi possível carregar os tiles do mapa");
      e.printStackTrace();
    }
  }

  public BufferedImage getTile(char tileType) {
    return tiles.get(tileType);
  }
}