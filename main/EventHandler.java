package main;

import java.awt.Rectangle;

public class EventHandler {
    GamePanel gp;
    Rectangle eventRect;
    int eventRectDefaultX, eventRectDefaultY;

    public EventHandler(GamePanel gp){
        this.gp = gp;
        eventRect = new Rectangle();
        eventRect.x = 23;
        eventRect.y=23;
        eventRect.width = 2;
        eventRect.height = 2;
        eventRectDefaultX = eventRect.x;
        eventRectDefaultY = eventRect.y;
        // we create a tiny 2 by 2 rectangle that activates an event.
        //the reasom for such a tiny square is because we want to activate the event when the
        //player is at the center of the tile, not halfway through
    }

    public void checkEvent(){
        if(hit(10,10,"any") == true){damagePit(gp.dialogueState);}

        if(hit(8,2,"any")==true){ healingPool(gp.dialogueState);}

        if(hit(6,4,"any") == true){teleport(gp.dialogueState);}
    
    }

    public boolean hit(int eventCol, int eventRow, String reqDirection){
        boolean hit = false;
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect.x = eventCol*gp.tileSize + eventRect.x;
        eventRect.y = eventRow*gp.tileSize + eventRect.y;

        //this checks if the two rectangles are hitting
        if(gp.player.solidArea.intersects(eventRect)){
            if(gp.player.direction == reqDirection || reqDirection.contentEquals("any")){
                hit = true;
            }
        }
        // by breaking down the if statements, we can set the req direction to "any"
        //if we want the player to be able to activate the event at any time.
        //If two rectangles are intersecting, we can then activate the event.

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect.x= eventRectDefaultX;
        eventRect.y = eventRectDefaultY;

        //these mechanics are pretty similar to the 
        return hit;
    }

    public void damagePit(int gameState){
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You fell into a pit!!!!";
        gp.player.life -=1;
    }

    public void healingPool(int gameState){
        if(gp.keyH.enterPressed==true) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "The Magic Wall Has Healed You!";
        gp.player.life = gp.player.maxLife;
        }
        
    }

    

    public void teleport(int gameState){
        gp.gameState = gameState;
        gp.ui.currentDialogue = "Teleported!";
        gp.player.worldX = 25 * gp.tileSize;
        gp.player.worldY = 25*gp.tileSize;

    }
}
