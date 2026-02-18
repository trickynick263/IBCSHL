package main;

import java.io.File;
import java.net.URL;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    Clip clip;//this is how we open sound files in java
    URL soundURL[] = new URL[30];//we use this to store the file path of the sound files
    public Sound(){//we initialize every sound in the array here
        /* 
        soundURL[0] = getClass().getResource("/res/sound/BlueBoyAdventure.wav");
        soundURL[1] = getClass().getResource("/res/sound/coin.wav");
        soundURL[2] = getClass().getResource("/res/sound/powerup.wav");
        soundURL[3] = getClass().getResource("/res/sound/unlock.wav");
        soundURL[4] = getClass().getResource("/res/sound/fanfare.wav");
        soundURL[5] = getClass().getResource("/res/sound/hitmonster.wav");
        soundURL[6] = getClass().getResource("/res/sound/receivedamage.wav");
        soundURL[7] = getClass().getResource("/res/sound/swingsword.wav");
        */
         

        
        try        {
            soundURL[0] = new File("res/sound/BlueBoyAdventure.wav").toURI().toURL();
            soundURL[1] = new File("res/sound/coin.wav").toURI().toURL();
            soundURL[2] = new File("res/sound/powerup.wav").toURI().toURL();
            soundURL[3] = new File("res/sound/unlock.wav").toURI().toURL();
            soundURL[4] = new File("res/sound/fanfare.wav").toURI().toURL();
            soundURL[5] = new File("res/sound/hitmonster.wav").toURI().toURL();
            soundURL[6] = new File("res/sound/receivedamage.wav").toURI().toURL();
            soundURL[7] = new File("res/sound/swingsword.wav").toURI().toURL(); 
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }

    public void setFile(int i){

        try{

            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);//these lines are basically a format of opening an audio file in java
            //
        }catch(Exception e){

        }

    }

    public void play(){

        clip.start();

    }

    public void loop(){

        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        clip.stop();
    }


}
