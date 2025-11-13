package br.thelokys.shared;

import java.util.ArrayList;
import java.util.List;

import br.thelokys.constants.Tiles;
import br.thelokys.core.DirectionInput;
import br.thelokys.drops.GemExp;
import br.thelokys.entities.Player;
import br.thelokys.loaders.LoaderTerrainMap;
import br.thelokys.systems.EnemySystem;
import br.thelokys.systems.GemDropsSystem;
import br.thelokys.systems.SpawnerSystem;
import br.thelokys.ui.Hud;
import br.thelokys.world.Camera;
import br.thelokys.world.Terrain;

public class GameContext {

  private static GameContext instance;

  private GameContext() {
  }

  public static GameContext get() {
    if (instance == null) {
      instance = new GameContext();
      instance.initializeObjects();
    }
    return instance;
  }

  private Terrain terrain;
  private Camera camera;
  private DirectionInput directionInput;
  private Player player;
  private EnemySystem enemiesSystem;
  private GemDropsSystem gemDropsSystem;
  private Hud hud;
  private SpawnerSystem spawnerSystem;

  public void initializeObjects() {
    var mapConfig = LoaderTerrainMap.fromFile("/terrain/level2.txt");
    this.camera = new Camera();
    this.terrain = new Terrain(mapConfig);
    this.directionInput = new DirectionInput();

    var coords = this.terrain.findSpawnOf(Tiles.PLAYER_SYMBOL);
    this.player = new Player(coords[0] * Tiles.MAX_TILE_SIZE, coords[1] * Tiles.MAX_TILE_SIZE);

    this.enemiesSystem = new EnemySystem();
    this.spawnerSystem = new SpawnerSystem();

    this.gemDropsSystem = new GemDropsSystem();
    this.hud = new Hud();

  }

  public Terrain getTerrain() {
    return terrain;
  }

  public Camera getCamera() {
    return camera;
  }

  public DirectionInput getDirectionInput() {
    return directionInput;
  }

  public Player getPlayer() {
    return player;
  }

  public EnemySystem getEnemiesSystem() {
    return enemiesSystem;
  }

  public GemDropsSystem getGemDropsSystem() {
    return gemDropsSystem;
  }

  public Hud getHud() {
    return hud;
  }

  public SpawnerSystem getSpawnerSystem() {
    return spawnerSystem;
  }

  /**
   * Reseta o jogo para o estado inicial
   */
  public void resetGame() {
    // Reseta os sistemas
    this.enemiesSystem = new EnemySystem();
    this.spawnerSystem = new SpawnerSystem();
    this.gemDropsSystem = new GemDropsSystem();
    
    // Reseta a câmera
    this.camera = new Camera();
    
    // Recria o player na posição inicial com HP máximo
    var coords = this.terrain.findSpawnOf(Tiles.PLAYER_SYMBOL);
    this.player = new Player(coords[0] * Tiles.MAX_TILE_SIZE, coords[1] * Tiles.MAX_TILE_SIZE);
    
    // Reseta o HUD
    this.hud = new Hud();
  }

}
