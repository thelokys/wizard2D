package br.thelokys.world;

import br.thelokys.constants.Screen;
import br.thelokys.constants.Tiles;
import br.thelokys.shared.GameContext;
import br.thelokys.shared.GameObject;

public class Camera extends GameObject {

  private float shakeIntensity = 0;
  private float shakeDuration = 0;
  private float currentShakeTime = 0;
  private int baseX, baseY; // Posição base sem shake

  /**
   * Atribuir valores da camera baseado na tela
   */
  public Camera() {
    super(0, 0, Screen.MAX_SCREEN_WIDTH, Screen.MAX_SCREEN_HEIGHT);
    this.baseX = 0;
    this.baseY = 0;
  }

  /*
   * Atribui as coordenadas baseado em um target
   */
  public void follow(GameObject target) {
    var terrain = GameContext.get().getTerrain();

    int targetX = target.getX() - this.width / 2;
    int targetY = target.getY() - this.height / 2;

    this.baseX += (targetX - this.baseX);
    this.baseY += (targetY - this.baseY);

    this.baseX = Math.max(0, Math.min(this.baseX, terrain.getWidth() * Tiles.MAX_TILE_SIZE - this.width));
    this.baseY = Math.max(0, Math.min(this.baseY, terrain.getHeight() * Tiles.MAX_TILE_SIZE - this.height));

    // Aplica o shake se estiver ativo
    applyShake();
  }

  /**
   * Ativa o efeito de camera shake
   * 
   * @param intensity Intensidade do shake (em pixels)
   * @param duration  Duração do shake (em segundos)
   */
  public void shake(float intensity, float duration) {
    this.shakeIntensity = intensity;
    this.shakeDuration = duration;
    this.currentShakeTime = 0;
  }

  private void applyShake() {
    if (currentShakeTime < shakeDuration) {
      // Calcula o progresso do shake
      float progress = currentShakeTime / shakeDuration;
      float currentIntensity = shakeIntensity * (1 - progress);

      // Gera offsets aleatórios
      float offsetX = (float) ((Math.random() - 0.5) * 2 * currentIntensity);
      float offsetY = (float) ((Math.random() - 0.5) * 2 * currentIntensity);

      // Aplica shake sobre a posição base
      this.x = baseX + (int) offsetX;
      this.y = baseY + (int) offsetY;

      currentShakeTime += 1 / 60f;
    } else {
      // Quando o shake termina, volta para a posição base
      this.x = baseX;
      this.y = baseY;
    }
  }

  public void update(double deltaTime) {
    // Atualiza o timer do shake
    if (currentShakeTime < shakeDuration) {
      currentShakeTime += deltaTime;
    }
  }

  public int getPixelX() {
    return this.x * Tiles.MAX_TILE_SIZE;
  }

  public int getPixelY() {
    return this.y * Tiles.MAX_TILE_SIZE;
  }

  public int getPixelWidth() {
    return this.width * Tiles.MAX_TILE_SIZE;
  }

  public int getPixelHeight() {
    return this.height * Tiles.MAX_TILE_SIZE;
  }

  /**
   * @return Se está tremendo a camera
   */
  public boolean isShaking() {
    return currentShakeTime < shakeDuration;
  }

}
