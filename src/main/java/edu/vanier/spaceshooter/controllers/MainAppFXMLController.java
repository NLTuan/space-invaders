package edu.vanier.spaceshooter.controllers;

import edu.vanier.geometry.Vector;
import edu.vanier.spaceshooter.models.Sprite;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class of the MainApp UI.
 */
public class MainAppFXMLController {

    private final static Logger logger = LoggerFactory.getLogger(MainAppFXMLController.class);
    @FXML
    private Pane animationPanel;
    private Sprite spaceShip;
    private Scene mainScene;
    AnimationTimer gameLoop;
    
    private long lastNanoTime = System.nanoTime();
    private double elapsedTime;
    private ArrayList<KeyCode> input;

    private double enemySpeed = 10;
    private double playerBulletSpeed = 20;
    private double enemyBulletSpeed = 20;
    
    @FXML
    public void initialize() {
        logger.info("Initializing MainAppController...");
        spaceShip = new Sprite(300, 750, 40, 40, "player", Color.BLUE, 10);
        animationPanel.setPrefSize(600, 800);
        animationPanel.getChildren().add(spaceShip);
        
        input = new ArrayList<>();
    }

    public void setupGameWorld() {
        initGameLoop();
        setupKeyPressHandlers();
        generateInvaders();
    }

    private void initGameLoop() {
        // Create the game loop.
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update(now);
            }
        };
        gameLoop.start();
    }

    /**
     * Sets up the key press event handler for the main scene.
     * <p>
     * This handler listens for specific key presses and executes corresponding
     * actions:
     * <ul>
     * <li>Pressing 'A' moves the spaceship to the left.</li>
     * <li>Pressing 'D' moves the spaceship to the right.</li>
     * <li>Pressing the SPACE key triggers the spaceship to shoot.</li>
     * </ul>
     * </p>
     */
    private void setupKeyPressHandlers() {
        // e the key event containing information about the key pressed.
        mainScene.setOnKeyPressed(e -> {
            KeyCode code = e.getCode();
            if (!input.contains(code)) {
                input.add(code);
            }
        });
        
        mainScene.setOnKeyReleased((KeyEvent e) -> {
            KeyCode code = e.getCode();
            input.remove(code);
        });
        

    }

    private void generateInvaders() {
        for (int i = 0; i < 10; i++) {
            Sprite invader = new Sprite(
                    90 + i * 100,
                    150, 30, 30, "enemy",
                    Color.RED,
                    enemySpeed
            );
            animationPanel.getChildren().add(invader);
        }
    }

    /**
     * Retrieves a list of all sprites currently in the animation panel.
     * <p>
     * This method iterates through the children of the animation panel and
     * collects those that are instances of {@link Sprite} into a list.
     * </p>
     *
     * @return A list of {@link Sprite} objects found in the animation panel.
     */
    private List<Sprite> getSprites() {
        List<Sprite> spriteList = new ArrayList<>();
        for (Node n : animationPanel.getChildren()) {
            if (n instanceof Sprite sprite) {
                // We should add to the list any node that is a Sprite object.
                spriteList.add(sprite);
            }
        }
        return spriteList;
    }

    /**
     * Updates the game state for each frame.
     * <p>
     * This method increments the elapsed time and processes each sprite based
     * on its type. It handles the movement and collision detection for enemy
     * bullets and player bullets, as well as the shooting behavior for enemies.
     * Dead sprites are removed from the animation panel, and the elapsed time
     * is reset after a certain threshold.
     * </p>
     */
    private void update(long now) {
        elapsedTime = (now - lastNanoTime) / 10000000;
        // Actions to be performed during each frame of the animation.


        Vector direction = new Vector(
            boolToDouble(input.contains(KeyCode.D)) - boolToDouble(input.contains(KeyCode.A)),
            boolToDouble(input.contains(KeyCode.S)) - boolToDouble(input.contains(KeyCode.W))
        ).normalized();
        
        spaceShip.setDirection(direction);
        
        getSprites().forEach(this::processSprite);
        removeDeadSprites();
        lastNanoTime = System.nanoTime();
    }

    private void processSprite(Sprite sprite) {
        sprite.setInternalClock(sprite.getInternalClock() + elapsedTime);
        switch (sprite.getType()) {
            case "player" -> 
                handlePlayer(sprite);
            case "enemybullet" ->
                handleEnemyBullet(sprite);
            case "playerbullet" ->
                handlePlayerBullet(sprite);
            case "enemy" ->
                handleEnemyFiring(sprite);
        }
    }

    private void handlePlayer(Sprite sprite){
        sprite.move(elapsedTime);

    }
    private void handleEnemyBullet(Sprite sprite) {
        sprite.setDirection(new Vector(0, 1));
        sprite.move(elapsedTime);
        // Check for collision with the spaceship
        if (sprite.getBoundsInParent().intersects(spaceShip.getBoundsInParent())) {
            spaceShip.setDead(true);
            sprite.setDead(true);
        }
    }

    private void handlePlayerBullet(Sprite sprite) {
        sprite.setDirection(new Vector(0, -1));
        sprite.move(elapsedTime);
        for (Sprite enemy : getSprites()) {
            if (enemy.getType().equals("enemy")) {
                // Check for collision with an enemy
                if (sprite.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                    enemy.setDead(true);
                    sprite.setDead(true);
                }
            }
        }
    }

    private void handleEnemyFiring(Sprite sprite) {
        if (sprite.getInternalClock() > sprite.CLOCK_RESET_VALUE) {
            if (Math.random() < 0.3) {
                shoot(sprite);
            }
            sprite.setInternalClock(0);
        }
    }

    /**
     * Removes all dead sprites from the animation panel.
     * <p>
     * This method iterates through the children of the animation panel and
     * removes any sprite that is marked as dead. It utilizes a lambda
     * expression to filter out the dead sprites efficiently.
     * </p>
     */
    private void removeDeadSprites() {
        animationPanel.getChildren().removeIf(n -> {
            Sprite sprite = (Sprite) n;
            return sprite.isDead();
        });
    }

    /**
     * Creates and adds a bullet sprite fired by the specified entity.
     * <p>
     * The firing entity can be either an enemy or the spaceship. A bullet is
     * created at the position of the firing entity with a slight offset to the
     * right. The bullet's dimensions are set, and it is given a type based on
     * the firing entity's type.
     * </p>
     *
     * @param firingEntity The entity that is firing the bullet, which can be
     * either an enemy or the spaceship.
     */
    private void shoot(Sprite firingEntity) {
        // The firing entity can be either an enemy or the sapceship.
        Sprite bullet = new Sprite(
                (int) firingEntity.getTranslateX() + 20,
                (int) firingEntity.getTranslateY(),
                5, 20,
                firingEntity.getType() + "bullet", Color.BLACK, enemyBulletSpeed);
        animationPanel.getChildren().add(bullet);
    }

    public void setScene(Scene scene) {
        mainScene = scene;
    }

    public void stopAnimation() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }
    
    private double boolToDouble(boolean bool){
        return (bool)? 1.0: 0;
    }
}
