package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_ManaCrystal extends Entity{
    GamePanel gp;
    public OBJ_ManaCrystal(GamePanel gp){
        super(gp);
        this.gp = gp;
        image = setup("/objects/mana full", gp.tileSize, gp.tileSize);
        image2 = setup("/objects/mana empty", gp.tileSize, gp.tileSize);
        name = "Mana Crystal";
        value = 2;
        down1 = image;
        
        type = type_pickupOnly;
    }

    public void use(Entity entity){
        gp.playSE(2);
        gp.ui.addMessage("You healed " + value + " Mana!");
        entity.mana += value;
    }

    
}
