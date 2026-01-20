package main;

import entity.NPC_OldMan;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp){
        this.gp = gp;
        setObject();
    }

    public void setObject(){
        
    }

    public void setNPC(){
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize * 4;
        gp.npc[0]. worldY = gp.tileSize * 4;
    }

    
}
