package edu.vanier.spaceshooter.controllers;

import edu.vanier.spaceshooter.SpaceShooterApp;
import static edu.vanier.spaceshooter.SpaceShooterApp.screenHeight;
import static edu.vanier.spaceshooter.SpaceShooterApp.screenWidth;
import java.io.IOException;
import java.util.logging.Level;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller for the main menu page prompting the player to play the game
 * @author Le Tuan Huy Nguyen
 */
public class MainMenuController {
    
    @FXML
    private Button playButton;

    @FXML
    private Pane mainMenuPane;
    
    @FXML
    private ImageView bgImg;
    
    @FXML
    private VBox menuVBox;

    private Stage primaryStage;
    
    private MainAppFXMLController gameController;
    
    public MainMenuController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
    @FXML
    public void initialize(){
        // Bind the elements to the screen size
        bgImg.fitWidthProperty().bind(mainMenuPane.widthProperty());
        bgImg.fitHeightProperty().bind(mainMenuPane.heightProperty());
        
        menuVBox.prefWidthProperty().bind(mainMenuPane.widthProperty());
        menuVBox.prefHeightProperty().bind(mainMenuPane.heightProperty());   
    
        // Add the ability to start the game to the start button
        playButton.setOnAction((event) -> {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainApp_layout.fxml"));
                gameController = new MainAppFXMLController(primaryStage);
                loader.setController(gameController);
                Pane gameRoot = null;
                try {
                    gameRoot = loader.load();
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(SpaceShooterApp.class.getName()).log(Level.SEVERE, null, ex);
                }
                Scene mainScene = new Scene(gameRoot, screenWidth, screenHeight);
                gameController.setMainScene(mainScene);
                gameController.setupGameWorld();
                primaryStage.setScene(mainScene);
            });
    }
    
    public Button getPlayButton() {
        return playButton;
    }

    public void setPlayButton(Button playButton) {
        this.playButton = playButton;
    }

    public MainAppFXMLController getGameController() {
        return gameController;
    }

    public void setGameController(MainAppFXMLController gameController) {
        this.gameController = gameController;
    }
}
