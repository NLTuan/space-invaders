/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.spaceshooter.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author letua
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
    
    @FXML
    public void initialize(){
        bgImg.fitWidthProperty().bind(mainMenuPane.widthProperty());
        bgImg.fitHeightProperty().bind(mainMenuPane.heightProperty());
        
        menuVBox.prefWidthProperty().bind(mainMenuPane.widthProperty());
        menuVBox.prefHeightProperty().bind(mainMenuPane.heightProperty());   
    }
    
    public Button getPlayButton() {
        return playButton;
    }

    public void setPlayButton(Button playButton) {
        this.playButton = playButton;
    }
    
    
}
