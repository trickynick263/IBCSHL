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

        int tileNum1, tileNum2;//we will check 2 tiles at a time for collision
        //we will check 2 tiles at a time and it is based on direction, we can start by having a player with
        //colliding on nothing, if the player moves up, we know they are not colliding with anything in all other
        //directions except up because they are heading up, so we only need to check the tile of the player and the tile
        //above them to the left and right
        switch(entity.direction){
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;//this line basically predicts
                //where the entity will be after moving up by its speed, so we check it little by little and
                //when one of the little predicted positions collides, we stop the entity from moving
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
        }


    }

    public int checkObject(Entity entity, boolean player){
    
        int index = 999;

        for(int i = 0;i < gp.obj.length;i++){
            if(gp.obj[i] != null)//we need to get entity's solid area position and
            // get the objects solid area position
            {
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY +  entity.solidArea.y;
                //these lines have set code in case we wanted to change the solid area of the 
                //objects later on and have more specific collsion detection and the whole tile isnt
                //used for collision detection
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;
                //we will now check collision based on direction
                switch(entity.direction){
                case "up":
                    entity.solidArea.y -= entity.speed;//predicts where entity will be next
                    if(entity.solidArea.intersects(gp.obj[i].solidArea)){//sees if the solid areas intersect
                        if(gp.obj[i].collision == true){//if intersects and object has collision true
                            entity.collisionOn = true;
                        }
                        if(player == true){
                            index = i;//we will return the index of the object we collided with
                        }
                        
                    }
                    break;
                case "down":
                    entity.solidArea.y += entity.speed;
                    if(entity.solidArea.intersects(gp.obj[i].solidArea)){
                        if(gp.obj[i].collision == true){//if intersects and object has collision true
                            entity.collisionOn = true;
                        }
                        if(player == true){
                            index = i;//we will return the index of the object we collided with
                        }
                    }
                    break;
                case "left":
                    entity.solidArea.x -= entity.speed;
                    if(entity.solidArea.intersects(gp.obj[i].solidArea)){
                       if(gp.obj[i].collision == true){//if intersects and object has collision true
                            entity.collisionOn = true;
                        }
                        if(player == true){
                            index = i;//we will return the index of the object we collided with
                        }
                    }
                    break;
                case "right":
                    entity.solidArea.x += entity.speed;
                    if(entity.solidArea.intersects(gp.obj[i].solidArea)){
                        if(gp.obj[i].collision == true){//if intersects and object has collision true
                            entity.collisionOn = true;
                        }
                        if(player == true){
                            index = i;//we will return the index of the object we collided with
                        }
                    }
                    break;



                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;//we have to reset values
                //so they dont keep increasing as we check for collisions
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;




            }

        }
        
        
        return index;

    }




}
