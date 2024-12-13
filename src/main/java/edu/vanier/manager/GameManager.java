package edu.vanier.manager;

import edu.vanier.geometry.Vector;
import edu.vanier.spaceshooter.controllers.MainGameController;
import edu.vanier.spaceshooter.models.BigInvader;
import edu.vanier.spaceshooter.models.Invader;
import edu.vanier.spaceshooter.models.MediumInvader1;
import edu.vanier.spaceshooter.models.MediumInvader2;
import edu.vanier.spaceshooter.models.SmallInvader1;
import edu.vanier.spaceshooter.models.SmallInvader2;
import javafx.scene.layout.Pane;

/**
 * Game Manager for the space shooter game. Keeps track of the levels as well as
 * the state of the game if it is over or not. Also has the ability to spawn in 
 * enemies depending on which level it is on.
 * @author Le Tuan Huy Nguyen
 */
public class GameManager {
    private int level;
    
    private MainGameController controller;
    private Pane animPane;
    private int score = 0;
    
    private boolean gameOver = false;

    /**
     * Constructor for GameManager
     * @param controller the main controller for the game
     * @param animPane the root pane in which the game elements are placed
     */
    public GameManager(MainGameController controller, Pane animPane) {
        level = 0;
        this.controller = controller;
        this.animPane = animPane;
    }
    
    /**
     * Spawns invaders based on the level that it is on.
     * If the level % 3 is  1 (repetitions of the first level), enemies are spawn
     * in a 3 by (level/3 + 5) grid with slight random shifts.
     * If the level % 3 is  2 (repetitions of the second level), enemies are spawn
     * in a 4 by (level/3 + 4) grid with slight random shifts.
     * If the level % 3 is  0 (repetitions of the third level), enemies are spawn
     * in a 3 by (level/3 * 2) + 10 grid with the top row having the singular big
     * invader at the center. The second row has small invaders, and the third row 
     * will have medium invaders.
     */
    public void spawnInvaders(){
        int enemiesPerRow;
        int numOfRows;
        switch (level % 3){
            case 1 -> {
                enemiesPerRow = level/3 + 5;
                numOfRows = 3;
                int spacingX = (int)animPane.getWidth()/enemiesPerRow;
                int spacingY = (int)(animPane.getHeight() * 0.5 / numOfRows);
                Vector topLeft = new Vector(spacingX/2, spacingY/2);
    
                for(int j = 0; j < numOfRows; j++){
                    for (int i = 0; i < enemiesPerRow; i++) {
                        // This is only to follow the requirements, I personally don't like random spawning positions
                        Vector randomOffset = new Vector(
                            (Math.random() * 0.5 - 0.25) * spacingX, 
                            (Math.random() * 0.5 - 0.25) * spacingY
                        );
                        Invader invader = getRandomSmallInvader(
                                (int)Math.round(Math.random()), 
                                (int)(topLeft.getX() + i * spacingX + randomOffset.getX()),
                                (int)(topLeft.getY() + j * spacingY + randomOffset.getY()),
                                30, 30,
                                controller.getSmallInvaderSpeed(),
                                controller.getEnemyBulletSpeed()
                        );
                        
                        if (level > 3){
                            invader.speedMultiplier((level - 3) * 0.2);
                            invader.firingCooldownMult((level - 3) * 0.2);
                        }
                        animPane.getChildren().add(invader);
                        animPane.getChildren().add(invader.getHpBar());
                    }
                }
            }
            case 2 -> {
                enemiesPerRow = level/3 + 4;
                numOfRows = 5;
                int spacingX = (int)animPane.getWidth()/enemiesPerRow;
                int spacingY = (int)(animPane.getHeight() * 0.5 / numOfRows);
                Vector topLeft = new Vector(spacingX/2, spacingY/2);
                for(int j = 0; j < numOfRows; j++){
                    for (int i = 0; i < enemiesPerRow; i++) {
                        Invader invader;
                        
                        Vector randomOffset = new Vector(
                            (Math.random() * 0.5 - 0.25) * spacingX, 
                            (Math.random() * 0.5 - 0.25) * spacingY
                        );
                        if((i+j) % 2 == 0){
                            invader = getRandomSmallInvader(
                                (int)Math.round(Math.random()), 
                                (int)(topLeft.getX() + i * spacingX + randomOffset.getX()),
                                (int)(topLeft.getY() + j * spacingY + randomOffset.getY()),
                                30, 30,
                                controller.getSmallInvaderSpeed(),
                                controller.getEnemyBulletSpeed()
                            );
                        }
                        else{
                            invader = getRandomMediumInvader(
                                (int)Math.round(Math.random()), 
                                (int)(topLeft.getX() + i * spacingX + randomOffset.getX()),
                                (int)(topLeft.getY() + j * spacingY + randomOffset.getY()),
                                30, 30,
                                controller.getMediumInvaderSpeed(),
                                controller.getEnemyBulletSpeed()
                            );
                        }

                        if (level > 3){
                            invader.speedMultiplier((level - 3) * 0.2);
                            invader.firingCooldownMult((level - 3) * 0.2);
                        }
                        animPane.getChildren().add(invader);
                        animPane.getChildren().add(invader.getHpBar());
                    }
                }
               
            }
            case 0 -> {
                enemiesPerRow = ((level/3) * 2) + 10;
                numOfRows = 3;
                int spacingX = (int)(animPane.getWidth() /enemiesPerRow);
                int spacingY = (int)(animPane.getHeight() * 0.5 / numOfRows);
                Vector topLeft = new Vector(spacingX/2, spacingY/2);
                for(int j = 0; j < numOfRows; j++){
                    for (int i = 0; i < enemiesPerRow; i++) {
                        Invader invader = null;
                        if (j == 0){
                            if (i == (enemiesPerRow / 2)){
                                invader = new BigInvader(
                                    (int)topLeft.getX() + i * spacingX,
                                    (int)topLeft.getY() + j * spacingY, 60, 60,
                                    "bigInvader.png",
                                    controller.getBigInvaderSpeed(),
                                    controller.getEnemyBulletSpeed(),
                                    controller.getSpaceShip(),
                                    0.01
                                );
                            }
                        }
                        else if (j == 1){
                            invader = getRandomSmallInvader(
                                (int)Math.round(Math.random()), 
                                (int)topLeft.getX() + i * spacingX,
                                (int)topLeft.getY() + j * spacingY, 30, 30,
                                controller.getSmallInvaderSpeed(),
                                controller.getEnemyBulletSpeed()
                            );
                        }
                        else{
                            invader = getRandomMediumInvader(
                                (int)Math.round(Math.random()), 
                                (int)topLeft.getX() + i * spacingX,
                                (int)topLeft.getY() + j * spacingY, 30, 30,
                                controller.getSmallInvaderSpeed(),
                                controller.getEnemyBulletSpeed()
                            );
                        }
                        if (invader != null){
                            if (level > 3){
                                invader.speedMultiplier((level - 3) * 0.2);
                                invader.firingCooldownMult((level - 3) * 0.2);
                            }
                            
                            animPane.getChildren().add(invader);
                            animPane.getChildren().add(invader.getHpBar());
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Fetches a small invader on random chance. It can either be SmallInvader1
     * or SmallInvader2
     * @param nSmall values of 0 or 1, 0 for SmallInvader1 and 1 for SmallInvader2
     * @param x the x position of the sprite
     * @param y the y position of the sprite
     * @param width the width of the sprite
     * @param height the height of the sprite
     * @param speed the speed of the invader
     * @param bulletSpeed the bullet speed
     * @return a random SmallInvader
     */
    public Invader getRandomSmallInvader(int nSmall, int x, int y, int width, int height, double speed, double bulletSpeed){
        return switch (nSmall) {
            case 0 -> new SmallInvader1(x, y, width, height, "smallInvader1.png", speed, bulletSpeed);
            case 1 -> new SmallInvader2(x, y, width, height, "smallInvader2.png", speed, bulletSpeed);
            default -> null;
        };
    }
    
        /**
     * Fetches a small invader on random chance. It can either be MediumInvader1
     * or MediumInvader2
     * @param nSmall values of 0 or 1, 0 for MediumInvader1 and 1 for MediumInvader2
     * @param x the x position of the sprite
     * @param y the y position of the sprite
     * @param width the width of the sprite
     * @param height the height of the sprite
     * @param speed the speed of the invader
     * @param bulletSpeed the bullet speed
     * @return a random MediumInvader
     */
    public Invader getRandomMediumInvader(int nSmall, int x, int y, int width, int height, double speed, double bulletSpeed){
        return switch (nSmall) {
            case 0 -> new MediumInvader1(x, y, width, height, "mediumInvader1.png", speed, bulletSpeed);
            case 1 -> new MediumInvader2(x, y, width, height, "mediumInvader2.png", speed, bulletSpeed);
            default -> null;
        };
    }
    
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    
    public void levelUp(){
        level++;
    }    

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    public void increaseScore(int score){
        this.score += score;
        if (this.score < 0){
            this.score = 0;
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
    
    public void reset(){
        gameOver = false;
        level = 0;
        score = 0;
    }
}
