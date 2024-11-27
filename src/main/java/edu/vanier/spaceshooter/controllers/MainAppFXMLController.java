package edu.vanier.spaceshooter.controllers;

import edu.vanier.geometry.Vector;
import edu.vanier.spaceshooter.SpaceShooterApp;
import edu.vanier.spaceshooter.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private Player spaceShip;
    private Scene mainScene;
    AnimationTimer gameLoop;
    
    private long lastNanoTime = System.nanoTime();
    private double elapsedTime;
    private double totalElapsedTime;
    
    private ArrayList<KeyCode> input;

    private double playerSpeed = 300;
    private double smallInvaderSpeed = 30;
    private double mediumInvaderSpeed = 100;

    private double playerBulletSpeed = 1000;
    private double enemyBulletSpeed = 300;

    private int windowWidth = 600;
    private int windowHeight = 800;
    
    private boolean weaponSwitchPressed = false;
    
    private int enemiesPerRow = 5;
    private int numOfRows = 5;

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    @FXML
    public void initialize() {
        logger.info("Initializing MainAppController...");
        spaceShip = new Player(
                SpaceShooterApp.screenWidth/2, 
                (int)(SpaceShooterApp.screenHeight * 0.75), 40, 40, "player", Color.BLUE, playerSpeed, playerBulletSpeed);
        animationPanel.setPrefSize(SpaceShooterApp.screenWidth, SpaceShooterApp.screenWidth);
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
            if (input.contains(code)){
                weaponSwitchPressed = false;
            }
            
            input.remove(code);

        });
    }

    private void generateInvaders() {
        
        int spacingX = SpaceShooterApp.screenWidth/enemiesPerRow;
        int spacingY = (int)(SpaceShooterApp.screenHeight * 0.5 / numOfRows);
        Vector topLeft = new Vector(spacingX, spacingY);
//        for (int i = 0; i < enemiesPerRow - 1; i++) {
//            Sprite invader = new SmallInvader(
//                    (int)topLeft.getX() + i * spacingX,
//                    (int)topLeft.getY(), 30, 30, "enemy",
//                    Color.RED,
//                    smallInvaderSpeed,
//                    enemyBulletSpeed
//            );
//            animationPanel.getChildren().add(invader);
//        }

//        for (int i = 0; i < enemiesPerRow; i++) {
//            Sprite invader = new MediumInvader(
//                    (int)topLeft.getX() + i * spacingX,
//                    (int)topLeft.getY() + spacingY, 30, 30, "enemy",
//                    Color.BLUE,
//                    mediumInvaderSpeed,
//                    enemyBulletSpeed
//            );
//            animationPanel.getChildren().add(invader);
//        }
        Sprite bigInvader = new BigInvader(
                SpaceShooterApp.screenWidth/2 - 35,
                35, 70, 70, "enemy",
                Color.ORANGE,
                mediumInvaderSpeed,
                enemyBulletSpeed,
                spaceShip,
                0.02
            );
        animationPanel.getChildren().add(bigInvader);

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
        elapsedTime = (now - lastNanoTime) / 1E9;
        totalElapsedTime += elapsedTime;
        // Actions to be performed during each frame of the animation.

        if(input.contains(KeyCode.E) && !weaponSwitchPressed){
            spaceShip.updateStage();
            weaponSwitchPressed = true;
        }
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
        switch (sprite.getType()) {
            case "player":
                handlePlayer(sprite);
                handlePlayerFiring(sprite);
                break;
            case "enemybullet" :
                handleEnemyBullet(sprite);
                break;
            case "playerbullet":
                handlePlayerBullet(sprite);
                break;
            case "enemy":
                handleEnemy(sprite);
                handleEnemyFiring(sprite);
                break;
        }
        
        if (outOfBounds(sprite)){
            sprite.setDead(true);
        }
    }

    private void handlePlayer(Sprite sprite){
        Player playerSprite = (Player) sprite;
        playerSprite.setInternalShootingClock(playerSprite.getInternalShootingClock() + elapsedTime);
        sprite.move(elapsedTime);
    }
    private void handleEnemyBullet(Sprite sprite) {
        if(sprite instanceof BigBullet){
            ((BigBullet)sprite).updateDirection();
        }
        
        sprite.move(elapsedTime);
        // Check for collision with the spaceship
        if (sprite.getBoundsInParent().intersects(spaceShip.getBoundsInParent())) {
            spaceShip.setDead(true);
            sprite.setDead(true);
        }
    }

    private void handlePlayerBullet(Sprite sprite) {
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
        Invader invader = (Invader) sprite;
        if (totalElapsedTime % invader.getFiringCooldown() < 0.01) {
            if (Math.random() < 0.3) {
                shoot(invader);
            }
        }
    }
    
    private void handlePlayerFiring(Sprite sprite) {
        Player player = (Player) sprite;
        if (player.getInternalShootingClock() >= player.getFiringCooldown()) {
            if (input.contains(KeyCode.SPACE)) {
                shoot((FiringSprite) sprite);
                player.setInternalShootingClock(0);
            }
        }
    }

    private void handleEnemy(Sprite sprite) {
        sprite.move(elapsedTime);
        Invader invader = (Invader) sprite;
        if ((totalElapsedTime + invader.getDeltaClock()) 
                % (invader.getMovementCooldown() + invader.getPauseCooldown())
                < invader.getMovementCooldown()
                && !invader.isMovementUpdated()
        ) {
            invader.updateMovement();
            invader.setMovementUpdated(true);
            invader.setPauseUpdated(false);
        }
        else if ((totalElapsedTime + invader.getDeltaClock()) 
                % (invader.getMovementCooldown() + invader.getPauseCooldown())
                > invader.getMovementCooldown()
            && !invader.isPauseUpdated()
        ){
            invader.setDirection(new Vector(0, 0));
            invader.setPauseUpdated(true);
            invader.setMovementUpdated(false);
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
    private void shoot(FiringSprite firingEntity) {
        // The firing entity can be either an enemy or the spaceship.
        ArrayList<Sprite> bullets = firingEntity.shoot();
        for (Sprite bullet: bullets){
            animationPanel.getChildren().add(bullet);
        }
    }

    public void setScene(Scene scene) {
        mainScene = scene;
    }

    public void stopAnimation() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }
    
    public static double boolToDouble(boolean bool){
        return (bool)? 1.0: 0;
    }
    
    private boolean outOfBounds(Sprite sprite){
        double tolerance = 50;
        if ((sprite.getTranslateX() + sprite.getWidth() < -tolerance)
                || (sprite.getTranslateX() > SpaceShooterApp.screenWidth + tolerance)
                || (sprite.getTranslateY() + sprite.getWidth() < -tolerance)
                || (sprite.getTranslateY() > SpaceShooterApp.screenHeight + tolerance)
                ){
            return true;
        }
        return false;

    }

    public Player getSpaceShip() {
        return spaceShip;
    }

    public void setSpaceShip(Player spaceShip) {
        this.spaceShip = spaceShip;
    }
}