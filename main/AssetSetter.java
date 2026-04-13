package main;

import entity.NPC_Merchant;
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
        int mapNum = 0;
        gp.obj[mapNum][i] = new OBJ_Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 65;
        gp.obj[mapNum][i].worldY = gp.tileSize * 65;
        i++;
        gp.obj[mapNum][i] = new OBJ_Axe(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 64;
        gp.obj[mapNum][i].worldY = gp.tileSize * 64;
        i++;
        gp.obj[mapNum][i] = new OBJ_Metal_Shield(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 63;
        gp.obj[mapNum][i].worldY = gp.tileSize * 63;
        i++;
        gp.obj[mapNum][i] = new OBJ_Potion_Pink(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 62;
        gp.obj[mapNum][i].worldY = gp.tileSize * 62;
        i++;
        gp.obj[mapNum][i] = new OBJ_GoldCoin(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 61;
        gp.obj[mapNum][i].worldY = gp.tileSize * 61;
        i++;
        gp.obj[mapNum][i] = new OBJ_Heart(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 60;
        gp.obj[mapNum][i].worldY = gp.tileSize * 61;
        i++;
        gp.obj[mapNum][i] = new OBJ_ManaCrystal(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 59;
        gp.obj[mapNum][i].worldY = gp.tileSize * 61;
        i++;
        gp.obj[mapNum][i] = new OBJ_GoldCoin(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 58;
        gp.obj[mapNum][i].worldY = gp.tileSize * 61;
        i++;


        
    }

    public void setNPC(){
        int mapNum = 0;
        int i = 0;
        gp.npc[mapNum][i] = new NPC_OldMan(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 51;
        gp.npc[mapNum][i].worldY = gp.tileSize * 51;
        mapNum++;
        i=0;
        gp.npc[mapNum][i] = new NPC_Merchant(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 12;
        gp.npc[mapNum][i].worldY = gp.tileSize * 44;
        

       
    }

    public void setMonster(){
        int mapNum = 0;
        gp.monster[mapNum][0] = new MON_Slime(gp);
        gp.monster[mapNum][0].worldX = 60*gp.tileSize;
        gp.monster[mapNum][0].worldY = 60*gp.tileSize;

        gp.monster[mapNum][1] = new MON_Slime(gp);
        gp.monster[mapNum][1].worldX = 61*gp.tileSize;
        gp.monster[mapNum][1].worldY = 61*gp.tileSize;
        
        gp.monster[mapNum][2] = new MON_Slime(gp);
        gp.monster[mapNum][2].worldX = 63*gp.tileSize;
        gp.monster[mapNum][2].worldY = 63*gp.tileSize;

        gp.monster[mapNum][3] = new MON_Slime(gp);
        gp.monster[mapNum][3].worldX = 65*gp.tileSize;
        gp.monster[mapNum][3].worldY = 65*gp.tileSize;
        
        gp.monster[mapNum][4] = new MON_Slime(gp);
        gp.monster[mapNum][4].worldX = 66*gp.tileSize;
        gp.monster[mapNum][4].worldY = 66*gp.tileSize;
    }

    public void setInteractiveTile(){
        int i = 0;
        int mapNum = 0;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,58,48);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,58,47);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,58,46);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,26,66);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,26,67);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,26,68);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,27,68);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,27,69);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,27,70);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,27,71);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,27,72);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,26,72);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,25,72);
        i++;
        
    }

    
}
