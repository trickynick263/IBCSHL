package objects;

import main.GamePanel;
import entity.Entity;

public class OBJ_GoldCoin extends Entity{
    int value = 5;
    GamePanel gp;
    public OBJ_GoldCoin(GamePanel gp){
        super(gp);
        this.gp = gp;
        name = "Gold Coin";
        down1 = setup("/objects/coin",gp.tileSize,gp.tileSize);
        value = 5;
        type = type_pickupOnly;
    }

    public boolean use(Entity entity){
        gp.playSE(1);
        gp.ui.addMessage("You got " + value + " coins!");
        gp.player.coin += value;
        return true;
    }
    
}