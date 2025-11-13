package br.thelokys.systems;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import br.thelokys.constants.Tiles;
import br.thelokys.entities.Bat;
import br.thelokys.entities.Enemy;
import br.thelokys.entities.Player;
import br.thelokys.shared.GameContext;
import br.thelokys.world.Terrain;

public class EnemySystem {
  private List<Enemy> enemies;

  public EnemySystem() {
    this.enemies = new ArrayList<>();
  }

  public void spawnEnemy() {
    var player = GameContext.get().getPlayer();
    var terrain = GameContext.get().getTerrain();

    int minDistanceFromPlayer = 200; // Distância mínima do jogador em pixels
    int minDistanceBetweenEnemies = 50; // Distância mínima entre inimigos
    int maxAttempts = 20;

    int posX, posY;
    int attempts = 0;
    boolean validPosition = false;

    do {
      // Gera posições aleatórias baseadas no tamanho real do terreno
      posX = (int) (Math.random() * terrain.getWidth() * Tiles.MAX_TILE_SIZE);
      posY = (int) (Math.random() * terrain.getHeight() * Tiles.MAX_TILE_SIZE);
      attempts++;

      // Verifica se a posição é válida
      validPosition = !isTooCloseToPlayer(posX, posY, player, minDistanceFromPlayer) &&
          !isTooCloseToOtherEnemies(posX, posY, minDistanceBetweenEnemies) &&
          isWalkableTile(posX, posY);

    } while (!validPosition && attempts < maxAttempts);

    // Se não encontrou posição válida, usa fallback
    if (!validPosition) {
      int[] fallbackPos = findFallbackSpawnPosition(player, terrain);
      posX = fallbackPos[0];
      posY = fallbackPos[1];
    }

    var newBat = new Bat(posX, posY);
    this.enemies.add(newBat);

    System.out.println(String.format("Spawn enemy at (%s,%s) - Attempts: %s", posX, posY, attempts));
  }

  private boolean isTooCloseToOtherEnemies(int x, int y, int minDistance) {
    for (Enemy enemy : enemies) {
      double distance = Math.sqrt(Math.pow(x - enemy.getX(), 2) + Math.pow(y - enemy.getY(), 2));
      if (distance < minDistance) {
        return true;
      }
    }
    return false;
  }

  private boolean isTooCloseToPlayer(int x, int y, Player player, int minDistance) {
    double distance = Math.sqrt(Math.pow(x - player.getX(), 2) + Math.pow(y - player.getY(), 2));
    return distance < minDistance;
  }

  private boolean isWalkableTile(int worldX, int worldY) {
    var terrain = GameContext.get().getTerrain();
    int tileX = worldX / Tiles.MAX_TILE_SIZE;
    int tileY = worldY / Tiles.MAX_TILE_SIZE;
    if (tileX < 0 || tileX >= terrain.getWidth() || tileY < 0 || tileY >= terrain.getHeight()) {
      return false;
    }
    char tileChar = terrain.terrainMap[tileY][tileX];
    return tileChar == Tiles.GRASS_SYMBOL || tileChar == Tiles.PATH_SYMBOL || tileChar == '.';
  }

  private int[] findFallbackSpawnPosition(Player player, Terrain terrain) {
    int mapWidth = terrain.getWidth() * Tiles.MAX_TILE_SIZE;
    int mapHeight = terrain.getHeight() * Tiles.MAX_TILE_SIZE;

    // Tenta posições nos cantos do mapa
    int[][] corners = {
        { 50, 50 }, // Canto superior esquerdo
        { mapWidth - 50, 50 }, // Canto superior direito
        { 50, mapHeight - 50 }, // Canto inferior esquerdo
        { mapWidth - 50, mapHeight - 50 } // Canto inferior direito
    };

    // Encontra o canto mais distante do jogador
    int[] bestCorner = corners[0];
    double maxDistance = 0;

    for (int[] corner : corners) {
      double distance = Math.sqrt(Math.pow(corner[0] - player.getX(), 2) + Math.pow(corner[1] - player.getY(), 2));
      if (distance > maxDistance && isWalkableTile(corner[0], corner[1])) {
        maxDistance = distance;
        bestCorner = corner;
      }
    }

    return bestCorner;
  }

  public void update() {
    var iterator = enemies.iterator();
    while (iterator.hasNext()) {
      var current = iterator.next();

      current.update();

      if (current.getHealth().isDead()) {
        GameContext.get().getGemDropsSystem().spawnGemExpDrop(current);
        iterator.remove();
      }
    }
  }

  public void render(Graphics2D g) {
    enemies.forEach(enemy -> enemy.render(g));
  }

  public List<Enemy> getEnemies() {
    return enemies;
  }

}
