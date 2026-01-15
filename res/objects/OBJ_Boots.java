package res.objects;

import main.GamePanel;

public class OBJ_Boots extends SuperObject {//a boots object that the player can pick up to increase speed
    GamePanel gp;
    public OBJ_Boots(GamePanel gp){
        name = "Boots";//sets name to Boots as it is a boots object
        try{
            image = javax.imageio.ImageIO.read(new java.io.File("res/objects/boots.png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(java.io.IOException e){
            e.printStackTrace();
        }
    }
    
}
