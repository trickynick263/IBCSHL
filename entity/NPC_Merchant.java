package entity;

import main.GamePanel;
import objects.OBJ_Boots;
import objects.OBJ_Key;
import objects.OBJ_Metal_Shield;
import objects.OBJ_Potion_Pink;
import objects.OBJ_Sword_Normal;

public class NPC_Merchant extends Entity{
     public NPC_Merchant(GamePanel gp){
        super(gp);
        direction = "down";
        getImage();
        setDialogue();
        solidArea.x = 8;
        solidArea.y = 16;//we can also set the values one by one
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;
        setItems();

    }

    public void getImage(){
            up1 = setup("/npcimage/merchant 1", gp.tileSize, gp.tileSize);
            up2 = setup("/npcimage/merchant 2", gp.tileSize, gp.tileSize);
            down1 = setup("/npcimage/merchant 1", gp.tileSize, gp.tileSize);
            down2 = setup("/npcimage/merchant 2", gp.tileSize, gp.tileSize);
            left1 = setup("/npcimage/merchant 1", gp.tileSize, gp.tileSize);
            left2 = setup("/npcimage/merchant 2", gp.tileSize, gp.tileSize);
            right1 = setup("/npcimage/merchant 1", gp.tileSize, gp.tileSize);
            right2 = setup("/npcimage/merchant 2", gp.tileSize, gp.tileSize);
            
    }

    public void setDialogue(){//STORE DIALOGUE TEXT
        dialogue[0] = "Found me huh?\n I've got the goods but you \nbetter have the supply";
    }

    public void setItems(){
        this.inventory.add(new OBJ_Boots(gp));
        this.inventory.add(new OBJ_Key(gp));
        this.inventory.add(new OBJ_Potion_Pink(gp));
        this.inventory.add(new OBJ_Metal_Shield(gp));
        this.inventory.add(new OBJ_Sword_Normal(gp));
    }

    public void speak(){
        super.speak();
        gp.ui.subState = 0;
        gp.gameState = gp.tradeState;
        gp.ui.npc = this;
    }
}
