package br.thelokys.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.thelokys.constants.Tiles;
import br.thelokys.core.DirectionState;
import br.thelokys.entities.stats.Exp;
import br.thelokys.entities.stats.Health;
import br.thelokys.loaders.LoaderImage;
import br.thelokys.shared.CollisionBox2D;
import br.thelokys.shared.GameContext;
import br.thelokys.shared.SoundPlayer;
import br.thelokys.skills.BasicAttack;
import br.thelokys.skills.Skill;

public class Player extends CollisionBox2D {
  private final int speed;

  private String currentSprite;
  private Map<String, BufferedImage> sprites;

  private List<Skill> skills;
  private Health health;
  private Exp exp;

  public Player(int x, int y) {
    super(x, y, Tiles.MAX_TILE_SIZE, Tiles.MAX_TILE_SIZE);
    this.speed = 4;
    this.currentSprite = "idle-right";
    this.sprites = Map.of(
        "idle-right", LoaderImage.from("/entities/player/sprites/idle-right.png"),
        "idle-right-hurt", LoaderImage.from("/entities/player/sprites/idle-right-hurt.png"),
        "idle-left", LoaderImage.from("/entities/player/sprites/idle-left.png"),
        "idle-left-hurt", LoaderImage.from("/entities/player/sprites/idle-left-hurt.png"));

    this.skills = new ArrayList<>();
    this.skills.add(new BasicAttack(this));
    this.health = new Health(10, 100);
    this.exp = new Exp(1000);
  }

  public void update(double deltaTime) {
    move(deltaTime);

    for (var skill : skills) {
      skill.updateCooldownTicks(deltaTime);
      skill.updateActive(deltaTime);
    }
  }

  private void updateDirectionSprite(float x, float y) {
    if (x < 0) {
      this.currentSprite = "idle-left";
    } else if (x > 0) {
      this.currentSprite = "idle-right";
    }
  }

  public void render(Graphics2D g) {
    var camera = GameContext.get().getCamera();

    var screenX = (this.x - camera.getX());
    var screenY = (this.y - camera.getY());

    g.drawImage(sprites.get(currentSprite), screenX, screenY, width, height, null);

    for (var skill : skills) {
      skill.render(g);
    }
  }

  private void move(double deltaTime) {
    var directionInput = GameContext.get().getDirectionInput();

    float deltaX = 0;
    float deltaY = 0;

    var directions = directionInput.heldDirections;

    for (var dir : directions) {
      switch (dir) {
        case UP -> deltaY -= 1;
        case DOWN -> deltaY += 1;
        case LEFT -> deltaX -= 1;
        case RIGHT -> deltaX += 1;
      }
    }

    if (deltaX != 0 || deltaY != 0) {
      var distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
      deltaX /= distance;
      deltaY /= distance;

      updateDirectionSprite(deltaX, deltaY);

      this.x += Math.round(deltaX * speed);
      this.y += Math.round(deltaY * speed);

    }
  }

  public DirectionState getDirection() {
    return switch (currentSprite) {
      case "idle-right" -> DirectionState.RIGHT;
      case "idle-left" -> DirectionState.LEFT;
      default -> DirectionState.RIGHT;
    };
  }

  public void takeDamage(int amount) {
    health.takeDamage(amount);
    playPlayerReceivingDamage();

    activateCameraShake();
  }

  private void playPlayerReceivingDamage() {
    SoundPlayer.play("/entities/player/sfx/mage-damage-received.wav");
  }

  private void activateCameraShake() {
    var camera = GameContext.get().getCamera();

    float shakeIntensity = 8.0f;
    float shakeDuration = 0.3f;

    camera.shake(shakeIntensity, shakeDuration);
  }

  public Health getHealth() {
    return health;
  }

  public Exp getExp() {
    return exp;
  }

  public List<Skill> getSkills() {
    return skills;
  }

}
