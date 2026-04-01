package objects;

import main.GamePanel;
import entity.Entity;

public class OBJ_Potion_Pink extends Entity{
    GamePanel gp;
    public OBJ_Potion_Pink(GamePanel gp){
        super(gp);
        this.gp = gp;
        name = "Pink Potion";
        down1 = setup("/class/healer",gp.tileSize,gp.tileSize);
        description =  "["+ name+ "]\n" +"For Healing..";
        type = type_consumable;
        value = 5;
    }
    public void use(Entity entity){
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "Your Life Has Been Refreshed \nBy The " + name + "!";
        entity.life+=value;
        
    }
}