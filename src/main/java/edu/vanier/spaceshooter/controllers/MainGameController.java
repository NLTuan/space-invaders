package edu.vanier.spaceshooter.controllers;

import edu.vanier.geometry.Vector;
import edu.vanier.helpers.AudioPlayer;
import edu.vanier.manager.GameManager;
import edu.vanier.spaceshooter.SpaceShooterApp;
import edu.vanier.spaceshooter.models.*;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class of the MainApp UI.
 * Controls the flow of the game and also the UI components
 * Handles animation and updates UI components accordingly
 * 
 * @author Le Tuan Huy Nguyen
 */
public class MainGameController {

    private final static Logger logger = LoggerFactory.getLogger(MainGameController.class);
    @FXML
    private Pane animationPanel;
    private Player spaceShip;
    private Scene mainScene;
    AnimationTimer gameLoop;
    
    private long lastNanoTime = System.nanoTime();
    private double elapsedTime;
    private double totalElapsedTime;
    
    private ArrayList<KeyCode> input;
    private ArrayList<KeyCode> prevInput;

    private double playerSpeed = 400;
    private double smallInvaderSpeed = 50;
    private double mediumInvaderSpeed = 100;
    private double bigInvaderSpeed = 20;

    private double playerBulletSpeed = 1000;
    private double enemyBulletSpeed = 300;

    private int windowWidth = 600;
    private int windowHeight = 900;
    
    private GameManager gameManager;

    @FXML
    private ImageView bgImage;
    
    @FXML
    private Text stageText;
    
    @FXML
    private Text livesText;
    
    @FXML
    private Text scoreText;
    
    @FXML
    private Text tipText;
    
    @FXML
    private HBox tipHBox;
    
    @FXML
    private VBox gameOverVBox;
    
    @FXML
    private Text gameOverScoreText;
    
    @FXML 
    private Button replayButton;
           
    @FXML
    private VBox gameCompleteVBox;
    
    @FXML
    private Text gameCompleteScoreText;
    
    @FXML
    private Button continueButton;
    
    @FXML
    private Button replayButtonComplete;
    
    @FXML
    private Button gameOverQuitButton;
    
    @FXML
    private Button winQuitButton;
    
    private Stage primaryStage;
    
    
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

    public MainGameController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
    
    @FXML
    public void initialize() {
        
        // Create Player
        spaceShip = new Player(
                SpaceShooterApp.screenWidth/2, 
                (int)(SpaceShooterApp.screenHeight * 0.75), 20, 20, "player", "playerShip1.png", playerSpeed, playerBulletSpeed);
        animationPanel.getChildren().add(spaceShip);
        
        input = new ArrayList<>();
        
        // Create game state
        gameManager = new GameManager(this, animationPanel);
        livesText.setText("Lives: " + spaceShip.getLives());
        
        // Bind the components to allow resizing of the screen
        tipHBox.prefWidthProperty().bind(animationPanel.widthProperty());
        bgImage.fitWidthProperty().bind(animationPanel.widthProperty());
        bgImage.fitHeightProperty().bind(animationPanel.heightProperty());
        
        gameOverVBox.prefWidthProperty().bind(animationPanel.widthProperty());
        gameOverVBox.prefHeightProperty().bind(animationPanel.heightProperty());
        
        gameCompleteVBox.prefWidthProperty().bind(animationPanel.widthProperty());
        gameCompleteVBox.prefHeightProperty().bind(animationPanel.heightProperty());
    }

    /**
     * Initializes the animation loop, key press handlers, and add behavior to buttons
     * @author Le Tuan Huy Nguyen
     */
    public void setupGameWorld() {
        initGameLoop();
        setupKeyPressHandlers();
        
        replayButton.setOnAction((event) -> {
            gameLoop.start();
            replayButton.setDisable(true);
            gameOverQuitButton.setDisable(true);

            gameOverVBox.setOpacity(0);
            spaceShip = new Player(
                SpaceShooterApp.screenWidth/2, 
                (int)(SpaceShooterApp.screenHeight * 0.75), 20, 20, "player", "playerShip1.png", playerSpeed, playerBulletSpeed);
            animationPanel.getChildren().add(spaceShip);
            gameManager.reset();
            livesText.setText("Lives: " + spaceShip.getLives());
            scoreText.setText("Score: " + gameManager.getScore());
        });
        
        replayButtonComplete.setOnAction((event) -> {
            gameLoop.start();
            continueButton.setDisable(true);
            replayButtonComplete.setDisable(true);
            winQuitButton.setDisable(true);
            gameCompleteVBox.setOpacity(0);
            spaceShip = new Player(
                SpaceShooterApp.screenWidth/2, 
                (int)(SpaceShooterApp.screenHeight * 0.75), 20, 20, "player", "playerShip1.png", playerSpeed, playerBulletSpeed);
            animationPanel.getChildren().add(spaceShip);
            gameManager.reset();
            livesText.setText("Lives: " + spaceShip.getLives());
            scoreText.setText("Score: " + gameManager.getScore());
        });
        
        continueButton.setOnAction((event) -> {
            gameLoop.start();
            continueButton.setDisable(true);
            replayButtonComplete.setDisable(true);
            winQuitButton.setDisable(true);
            gameCompleteVBox.setOpacity(0);
            animationPanel.getChildren().add(spaceShip);
            gameManager.setGameOver(false);
            gameManager.levelUp();
            spaceShip.levelUp();
            stageText.setText("Stage " + gameManager.getLevel());
            livesText.setText("Lives: " + spaceShip.getLives());
            scoreText.setText("Score: " + gameManager.getScore());
            bgImage.setImage(new Image(getClass().getResource("/bgimages/dueling_stars.png").toExternalForm()));

            
            AudioPlayer levelUpTune = new AudioPlayer("/sfx/levelUp.wav");
            levelUpTune.play();
            
            gameManager.spawnInvaders();
        });
        
        gameOverQuitButton.setOnAction(((event) -> {
            primaryStage.close();
        }));
        
        winQuitButton.setOnAction(((event) -> {
            primaryStage.close();
        }));
    }

    /**
     * Starts an AnimationTimer loop
     */
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
     * <li>Pressing 'W' moves the spaceship upwards.</li>
     * <li>Pressing 'S' moves the spaceship downwards.</li>
     * <li>Pressing the SPACE key triggers the spaceship to shoot.</li>
     * <li>Pressing 'E' switches the spaceship's weapon.</li>
     * <li>Pressing 'L' activates crazy mode.</li>
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
     * Updates the state of the explosions to know when they have to be removed
     */
    private void updateExplosions() {
        for (Node n : animationPanel.getChildren()) {
            if (n instanceof Explosion) {
                ((Explosion) n).decreaseTime(elapsedTime);
            }
        }
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
        
        // fetch the time for smooth animation;
        lastNanoTime = System.nanoTime();
        
        // Actions to be performed during each frame of the animation.
        if(input.contains(KeyCode.E) && !prevInput.contains(KeyCode.E)){
            spaceShip.updateStage();
            AudioPlayer weaponChange = new AudioPlayer("/sfx/changeWeapon.wav");
            weaponChange.play();
        }
        if(input.contains(KeyCode.L) && !prevInput.contains(KeyCode.L)){
            spaceShip.toggleCrazyMode();
            livesText.setText("Lives: " + spaceShip.getLives());
        }
        
        // Update player direction
        Vector direction = new Vector(
            boolToDouble(input.contains(KeyCode.D)) - boolToDouble(input.contains(KeyCode.A)),
            boolToDouble(input.contains(KeyCode.S)) - boolToDouble(input.contains(KeyCode.W))
        ).normalized();
        spaceShip.setDirection(direction);
        
        // Process the sprites
        getSprites().forEach(this::processSprite);
        
        // Update the state of the explosions
        updateExplosions();

        // Clear dead sprites
        removeDeadSprites();
        
        // Update constants in case the screen has been resized
        SpaceShooterApp.screenWidth = (int)animationPanel.getWidth();
        SpaceShooterApp.screenHeight = (int)animationPanel.getHeight();
        
        // Check if enemies are cleared and level up should happen
        boolean levelUp = true;
        for (Node sprite: animationPanel.getChildren()){
            if(sprite instanceof Invader){
                levelUp = false;
                break;
            }
        }
        
        // Perform leveling up 
        if(levelUp && !gameManager.isGameOver()){
            
            if (gameManager.getLevel() == 3){
                // Check if player completed level 3, which prompts a game complete screen
                gameManager.setGameOver(true);
            }

            if (!gameManager.isGameOver()){
                // Leveling up
                gameManager.levelUp();
                spaceShip.levelUp();
                stageText.setText("Stage: " + gameManager.getLevel());
                AudioPlayer levelUpTune = new AudioPlayer("/sfx/levelUp.wav");
                levelUpTune.play();
                
                switch (gameManager.getLevel() % 3) {
                    case 1:
                        bgImage.setImage(new Image(getClass().getResource("/bgimages/dueling_stars.png").toExternalForm()));
                        spaceShip.setImage(new Image(getClass().getResource("/spriteimages/playerShip1.png").toExternalForm()));
                        break;
                    case 2:
                        bgImage.setImage(new Image(getClass().getResource("/bgimages/Starset.png").toExternalForm()));
                        spaceShip.setImage(new Image(getClass().getResource("/spriteimages/playerShip2.png").toExternalForm()));
                        break;
                    case 0:
                        bgImage.setImage(new Image(getClass().getResource("/bgimages/Starsetcolorful.png").toExternalForm()));
                        spaceShip.setImage(new Image(getClass().getResource("/spriteimages/playerShip3.png").toExternalForm()));
                        break;
                }
                if (!gameManager.isGameOver()){
                    gameManager.spawnInvaders();
                }
            }
        }
        if (gameManager.isGameOver() && spaceShip.getLives() > 0){
            // Prompt game complete screen
            gameComplete();
            gameLoop.stop();
        }
        else if (gameManager.isGameOver()){
            // Prompt game over screen when player dies
            gameOver();
            gameLoop.stop();
        }
        
        
        // save last frame's inputs to allow checking if a button was clicked in the last frame
        prevInput = new ArrayList<>(input);
    }

    /**
     * Handle each sprite's behavior on each frame. This method takes care of
     * updating collision, movement, shooting, and internal clocks.
     * @param sprite the sprite to process
     */
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
    
    /**
     * Handle the player's spaceship, it checks for collision with enemy bullets,
     * and enemies. It also handles removing player lives and raising gameOver
     * whenever a player dies.
     * @param sprite 
     */
    private void handlePlayer(Sprite sprite){
        Player playerSprite = (Player) sprite;
        playerSprite.setInternalShootingClock(playerSprite.getInternalShootingClock() + elapsedTime);
        sprite.move(elapsedTime);
        
        for (Sprite enemy : getSprites()) {
            if (enemy instanceof Invader) {
                // Check for collision with an enemy
                if (sprite.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                    ((Invader) enemy).setHitpoints(0);

                    // Handle player after collision with enemy
                    if (playerSprite.getLives() > 1){
                        // Player doesn't die
                        playerSprite.setLives(playerSprite.getLives() - 1);
                        livesText.setText("Lives: " + playerSprite.getLives());
                        AudioPlayer damage = new AudioPlayer("/sfx/damage.mp3");
                        damage.play();
                    }
                    else{
                        // Player dies
                        playerSprite.setLives(0);
                        livesText.setText("Lives: " + playerSprite.getLives());
                        playerSprite.setDead(true);
                        gameManager.setGameOver(true); // Terminate game
                    }
                    // Handle enemy after collision
                    if (((Invader) enemy).getHitpoints() == 0){
                        enemy.setDead(true);
                        
                        // Increase the score after killing an enemy by colliding into them
                        ((Invader) enemy).getHpBar().setDead(true);
                        if(enemy instanceof SmallInvader1){
                            gameManager.increaseScore(200);
                        }
                        else if(enemy instanceof MediumInvader1){
                            gameManager.increaseScore(400);
                        }
                        else if(enemy instanceof BigInvader){
                            gameManager.increaseScore(3000);
                        }
                        scoreText.setText("Score: " + gameManager.getScore());
                        
                        AudioPlayer enemyDeath = new AudioPlayer("/sfx/enemyDeath.wav");
                        enemyDeath.setVolume(0.5);
                        enemyDeath.play();
                        
                        Explosion explosion = new Explosion(enemy, "/animations/explosion.gif", 0.7);
                        animationPanel.getChildren().add(explosion);
                    }

                }
            }
        }
    }
    /**
     * Process enemy bullets movement and collision with the player. It removes
     * lives or raises gameOver when the player is hit.
     * @param sprite the bullet Sprite to process
     */
    private void handleEnemyBullet(Sprite sprite) {
        if(sprite instanceof BigBullet){
            ((BigBullet)sprite).updateDirection();
        }
        
        sprite.move(elapsedTime);
        // Check for collision with the spaceship
        if (sprite.getBoundsInParent().intersects(spaceShip.getBoundsInParent())) {
            Explosion explosionBullet = new Explosion(sprite, sprite.getFitWidth() * 2,
                    sprite.getFitWidth() * 2,
                    "/animations/explosionBullet.gif", 0.6);
            animationPanel.getChildren().add(explosionBullet); 

            if (spaceShip.getLives() > 1){
                // Player doesn't die
                sprite.setDead(true);
                spaceShip.setLives(spaceShip.getLives() - 1);
                livesText.setText("Lives: " + spaceShip.getLives());
                AudioPlayer damage = new AudioPlayer("/sfx/damage.mp3");
                damage.play();
            }
            else{
                // Player dies
                spaceShip.setLives(spaceShip.getLives() - 1);
                livesText.setText("Lives: " + spaceShip.getLives());
                spaceShip.setDead(true);
                sprite.setDead(true);
                
                Explosion explosion = new Explosion(spaceShip, "/animations/explosion.gif", 0.7);
                animationPanel.getChildren().add(explosion);
                
                gameManager.setGameOver(true); // Terminate game
            }
                
        }
    }

    /**
     * Manage the player's bullet's collision with the enemy, either taking away
     * their hp or killing them once it collides.
     * @param sprite the player's bullet sprite
     */
    private void handlePlayerBullet(Sprite sprite) {
        sprite.move(elapsedTime);
        for (Sprite enemy : getSprites()) {
            if (enemy instanceof Invader) {
                // Check for collision with an enemy
                if (sprite.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                    // Remove HP
                    ((Invader) enemy).setHitpoints(((Invader) enemy).getHitpoints() - 1);
                    
                    AudioPlayer enemyHit = new AudioPlayer("/sfx/enemyHit.mp3");
                    enemyHit.setVolume(0.5);
                    enemyHit.play();
                    
                    Explosion explosionBullet = new Explosion(sprite, sprite.getFitHeight() * 4,
                            sprite.getFitHeight() * 4,
                            "/animations/explosionBullet.gif", 0.6);
                    animationPanel.getChildren().add(explosionBullet);
                    
                    if (((Invader) enemy).getHitpoints() == 0){
                        // Enemy is dead after collision
                        enemy.setDead(true);
                        ((Invader) enemy).getHpBar().setDead(true);
                        if(enemy instanceof SmallInvader1){
                            gameManager.increaseScore(200);
                        }
                        else if(enemy instanceof MediumInvader1){
                            gameManager.increaseScore(400);
                        }
                        else if(enemy instanceof BigInvader){
                            gameManager.increaseScore(3000);
                        }
                        scoreText.setText("Score: " + gameManager.getScore());
                        
                        Explosion explosionEnemy = new Explosion(enemy, "/animations/explosion.gif", 0.7);
                        animationPanel.getChildren().add(explosionEnemy);
                        
                        AudioPlayer enemyDeath = new AudioPlayer("/sfx/enemyDeath.wav");
                        enemyDeath.setVolume(0.5);
                        enemyDeath.play();
                    }
                    sprite.setDead(true);
                }
            }
        }
    }

    /** 
     * Determines if the enemy fires in a certain frame
     * The enemy must check if their shot is still on cooldown, and once it's
     * down, they have a 30% chance of shooting. Shoot or not, they will still
     * have to undergo cooldown again.
     * @param sprite the sprite that performs the shooting
     */
    private void handleEnemyFiring(Sprite sprite) {
        Invader invader = (Invader) sprite;
        if ((totalElapsedTime + invader.getDeltaClock()) % invader.getFiringCooldown() < 0.01) {
            if (Math.random() < 0.3) {
                shoot(invader);
            }
        }
    }
    
    /**
     * Handles player shooting depending on if the player is on cooldown. The
     * player should SPACE to shoot. After a player shoots, they are in cooldown
     * which must run up before the player can shoot again.
     * Also decreases the score by 10 on each shot for penalty.
     * @param sprite 
     */
    private void handlePlayerFiring(Sprite sprite) {
        Player player = (Player) sprite;
        if (player.getInternalShootingClock() >= player.getFiringCooldown()) {
            if (input.contains(KeyCode.SPACE)) {
                shoot(player);
                player.setInternalShootingClock(0);
                gameManager.increaseScore(-10);
                scoreText.setText("Score: " + gameManager.getScore());
            }
        }
    }

    /**
     * Handles enemy movement and HP bar. Determines whether the enemy is in a 
     * paused state or moving state and moves it accordingly
     * @param sprite the enemy to be updated
     */
    private void handleEnemy(Sprite sprite) {
        sprite.move(elapsedTime);
        Invader invader = (Invader) sprite;
        invader.updateHP();
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
     * Removes all dead sprites, HpBars, and Explosions from the animation panel.
     * <p>
     * This method iterates through the children of the animation panel and
     * removes any sprite that is marked as dead. It utilizes a lambda
     * expression to filter out the dead sprites efficiently.
     * </p>
     */
    private void removeDeadSprites() {
        animationPanel.getChildren().removeIf(n -> {
            if (n instanceof Sprite) {
                Sprite sprite = (Sprite) n;
                return sprite.isDead();
            }
            else if (n instanceof HpBar){
                HpBar hpBar = (HpBar) n;
                return hpBar.isDead();
            }
            else if (n instanceof Explosion){
                return ((Explosion) n).getTimeLeft() <= 0;
            }
            return false;
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

    public void stopAnimation() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }
    
    /**
     * Helper method for converting boolean to a double. It helps for determining
     * direction from input
     * @param bool the boolean to convert
     * @return 1.0 if true, 0.0 if false
     */
    private static double boolToDouble(boolean bool){
        return (bool)? 1.0: 0;
    }
    
    /**
     * Check if a sprite is out of bounds (the playing window) and sets them 
     * to dead=true for removal. This method only works for bullets objects 
     * (which are Sprites with a type String that contains "bullet"). It does not
     * work with other sprites, they shouldn't be deleted if they go off screen.
     * @param sprite the sprite that needs to be checked, has to be a bullet
     * @return true if the sprite is out of bounds, false if not
     */
    private boolean outOfBounds(Sprite sprite){
        double tolerance = 20;
        if ((sprite.getTranslateX() + sprite.getFitWidth() < -tolerance
                || (sprite.getTranslateX() > animationPanel.getWidth()+ tolerance)
                || (sprite.getTranslateY() + sprite.getFitHeight()< -tolerance)
                || (sprite.getTranslateY() > animationPanel.getHeight()+ tolerance))
                && sprite.getType().contains("bullet")
                ){
            return true;
        }
        return false;

    }
    
        /**
     * Raises the game over screen and prompts the player to replay or quit
     */
    private void gameOver() {
        AudioPlayer gameOverTune = new AudioPlayer("/sfx/gameOver.wav");
        gameOverTune.play();
        
        gameOverVBox.setViewOrder(-1);
        gameCompleteVBox.setViewOrder(0);
        input.removeAll(input);
        gameOverVBox.setOpacity(1);
        replayButton.setDisable(false);
        gameOverQuitButton.setDisable(false);
        gameOverScoreText.setText("Final score: " + gameManager.getScore());
        
        // Clear out the game elements
        animationPanel.getChildren().removeIf((t) -> {
            return (t instanceof Sprite || t instanceof HpBar || t instanceof Explosion);
        });
    }
    
    /**
     * After completing level 3, this method is called to raise a victory screen,
     * where the player can continue to play harder levels, restart, or quit.
     */
    private void gameComplete(){
        AudioPlayer victory = new AudioPlayer("/sfx/victory.mp3");
        victory.play();
        
        gameCompleteVBox.setViewOrder(-1);
        gameOverVBox.setViewOrder(0);
        input.removeAll(input);
        gameCompleteVBox.setOpacity(1);
        replayButtonComplete.setDisable(false);
        winQuitButton.setDisable(false);
        continueButton.setDisable(false);
        gameCompleteScoreText.setText("Score: " + gameManager.getScore());
        
        // Clear out the game elements
        animationPanel.getChildren().removeIf((t) -> {
            return (t instanceof Sprite || t instanceof HpBar || t instanceof Explosion);
        });
    }

    public Player getSpaceShip() {
        return spaceShip;
    }

    public void setSpaceShip(Player spaceShip) {
        this.spaceShip = spaceShip;
    }

    public double getPlayerSpeed() {
        return playerSpeed;
    }

    public void setPlayerSpeed(double playerSpeed) {
        this.playerSpeed = playerSpeed;
    }

    public double getMediumInvaderSpeed() {
        return mediumInvaderSpeed;
    }

    public void setMediumInvaderSpeed(double mediumInvaderSpeed) {
        this.mediumInvaderSpeed = mediumInvaderSpeed;
    }

    public double getBigInvaderSpeed() {
        return bigInvaderSpeed;
    }

    public void setBigInvaderSpeed(double bigInvaderSpeed) {
        this.bigInvaderSpeed = bigInvaderSpeed;
    }

    public double getPlayerBulletSpeed() {
        return playerBulletSpeed;
    }

    public void setPlayerBulletSpeed(double playerBulletSpeed) {
        this.playerBulletSpeed = playerBulletSpeed;
    }

    public double getEnemyBulletSpeed() {
        return enemyBulletSpeed;
    }

    public void setEnemyBulletSpeed(double enemyBulletSpeed) {
        this.enemyBulletSpeed = enemyBulletSpeed;
    }

    public double getSmallInvaderSpeed() {
        return smallInvaderSpeed;
    }

    public void setSmallInvaderSpeed(double smallInvaderSpeed) {
        this.smallInvaderSpeed = smallInvaderSpeed;
    }

    public Scene getMainScene() {
        return mainScene;
    }

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }
}