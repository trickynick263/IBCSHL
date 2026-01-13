package res.objects;

import java.io.IOException;

public class OBJ_Key extends SuperObject {//a key object that the player can pick up

    public OBJ_Key(){
        name = "Key";//sets name to Key as it is a key object
        try{
            image = javax.imageio.ImageIO.read(new java.io.File("res/objects/key.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
}
