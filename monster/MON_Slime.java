package monster;
import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class MON_Slime extends Entity{
    public MON_Slime(GamePanel gp){
        super(gp);
        name = "Slime";
        speed = 1;
        maxLife = 4;
        life = maxLife;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }
    
    public void getImage(){
        up1 = setup("/monster/slime_down1");
        up2 = setup("/monster/slime_down2");
        down1 = setup("/monster/slime_down1");
        down2 = setup("/monster/slime_down2");
        left1 = setup("/monster/slime_down1");
        left2 = setup("/monster/slime_down2");
        right1 = setup("/monster/slime_down1");
        right2 = setup("/monster/slime_down2");

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
}
