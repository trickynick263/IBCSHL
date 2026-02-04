package objects;

import main.GamePanel;


import entity.Entity;


/*We changed what class we extended the objects from. */
public class OBJ_Boots extends Entity {

    GamePanel gp;

    public OBJ_Boots(GamePanel gp) {
        super(gp);
        name = "Boots";
        down1 = setup("/objects/boots");

       
    }
}