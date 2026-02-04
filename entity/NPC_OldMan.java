package entity;



import java.util.Random;

import main.GamePanel;


public class NPC_OldMan extends Entity{
    
    
    public NPC_OldMan(GamePanel gp){
        super(gp);
        direction = "down";
        speed = 1;
        getImage();
        setDialogue();

        solidArea.x = 8;
        solidArea.y = 16;//we can also set the values one by one
        
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        
        
        solidArea.width = 32;
        solidArea.height = 32;

    }

    public void setDialogue(){//STORE DIALOGUE TEXT
        dialogue[0] = "whats going on bro";
        dialogue[1] = "So you came to find the treasure?";
        dialogue[2] = "I used to be a great wizard but now.. \nI'm way too old for that";
        dialogue[3] = "Well, good luck.. \nI've heard too many daunting\n stories of the guardian";
        
        

    }

    public void speak(){
        super.speak();
    }


    public void getImage(){
       
        up1 = setup("/npcimage/oldman_up_1");
        up2 = setup("/npcimage/oldman_up_2");
        down1 = setup("/npcimage/oldman_down_1");
        down2 = setup("/npcimage/oldman_down_2");
        left1 = setup("/npcimage/oldman_left_1");
        left2 = setup("/npcimage/oldman_left_2");
        right1 = setup("/npcimage/oldman_right_1");
        right2 = setup("/npcimage/oldman_right_2");

    }

    public void setAction(){//sets character behavior with AI
    
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
