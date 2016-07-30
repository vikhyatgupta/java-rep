/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.com;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class test {

    public test() {
         
        try {
            AudioInputStream audioInputStream
                    = AudioSystem.getAudioInputStream(
                            this.getClass().getResource("audio/Bomb.mp3"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            System.out.println("Started playing");
            clip.start();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
         

    }

    public String getpath(String filename) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(filename).getFile());

        return file.toURI().toString();
    }

    public static void main(String[] args) {
        test t = new test();
    }

}
