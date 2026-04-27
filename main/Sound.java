package main;

import java.io.File;
//import java.io.File;
import java.net.URL;
//import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[30];
    FloatControl fc;
    int volumeScale = 3;
    float volume;
    GamePanel gp;
    public Sound(GamePanel gp){//we initialize every sound in the array here
        this.gp = gp;
        if(gp.atHome == false){
        getClass().getResourceAsStream("/res/sound/adventure.wav");
        soundURL[0] = getClass().getResource("/res/sound/adventure.wav");
        soundURL[1] = getClass().getResource("/res/sound/coin.wav");
        soundURL[2] = getClass().getResource("/res/sound/powerup.wav");
        soundURL[3] = getClass().getResource("/res/sound/unlock.wav");
        soundURL[4] = getClass().getResource("/res/sound/fanfare.wav");
        soundURL[5] = getClass().getResource("/res/sound/hitmonster.wav");
        soundURL[6] = getClass().getResource("/res/sound/receivedamage.wav");
        soundURL[7] = getClass().getResource("/res/sound/swingsword.wav");
        soundURL[8] = getClass().getResource("/res/sound/cursor.wav");
        soundURL[9] = getClass().getResource("/res/sound/burning.wav");
        soundURL[10] = getClass().getResource("/res/sound/treebreak.wav");
        soundURL[11] = getClass().getResource("/res/sound/teleport.wav");
        soundURL[12] = getClass().getResource("/res/sound/death.wav");
        soundURL[13] = getClass().getResource("/res/sound/stairs.wav");
        }
        else{
             try        {
            soundURL[0] = new File("res/sound/adventure.wav").toURI().toURL();
            soundURL[1] = new File("res/sound/coin.wav").toURI().toURL();
            soundURL[2] = new File("res/sound/powerup.wav").toURI().toURL();
            soundURL[3] = new File("res/sound/unlock.wav").toURI().toURL();
            soundURL[4] = new File("res/sound/fanfare.wav").toURI().toURL();
            soundURL[5] = new File("res/sound/hitmonster.wav").toURI().toURL();
            soundURL[6] = new File("res/sound/receivedamage.wav").toURI().toURL();
            soundURL[7] = new File("res/sound/swingsword.wav").toURI().toURL(); 
            soundURL[8] = new File("res/sound/cursor.wav").toURI().toURL();
            soundURL[9] = new File("res/sound/burning.wav").toURI().toURL();
            soundURL[10] = new File("res/sound/treebreak.wav").toURI().toURL();
            soundURL[11] = new File("res/sound/teleport.wav").toURI().toURL();
            soundURL[12] = new File("res/sound/death.wav").toURI().toURL();
            soundURL[13] = new File("res/sound/stairs.wav").toURI().toURL();
        }catch(Exception e){
            e.printStackTrace();
        }
        }

         
         

        
       
            
            
        
    }

    public void setFile(int i){

        try{

            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
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

    public void checkVolume(){
        switch(volumeScale){
            case 0: volume = -80f; break;
            case 1: volume = -20f; break;
            case 2: volume = -12f; break;
            case 3: volume = -5f; break;
            case 4: volume = 1f; break;
            case 5: volume = 6f; break;
        }
        fc.setValue(volume);
    }


}
