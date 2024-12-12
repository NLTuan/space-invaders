/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.helpers;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author letua
 */
public class AudioPlayer {
    MediaPlayer mediaPlayer;

    public AudioPlayer(String path) {
        Media media = new Media(getClass().getResource(path).toExternalForm());
        mediaPlayer = new MediaPlayer(media);
    }
    
    public void setVolume(double volume){
        mediaPlayer.setVolume(volume);
    }
    
    public void play(){
        mediaPlayer.play();
    }
    
    
}
