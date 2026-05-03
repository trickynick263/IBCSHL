package main;

import entity.Entity;

public class CollisionChecker {
    
    GamePanel gp;

    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }

    public void checkTile(Entity entity){//we import entity because we will also use
        //this for npcs and monsters which therefore are also entities
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;//by finding the coordinate
        //of the left side of the entity,then adding how far in the tile goes before we reach solid tile
        //that will give us the coordinate of the leftside of the solid area for the tile.
        //we repeat this further on with the right, top and bottom sides of the entity

        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;//by dividing by integers we can use its properties
        int entityTopRow = entityTopWorldY / gp.tileSize;//to get columns to correctly round down to the nearest whole and
        int entityBottomRow = entityBottomWorldY / gp.tileSize;//get the correct row/column above,down,left, or right of the entity

        int tileNum1, tileNum2;
        switch(entity.direction){
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
        }


    }

    public int checkObject(Entity entity, boolean player){
    
        int index = 999;

        for(int i = 0;i < gp.obj[1].length;i++){
            if(gp.obj[gp.currentMap][i] != null)
            {
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY +  entity.solidArea.y;
                gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].solidArea.x;
                gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].solidArea.y;
                switch(entity.direction){
                case "up":entity.solidArea.y -= entity.speed;break;
                case "down":entity.solidArea.y += entity.speed;break;
                case "left":entity.solidArea.x -= entity.speed;break;
                case "right":entity.solidArea.x += entity.speed;break;
                }
                if(entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)){
                       if(gp.obj[gp.currentMap][i].collision == true){//if intersects and object has collision true
                            entity.collisionOn = true;
                        }
                        if(player == true){
                            index = i;//we will return the index of the object we collided with
                        }
                    }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;//we have to reset values
                //so they dont keep increasing as we check for collisions
                gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].solidAreaDefaultX;
                gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].solidAreaDefaultY;




            }

        }
        
        
        return index;

    }
    //CHECKS NPC OR MONSTER COLLISION
    public int   checkEntity(Entity entity, Entity[][] target){

    int index = 999;

        for(int i = 0;i < target[1].length;i++){
            if(target[gp.currentMap][i] != null)//we need to get entity's solid area position and
            // get the objects solid area position
            {
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY +  entity.solidArea.y;
                //these lines have set code in case we wanted to change the solid area of the 
                //objects later on and have more specific collsion detection and the whole tile isnt
                //used for collision detection
                target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].worldX + target[gp.currentMap][i].solidArea.x;
                target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].worldY + target[gp.currentMap][i].solidArea.y;
                /*KEY DIFFERENCES BETWEEN CHECK OBJECT
                we just want to know if two entities are running into eachother and if they are something special will happen
                we dont care if its a player, we just want an entity to be checked
                */
                switch(entity.direction){
                case "up":
                    entity.solidArea.y -= entity.speed;//predicts where entity will be next
                    break;
                case "down":
                    entity.solidArea.y += entity.speed;
                    break;
                case "left":
                    entity.solidArea.x -= entity.speed;
                    break;
                case "right":
                    entity.solidArea.x += entity.speed;
                    break;
                }
                if(entity.solidArea.intersects(target[gp.currentMap][i].solidArea)){ 
                        if(target[gp.currentMap][i] != entity){
                            entity.collisionOn=true;
                            index = i;//we will return the index of the object we collided with   
                        }         
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;//we have to reset values
                //so they dont keep increasing as we check for collisions
                target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].solidAreaDefaultX;
                target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].solidAreaDefaultY;




            }

        }
        
        
        return index;

    }

    public boolean checkPlayer(Entity entity){//pasted from check entity method, we dont need to scan the array though.
                boolean contactPlayer = false;
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY +  entity.solidArea.y;
                //these lines have set code in case we wanted to change the solid area of the 
                //objects later on and have more specific collsion detection and the whole tile isnt
                //used for collision detection
                gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
                gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
                /*KEY DIFFERENCES BETWEEN CHECK OBJECT
                we just want to know if two entities are running into eachother and if they are something special will happen
                we dont care if its a player, we just want an entity to be checked
                */
                switch(entity.direction){
                case "up":
                    entity.solidArea.y -= entity.speed;//predicts where entity will be next
                    break;
                case "down":
                    entity.solidArea.y += entity.speed;
                    break;
                case "left":
                    entity.solidArea.x -= entity.speed;
                    break;
                case "right":
                    entity.solidArea.x += entity.speed;
                    break;
                }
                if(entity.solidArea.intersects(gp.player.solidArea)){//sees if the solid areas intersect
                        entity.collisionOn=true;
                        contactPlayer = true;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;//we have to reset values
                //so they dont keep increasing as we check for collisions
                gp.player.solidArea.x = gp.player.solidAreaDefaultX;
                gp.player.solidArea.y = gp.player.solidAreaDefaultY;

                return contactPlayer;


            }

        
        
        
        

    





}
