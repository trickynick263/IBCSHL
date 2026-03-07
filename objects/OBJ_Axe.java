package objects;

import main.GamePanel;


import entity.Entity;


/*We changed what class we extended the objects from. */
public class OBJ_Axe extends Entity {

    GamePanel gp;

    public OBJ_Axe(GamePanel gp) {
        super(gp);
        name = "Basic Axe";
        down1 = setup("/objects/axe", gp.tileSize, gp.tileSize);
        description =  "["+ name+ "]\n" +"A basic tool used \nfor basic things";
        attackArea.width = 30;
        attackArea.height = 30;
        type = type_axe;
    }
}
