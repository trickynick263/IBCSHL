package main;

import entity.NPC_OldMan;
import monster.MON_Slime;
import objects.OBJ_Axe;
import objects.OBJ_GoldCoin;
import objects.OBJ_Heart;
//import objects.OBJ_Door;
import objects.OBJ_Key;
import objects.OBJ_ManaCrystal;
import objects.OBJ_Metal_Shield;
import objects.OBJ_Potion_Pink;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp){
        this.gp = gp;
        setObject();
    }

    public void setObject(){
        int i = 0;
        gp.obj[i] = new OBJ_Key(gp);
        gp.obj[i].worldX = gp.tileSize * 55;
        gp.obj[i].worldY = gp.tileSize * 55;
        i++;
        gp.obj[i] = new OBJ_Axe(gp);
        gp.obj[i].worldX = gp.tileSize * 54;
        gp.obj[i].worldY = gp.tileSize * 54;
        i++;
        gp.obj[i] = new OBJ_Metal_Shield(gp);
        gp.obj[i].worldX = gp.tileSize * 53;
        gp.obj[i].worldY = gp.tileSize * 53;
        i++;
        gp.obj[i] = new OBJ_Potion_Pink(gp);
        gp.obj[i].worldX = gp.tileSize * 52;
        gp.obj[i].worldY = gp.tileSize * 52;
        i++;
        gp.obj[i] = new OBJ_GoldCoin(gp);
        gp.obj[i].worldX = gp.tileSize * 51;
        gp.obj[i].worldY = gp.tileSize * 51;
        i++;
        gp.obj[i] = new OBJ_Heart(gp);
        gp.obj[i].worldX = gp.tileSize * 56;
        gp.obj[i].worldY = gp.tileSize * 56;
        i++;
        gp.obj[i] = new OBJ_ManaCrystal(gp);
        gp.obj[i].worldX = gp.tileSize * 57;
        gp.obj[i].worldY = gp.tileSize * 57;
        i++;
        gp.obj[i] = new OBJ_GoldCoin(gp);
        gp.obj[i].worldX = gp.tileSize * 58;
        gp.obj[i].worldY = gp.tileSize * 58;
        i++;


        
    }

    public void setNPC(){
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize * 51;
        gp.npc[0].worldY = gp.tileSize * 51;

       
    }

    public void setMonster(){
        gp.monster[0] = new MON_Slime(gp);
        gp.monster[0].worldX = 60*gp.tileSize;
        gp.monster[0].worldY = 60*gp.tileSize;

        gp.monster[1] = new MON_Slime(gp);
        gp.monster[1].worldX = 61*gp.tileSize;
        gp.monster[1].worldY = 61*gp.tileSize;
        
        gp.monster[2] = new MON_Slime(gp);
        gp.monster[2].worldX = 63*gp.tileSize;
        gp.monster[2].worldY = 63*gp.tileSize;

        gp.monster[3] = new MON_Slime(gp);
        gp.monster[3].worldX = 65*gp.tileSize;
        gp.monster[3].worldY = 65*gp.tileSize;
        
        gp.monster[4] = new MON_Slime(gp);
        gp.monster[4].worldX = 66*gp.tileSize;
        gp.monster[4].worldY = 66*gp.tileSize;
    }

    
}
