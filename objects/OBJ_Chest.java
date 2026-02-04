package objects;

import main.GamePanel;


import entity.Entity;


/*We changed what class we extended the objects from. */
public class OBJ_Chest extends Entity {

    GamePanel gp;

    public OBJ_Chest(GamePanel gp) {
        super(gp);
        name = "Chest";
        down1 = setup("/objects/chest");

       
    }
}

