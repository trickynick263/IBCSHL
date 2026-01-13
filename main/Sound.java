package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    Clip clip;//this is how we open sound files in java
    URL soundURL[] = new URL[30];//we use this to store the file path of the sound files
    public Sound(){//we initialize every sound in the array here
        soundURL[0] = getClass().getResource("/res/sound/BlueBoyAdventure.wav");
        soundURL[1] = getClass().getResource("/res/sound/coin.wav");
        soundURL[2] = getClass().getResource("/res/sound/powerup.wav");
        soundURL[3] = getClass().getResource("/res/sound/unlock.wav");
        soundURL[4] = getClass().getResource("/res/sound/fanfare.wav");
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
