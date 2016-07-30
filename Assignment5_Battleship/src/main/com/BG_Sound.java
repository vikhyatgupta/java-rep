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
import sun.audio.*;    //import the sun.audio package
import java.io.*;
import java.net.URL;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class BG_Sound {

    AudioStream miss, bomb;

    public void play_miss() {
        InputStream in = null;
        try {
            URL url = getClass().getResource("/audio/miss.mp3");
            in = new FileInputStream(url.getPath());
            miss = new AudioStream(in);
            AudioPlayer.player.start(miss);
            //AudioPlayer.player.stop(miss);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BG_Sound.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BG_Sound.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(BG_Sound.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void play_hit() {
        InputStream in1 = null;
        try {
            URL url1 = getClass().getResource("/audio/Bomb.mp3");
            in1 = new FileInputStream(url1.getPath());
            bomb = new AudioStream(in1);
            AudioPlayer.player.start(bomb);
            //AudioPlayer.player.stop(bomb);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BG_Sound.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BG_Sound.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in1.close();
            } catch (IOException ex) {
                Logger.getLogger(BG_Sound.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
 
}
