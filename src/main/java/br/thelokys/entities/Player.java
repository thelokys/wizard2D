package br.thelokys.entities;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.thelokys.constants.Tiles;
import br.thelokys.core.DirectionState;
import br.thelokys.entities.stats.Exp;
import br.thelokys.entities.stats.Health;
import br.thelokys.loaders.LoaderImage;
import br.thelokys.shared.Animator;
import br.thelokys.shared.CollisionBox2D;
import br.thelokys.shared.GameContext;
import br.thelokys.shared.SoundPlayer;
import br.thelokys.shared.SpriteSheet;
import br.thelokys.skills.BasicAttack;
import br.thelokys.skills.Skill;

public class Player extends CollisionBox2D {
  private final int speed;
  private final int runSpeed;
  private final int dashSpeed;
  private final int dashDuration;
  private final int dashCooldown;
  
  private int dashTimer;
  private int dashCooldownTimer;
  private boolean isDashing;

  private String currentAnimation;
  private String currentDirection;
  private Animator animator;

  private List<Skill> skills;
  private Health health;
  private Exp exp;

  public Player(int x, int y) {
    super(x, y, Tiles.MAX_TILE_SIZE, Tiles.MAX_TILE_SIZE);
    this.speed = 4;
    this.runSpeed = 6;
    this.dashSpeed = 12;
    this.dashDuration = 10; // frames
    this.dashCooldown = 60; // frames
    
    this.dashTimer = 0;
    this.dashCooldownTimer = 0;
    this.isDashing = false;
    
    this.currentAnimation = "idle";
    this.currentDirection = "right";

    // Carrega spritesheets de animação
    var sprites = Map.of(
        "idle-right", LoaderImage.from("/entities/player/sprites/idle-right.png"),
        "idle-left", LoaderImage.from("/entities/player/sprites/idle-left.png"),
        "walk-right", LoaderImage.from("/entities/player/sprites/walk-right.png"),
        "walk-left", LoaderImage.from("/entities/player/sprites/walk-left.png"),
        "run-right", LoaderImage.from("/entities/player/sprites/run-right.png"),
        "run-left", LoaderImage.from("/entities/player/sprites/run-left.png"),
        "dash-right", LoaderImage.from("/entities/player/sprites/dash-right.png"),
        "dash-left", LoaderImage.from("/entities/player/sprites/dash-left.png"));

    var spriteSheet = new SpriteSheet(sprites, Tiles.MAX_TILE_SIZE, Tiles.MAX_TILE_SIZE);
    this.animator = new Animator(spriteSheet, 8); // 8 frames por frame de animação
    this.animator.playAnimation("idle-right");

    this.skills = new ArrayList<>();
    this.skills.add(new BasicAttack(this));
    this.health = new Health(100, 100);
    this.exp = new Exp(1000);
  }

  public void update(double deltaTime) {
    // Se o player está morto, não atualiza movimento ou skills
    if (health.isDead()) {
      return;
    }

    // Atualiza cooldown do dash
    if (dashCooldownTimer > 0) {
      dashCooldownTimer--;
    }
    
    // Atualiza timer do dash
    if (isDashing) {
      dashTimer--;
      if (dashTimer <= 0) {
        isDashing = false;
        dashCooldownTimer = dashCooldown;
      }
    }

    move(deltaTime);
    updateAnimation();

    for (var skill : skills) {
      skill.updateCooldownTicks(deltaTime);
      skill.updateActive(deltaTime);
    }
    
    // Atualiza animação
    animator.update();
  }

  private void updateAnimation() {
    var directionInput = GameContext.get().getDirectionInput();
    boolean isMoving = !directionInput.heldDirections.isEmpty();
    boolean isRunning = directionInput.isShiftPressed && isMoving && !isDashing;
    
    String newAnimation;
    String newDirection = currentDirection;
    
    // Determina direção
    if (isMoving) {
      for (var dir : directionInput.heldDirections) {
        if (dir == DirectionState.LEFT) {
          newDirection = "left";
          break;
        } else if (dir == DirectionState.RIGHT) {
          newDirection = "right";
          break;
        }
      }
    }
    
    // Determina animação baseado no estado
    if (isDashing) {
      newAnimation = "dash";
    } else if (isRunning) {
      newAnimation = "run";
    } else if (isMoving) {
      newAnimation = "walk";
    } else {
      newAnimation = "idle";
    }
    
    String animationName = newAnimation + "-" + newDirection;
    String currentAnimationName = currentAnimation + "-" + currentDirection;
    
    // Só muda animação se for diferente
    if (!animationName.equals(currentAnimationName)) {
      currentAnimation = newAnimation;
      currentDirection = newDirection;
      animator.playAnimation(animationName);
    }
  }

  public void render(Graphics2D g) {
    var camera = GameContext.get().getCamera();

    var screenX = (this.x - camera.getX());
    var screenY = (this.y - camera.getY());

    var frame = animator.getCurrentFrame();
    if (frame != null) {
      g.drawImage(frame, screenX, screenY, width, height, null);
    }

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

      // Verifica se pode fazer dash
      if (directionInput.isShiftPressed && dashCooldownTimer <= 0 && !isDashing) {
        isDashing = true;
        dashTimer = dashDuration;
      }

      // Calcula velocidade baseado no estado
      int currentSpeed;
      if (isDashing) {
        currentSpeed = dashSpeed;
      } else if (directionInput.isShiftPressed) {
        currentSpeed = runSpeed;
      } else {
        currentSpeed = speed;
      }

      this.x += Math.round(deltaX * currentSpeed);
      this.y += Math.round(deltaY * currentSpeed);
    }
  }

  public DirectionState getDirection() {
    return currentDirection.equals("right") ? DirectionState.RIGHT : DirectionState.LEFT;
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
