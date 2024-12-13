package edu.vanier.helpers;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Creates a MediaPlayer given a filepath to an audio file that can be easily played.
 * @author Le Tuan Huy Nguyen
 */
public class AudioPlayer {
    MediaPlayer mediaPlayer;

    /**
     * Constructor for AudioPlayer
     * @param path the path to the audio file
     */
    public AudioPlayer(String path) {
        Media media = new Media(getClass().getResource(path).toExternalForm());
        mediaPlayer = new MediaPlayer(media);
    }
    
    /**
     * Setter for the MediaPlayer's volume
     * @param volume the proportion of the volume with respect to the original volume
     */
    public void setVolume(double volume){
        mediaPlayer.setVolume(volume);
    }
    
    /**
     * Play the file stored in the MediaPlayer.
     */
    public void play(){
        mediaPlayer.play();
    }
    
    
}
