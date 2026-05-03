package objects;

import main.GamePanel;


import entity.Entity;


/*We changed what class we extended the objects from. */
public class OBJ_Key extends Entity {

    public OBJ_Key(GamePanel gp) {
        super(gp);
        name = "Key";
        down1 = setup("/objects/key", gp.tileSize, gp.tileSize);
        description = "["+ name+ "]\n" + "Just open the door \nalready";
        type = type_consumable;
        price = 50;
        stackable = true;
    }

    public boolean use(Entity entity){
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You used the key to open the door.";

        int objectIndex = getDetected(entity, gp.obj, "Door");
        if(objectIndex != 999){
            gp.ui.currentDialogue = "You used the key to open the door.";
            gp.playSE(3);
            gp.obj[gp.currentMap][objectIndex] = null;
            return true;
        }else{
            gp.ui.currentDialogue = "Keys are meant for doors, you know.";
            return false;
        }
    }
}


