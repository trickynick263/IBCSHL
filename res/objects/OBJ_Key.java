package res.objects;

import java.io.IOException;

import main.GamePanel;

public class OBJ_Key extends SuperObject {//a key object that the player can pick up

GamePanel gp;


    public OBJ_Key(GamePanel gp){
        name = "Key";//sets name to Key as it is a key object
        try{
            image = javax.imageio.ImageIO.read(new java.io.File("res/objects/key.png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
}
