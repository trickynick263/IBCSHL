package objects;

import main.GamePanel;


import entity.Entity;


/*We changed what class we extended the objects from. */
public class OBJ_Chest extends Entity {

    GamePanel gp;
    Entity loot;
    boolean opened = false;


    public OBJ_Chest(GamePanel gp, Entity loot) {
        super(gp);
        this.gp = gp;
        this.loot = loot;
        type = type_obstacle;
        name = "Chest";
        image = setup("/objects/chest", gp.tileSize, gp.tileSize);
        image2 = setup("/objects/opened chest", gp.tileSize, gp.tileSize);
        description =  "["+ name+ "]\n" + "How did you get this?";
        down1 = image;
        collision = true;
        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
       
    }

    public void interact(){
        gp.gameState = gp.dialogueState;
        if(opened == false){
            gp.playSE(3);
            StringBuilder sb = new StringBuilder();
            sb.append("You opened the chest and found a " + loot.name + "!");
            if(gp.player.inventory.size() == gp.player.maxInventorySize){
                sb.append("\n...But your inventory is full!");
            }else{
                gp.player.inventory.add(loot);
                down1 = image2;
                opened = true;
            }
            gp.ui.currentDialogue = sb.toString();

            
        }else{
            gp.ui.currentDialogue = "The chest is empty, you already took the loot.";
        }
    }
}

