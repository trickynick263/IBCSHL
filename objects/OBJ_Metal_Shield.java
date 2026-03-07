package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_Metal_Shield extends Entity{
    public OBJ_Metal_Shield( GamePanel gp ){
        super(gp);
        name = "Metal Shield";
        down1 = setup("/objects/metal shield",gp.tileSize,gp.tileSize);
        description = "["+name+"]\n" + "An upgraded shield..";
        type = type_shield;
    }
}
