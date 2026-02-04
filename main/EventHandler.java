package main;

import java.awt.Rectangle;

public class EventHandler {
    GamePanel gp;
    
    EventRect eventRect[][];
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;//we create these variables in order to record previous moves
    //if the player is about 1 tile away from the event object after interacting with it, we will let the player interact with it again

    public EventHandler(GamePanel gp){
        this.gp = gp;
        
        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
        int col = 0;
        int row = 0;

        while(col < gp.maxWorldCol && row < gp.maxWorldRow){//solid area for an event rect around the whole map
        eventRect[col][row] = new EventRect();
        eventRect[col][row].x = 23;
        eventRect[col][row].y=23;
        eventRect[col][row].width = 2;
        eventRect[col][row].height = 2;
        eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
        eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;
        col++;
        if(col == gp.maxWorldCol){
            col = 0;
            row++;
        }
    }
        // we create a tiny 2 by 2 rectangle that activates an event.
        //the reasom for such a tiny square is because we want to activate the event when the
        //player is at the center of the tile, not halfway through
        
    }

    public void checkEvent(){
        //check if player is more than 1 tile away from event
        int xDistance = Math.abs(gp.player.worldX - previousEventX);//|
        int yDistance = Math.abs(gp.player.worldY - previousEventY);//|-Distance between two things
        int distance = Math.max(xDistance, yDistance);              //|
        if(distance > gp.tileSize){
            canTouchEvent = true;

        }

        if(canTouchEvent == true){
            if(hit(10,10,"any") == true){damagePit(10,10,gp.dialogueState);}

            if(hit(8,2,"any")==true){ healingPool(8,2,gp.dialogueState);}

            if(hit(6,4,"any") == true){teleport(6,4,gp.playState);}

        }
    }

    public boolean hit(int col, int row, String reqDirection){
        boolean hit = false;
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[col][row].x = col*gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row*gp.tileSize + eventRect[col][row].y;

        //this checks if the two rectangles are hitting
        if(gp.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false){
            if(gp.player.direction == reqDirection || reqDirection.contentEquals("any")){
                hit = true;
                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }
        // by breaking down the if statements, we can set the req direction to "any"
        //if we want the player to be able to activate the event at any time.
        //If two rectangles are intersecting, we can then activate the event.

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x= eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        //these mechanics are pretty similar to the 
        return hit;
    }

    public void damagePit(int col, int row,int gameState){
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You fell into a pit!!!!";
        gp.player.life -=1;
        canTouchEvent = false;
    }

    public void healingPool(int col, int row,int gameState){
        if(gp.keyH.enterPressed==true) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "The Magic Wall Has Healed You!";
        gp.player.life = gp.player.maxLife;
        
        }
        
    }

    

    
    
    public void teleport(int col, int row,int gameState){
        gp.gameState = gameState;
        
        gp.player.worldX = 25 * gp.tileSize;
        gp.player.worldY = 25*gp.tileSize;
        canTouchEvent = false;

    }
   
}
