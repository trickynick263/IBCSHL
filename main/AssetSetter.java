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
import tiles_interactive.IT_DryTree;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp){
        this.gp = gp;
        setObject();
    }

    public void setObject(){
        int i = 0;
        gp.obj[i] = new OBJ_Key(gp);
        gp.obj[i].worldX = gp.tileSize * 65;
        gp.obj[i].worldY = gp.tileSize * 65;
        i++;
        gp.obj[i] = new OBJ_Axe(gp);
        gp.obj[i].worldX = gp.tileSize * 64;
        gp.obj[i].worldY = gp.tileSize * 64;
        i++;
        gp.obj[i] = new OBJ_Metal_Shield(gp);
        gp.obj[i].worldX = gp.tileSize * 63;
        gp.obj[i].worldY = gp.tileSize * 63;
        i++;
        gp.obj[i] = new OBJ_Potion_Pink(gp);
        gp.obj[i].worldX = gp.tileSize * 62;
        gp.obj[i].worldY = gp.tileSize * 62;
        i++;
        gp.obj[i] = new OBJ_GoldCoin(gp);
        gp.obj[i].worldX = gp.tileSize * 61;
        gp.obj[i].worldY = gp.tileSize * 61;
        i++;
        gp.obj[i] = new OBJ_Heart(gp);
        gp.obj[i].worldX = gp.tileSize * 60;
        gp.obj[i].worldY = gp.tileSize * 61;
        i++;
        gp.obj[i] = new OBJ_ManaCrystal(gp);
        gp.obj[i].worldX = gp.tileSize * 59;
        gp.obj[i].worldY = gp.tileSize * 61;
        i++;
        gp.obj[i] = new OBJ_GoldCoin(gp);
        gp.obj[i].worldX = gp.tileSize * 58;
        gp.obj[i].worldY = gp.tileSize * 61;
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

    public void setInteractiveTile(){
        int i = 0;
        gp.iTile[i] = new IT_DryTree(gp,58,48);
        i++;
        gp.iTile[i] = new IT_DryTree(gp,58,47);
        
        i++;
        gp.iTile[i] = new IT_DryTree(gp,58,46);
        
        i++;
        
    }

    
}
