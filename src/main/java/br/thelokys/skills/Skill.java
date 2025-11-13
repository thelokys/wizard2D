package br.thelokys.skills;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import br.thelokys.shared.GameObject;

/**
 * Classe abstrata que representa a skill que o jogador seleciona ao upar de
 * nível, terá regras basicas de cooldown e nomes que aparecem na hud, mas o
 * comportamento da skill é feito por quem herda dela, o comportamento da skill
 * não é dessa classe.
 */
public abstract class Skill extends GameObject {
  private String name;
  private String description;
  private BufferedImage icon;
  private int cooldown;
  private int currentCooldown;
  private GameObject owner;

  /**
   * 
   * @param name        Nome da skill
   * @param description Descrição da skill que mostra na seleção
   * @param icon        Icone da skill que mostra na hud e na seleção de skill
   */
  public Skill(GameObject owner, String name, String description, BufferedImage icon, int cooldown) {
    this.owner = owner;
    this.name = name;
    this.description = description;
    this.icon = icon;

    this.cooldown = cooldown * 60;
    this.currentCooldown = 0;
  }

  /**
   * atualiza o cooldown da skill.
   */
  public void updateCooldownTicks(double deltaTime) {
    this.decreaseCooldown();

    if (isReady()) {
      this.onActiveSkill(deltaTime);
      this.resetCooldown();
    }
  }

  /**
   * Atualiza a skill quando a skill estiver ativa @see#updateCooldownTicks(double
   * deltaTime)
   * 
   * @param deltaTime Valor para normalizar os valores caso necessário.
   */
  public abstract void onActiveSkill(double deltaTime);

  public abstract void updateActive(double deltaTime);

  public abstract void render(Graphics2D g);

  /**
   * @return O dano causado pela skill
   */
  public abstract int getDamage();

  /**
   * Renderiza o icon da hud da skill, mostra o cooldown da skill também.
   * 
   * @param g Graphics2D para desenhar na tela.
   */
  public void renderIcon(Graphics2D g, int posX, int posY, int size) {
    if (icon != null) {
      var originalComposite = g.getComposite();

      g.drawImage(icon, posX, posY, size, size, null);
      g.setComposite(originalComposite);

      if (currentCooldown > 0) {
        drawCoolDownCircle(g, posX, posY, size);
      }
    }
  }

  /**
   * @return Se a skill esta ativa baseado no cooldown.
   */
  public Boolean isReady() {
    return currentCooldown <= 0;
  }

  /**
   * @return A quantidade que falta para
   */
  public double getCooldownProgress() {
    return currentCooldown > 0 ? currentCooldown / cooldown : 0;
  }

  /**
   * Reseta o cooldown da skill para o mesmo no inicio.
   */
  public void resetCooldown() {
    currentCooldown = cooldown;
  }

  /**
   * Diminui o cooldown da skill em 1
   */
  public void decreaseCooldown() {
    this.decreaseCooldown(1);
  }

  /**
   * Diminui o cooldown da skill baseado
   * 
   * @param amount Quantidade para diminuir
   */
  public void decreaseCooldown(int amount) {
    this.currentCooldown -= amount;

    if (this.currentCooldown < 0) {
      this.currentCooldown = 0;
    }
  }

  /**
   * @return O GameObject representando o dono da skill
   */
  public GameObject getOwner() {
    return owner;
  }

  /**
   * @return Nome da skill
   */
  public String getName() {
    return name;
  }

  /**
   * @return Descrição da skill
   */
  public String getDescription() {
    return description;
  }

  /**
   * Desenha o contador de cooldown encima do ícone da skill.
   * 
   * @param g Graphics2D para desenhar.
   */
  private void drawCoolDownCircle(Graphics2D g, int x, int y, int size) {
    if (currentCooldown > 0) {
      float progress = currentCooldown / (float) cooldown;

      g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
      int angle = (int) (360 * progress);
      g.fillArc(x, y, size, size, 90, angle);

      g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
      g.setColor(Color.WHITE);
      g.drawOval(x, y, size, size);
    }
  }

}
