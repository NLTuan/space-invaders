package edu.vanier.spaceshooter;

import edu.vanier.spaceshooter.controllers.MainMenuController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpaceShooterApp extends Application {

    private final static Logger logger = LoggerFactory.getLogger(SpaceShooterApp.class);

    public static int screenWidth = 1000;
    public static int screenHeight = 600;
    
    private MainMenuController controller;
    @Override
    public void start(Stage primaryStage) {
        try {
            logger.info("Bootstrapping the application...");
            //-- 1) Load the scene graph from the specified FXML file and 
            // associate it with its FXML controller.
            
            FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
            controller = new MainMenuController(primaryStage);
            menuLoader.setController(controller);
            Pane root = menuLoader.load();
            
//            //-- 2) Create and set the scene to the stage.
            Scene mainMenuScene = new Scene(root, screenWidth, screenHeight);            
            primaryStage.setScene(mainMenuScene);
            primaryStage.setTitle("Space Invaders!");
            primaryStage.sizeToScene();
            primaryStage.setAlwaysOnTop(true);
            primaryStage.show();
            primaryStage.setAlwaysOnTop(false);
            
            
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
    
    @Override
    public void stop() throws Exception {
        // Stop the animation timer upon closing the main stage.
        controller.getGameController().stopAnimation();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
