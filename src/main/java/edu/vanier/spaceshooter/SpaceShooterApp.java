package edu.vanier.spaceshooter;

import edu.vanier.spaceshooter.controllers.MainAppFXMLController;
import edu.vanier.spaceshooter.controllers.MainMenuController;
import java.io.IOException;
import java.util.logging.Level;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpaceShooterApp extends Application {

    private final static Logger logger = LoggerFactory.getLogger(SpaceShooterApp.class);

    public static int screenWidth = 1000;
    public static int screenHeight = 600;

    MainAppFXMLController controller;
    
    Scene mainScene;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            logger.info("Bootstrapping the application...");
            //-- 1) Load the scene graph from the specified FXML file and 
            // associate it with its FXML controller.
            
            FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
            MainMenuController mainMenuController = new MainMenuController();
            menuLoader.setController(mainMenuController);
            Pane root = menuLoader.load();
            
            Button playButton = mainMenuController.getPlayButton();
            playButton.setOnAction((event) -> {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainApp_layout.fxml"));
                controller = new MainAppFXMLController(primaryStage);
                loader.setController(controller);
                Pane gameRoot = null;
                try {
                    gameRoot = loader.load();
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(SpaceShooterApp.class.getName()).log(Level.SEVERE, null, ex);
                }
                mainScene = new Scene(gameRoot, screenWidth, screenHeight);
                controller.setScene(mainScene);
                controller.setupGameWorld();
                primaryStage.setScene(mainScene);
            });
            
            
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

    public Scene getMainScene() {
        return mainScene;
    }

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }
    
    @Override
    public void stop() throws Exception {
        // Stop the animation timer upon closing the main stage.
//        controller.stopAnimation();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
