package res.objects;

import main.GamePanel;

public class OBJ_Door extends SuperObject {//a door object that can be opened with a key
    GamePanel gp;
    public OBJ_Door(GamePanel gp){ 
        
        name = "Door";//sets name to Door as it is a door object
        try {
            image = javax.imageio.ImageIO.read(new java.io.File("res/objects/door.png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        
        }
        collision = true;//sets collision to true so player cant walk through door unless they have key
    }
}

