package res.objects;

import main.GamePanel;

public class OBJ_Chest extends SuperObject {//a chest object that can be opened to find items inside
    GamePanel gp;
    
    public OBJ_Chest(GamePanel gp){ 
    name = "Chest";//sets name to Chest as it is a chest object
    try {
        image = javax.imageio.ImageIO.read(new java.io.File("res/objects/chest.png"));
        image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
    } catch (java.io.IOException e) {
        e.printStackTrace();


    }
    }
}
