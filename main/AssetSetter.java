package main;

import entity.NPC_OldMan;
import monster.MON_Slime;
import objects.OBJ_Door;
import objects.OBJ_Key;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp){
        this.gp = gp;
        setObject();
    }

    public void setObject(){
        gp.obj[0] = new OBJ_Key(gp);
        gp.obj[0].worldX = gp.tileSize * 8;
        gp.obj[0].worldY = gp.tileSize * 8;

        
    }

    public void setNPC(){
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize * 4;
        gp.npc[0].worldY = gp.tileSize * 4;

       
    }

    public void setMonster(){
        gp.monster[0] = new MON_Slime(gp);
        gp.monster[0].worldX = 9*gp.tileSize;
        gp.monster[0].worldY = 9*gp.tileSize;

        gp.monster[1] = new MON_Slime(gp);
        gp.monster[1].worldX = 13*gp.tileSize;
        gp.monster[1].worldY = 13*gp.tileSize;
        
        gp.monster[2] = new MON_Slime(gp);
        gp.monster[2].worldX = 16*gp.tileSize;
        gp.monster[2].worldY = 19*gp.tileSize;

        gp.monster[3] = new MON_Slime(gp);
        gp.monster[3].worldX = 31*gp.tileSize;
        gp.monster[3].worldY = 24*gp.tileSize;
        
        gp.monster[4] = new MON_Slime(gp);
        gp.monster[4].worldX = 30*gp.tileSize;
        gp.monster[4].worldY = 25*gp.tileSize;
    }

    
}
