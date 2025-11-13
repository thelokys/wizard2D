package br.thelokys.systems;

import br.thelokys.shared.GameContext;

public class SpawnerSystem {

  private int currentWave;
  private int timerNextWave;
  private int currentTimerNextWave;
  private int maxEnemiesPerWave;
  private int enemiesPerSpawn;

  private EnemySystem enemySystem;

  public SpawnerSystem() {
    currentWave = 0;
    timerNextWave = 60 * 2;
    currentTimerNextWave = 0;

    maxEnemiesPerWave = 10;
    enemiesPerSpawn = 3;
    enemySystem = GameContext.get().getEnemiesSystem();
  }

  public void update() {
    if (isReadyToSpawn()) {
      for (int i = 0; i < enemiesPerSpawn; i++) {
        enemySystem.spawnEnemy();
      }
      this.resetCoolDownNextWave();
      this.nextWave();
    }

    this.decreaseCooldownNextWave();
  }

  public void decreaseCooldownNextWave() {
    this.decreaseCooldownNextWave(1);
  }

  public void decreaseCooldownNextWave(int amount) {
    this.currentTimerNextWave -= amount;

    if (this.currentTimerNextWave < 0) {
      this.currentTimerNextWave = 0;
    }
  }

  public void resetCoolDownNextWave() {
    this.currentTimerNextWave = timerNextWave;
  }

  public Boolean isReadyToSpawn() {
    return enemySystem.getEnemies().size() < maxEnemiesPerWave && currentTimerNextWave == 0;
  }

  public void nextWave() {
    this.currentWave++;
  }

  public int getCurrentWave() {
    return currentWave;
  }

  public int getEnemiesPerSpawn() {
    return enemiesPerSpawn;
  }

  public void setEnemiesPerSpawn(int enemiesPerSpawn) {
    this.enemiesPerSpawn = enemiesPerSpawn;
  }
}
