package monster;
import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class MON_Slime extends Entity{
    GamePanel gp;
    
    public MON_Slime(GamePanel gp){
        super(gp);
        this.gp = gp;
        name = "Slime";
        speed = 1;
        maxLife = 4;
        life = maxLife;
        type = 2;
        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attack = 5;
        defense = 0;
        exp = 3;

        getImage();
    }
    
    public void getImage(){
        up1 = setup("/monster/slime_down1", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/slime_down2", gp.tileSize, gp.tileSize);
        down1 = setup("/monster/slime_down1", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/slime_down2", gp.tileSize, gp.tileSize);
        left1 = setup("/monster/slime_down1", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/slime_down2", gp.tileSize, gp.tileSize);
        right1 = setup("/monster/slime_down1", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/slime_down2", gp.tileSize, gp.tileSize);

    }
    public void setAction(){
        if(actionLockCounter == 120){
        Random random = new Random();
        int i = random.nextInt(100)+1;//random number from 1 to 100
    if(i <= 25){
        direction = "up";
    }
    if(i > 25 && i <= 50 ){
        direction = "down";
    }
    if(i > 50 && i <=75){
        direction = "left";
    }
    if(i > 75 && i <= 100){
        direction = "right";
    }
    actionLockCounter = 0;
}
else{
    actionLockCounter++;
}

    
    }


public void damageReaction(){
    actionLockCounter = 0;
    direction = gp.player.direction;
}
}
