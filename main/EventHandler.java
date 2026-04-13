package main;



public class EventHandler {
    GamePanel gp;
    
    EventRect eventRect[][][];
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;

    public EventHandler(GamePanel gp){
        this.gp = gp;
        
        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        int col = 0;
        int row = 0;
        int map = 0;

        while(map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow){//solid area for an event rect around the whole map
        eventRect[map][col][row] = new EventRect();
        eventRect[map][col][row].x = 23;
        eventRect[map][col][row].y=23;
        eventRect[map][col][row].width = 2;
        eventRect[map][col][row].height = 2;
        eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
        eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
        col++;
        if(col == gp.maxWorldCol){
            col = 0;
            row++;

            if(row == gp.maxWorldRow){
                row = 0;
                map++;
            }
        }
        
    }
        
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
            if(hit(0,30,30,"any") == true){damagePit(gp.dialogueState);}
            else if(hit(0,48,48,"any")==true){ healingPool(gp.dialogueState);}
            else if(hit(0,39,48,"any") == true){teleport(0,29,51);}
            else if(hit(0,30,50, "any") == true){teleport(0,50,50);}
            else if(hit(0,24,72,"any") == true){teleport(1,12,50);}
            else if(hit(1,12,50,"any") == true){teleport(0,24,72);}
        }
    }

    public boolean hit(int map, int col, int row, String reqDirection){
        boolean hit = false;
        if(map == gp.currentMap){
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[map][col][row].x = col*gp.tileSize + eventRect[map][col][row].x;
        eventRect[map][col][row].y = row*gp.tileSize + eventRect[map][col][row].y;
        //this checks if the two rectangles are hitting
        if(gp.player.solidArea.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone == false){
            if(gp.player.direction == reqDirection || reqDirection.contentEquals("any")){
                hit = true;

                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[map][col][row].x= eventRect[map][col][row].eventRectDefaultX;
        eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }
        return hit;
        
    }

    public void damagePit(int gameState){
        gp.gameState = gameState;
        gp.playSE(6);
        gp.ui.currentDialogue = "You fell into a pit!!!!";
        gp.player.life -=1;
        canTouchEvent = false;
    }

    public void healingPool(int gameState){
        if(gp.keyH.enterPressed==true) {
        gp.gameState = gameState;
        gp.playSE(2);
        gp.player.attackCanceled = true;
        gp.ui.currentDialogue = "The Magic Wall Has Healed You!";
        gp.player.life = gp.player.maxLife;
        gp.player.mana = gp.player.maxMana;
        gp.aSetter.setMonster();
        
        }
        
    }
    
    
    public void teleport(int map,int col, int row){
        gp.gameState = gp.transitionState;
        tempMap = map;
        tempCol = col;
        tempRow = row;
        gp.playSE(13);
        canTouchEvent = false;

    }
   
}
