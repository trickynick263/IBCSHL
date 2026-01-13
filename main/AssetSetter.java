package main;



public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp){
        this.gp = gp;
        setObject();
    }

    public void setObject(){
        gp.obj[0] = new res.objects.OBJ_Key();
        gp.obj[0].worldX = gp.tileSize *  12;
        gp.obj[0].worldY = gp.tileSize * 13;

        gp.obj[1] = new res.objects.OBJ_Door();
        gp.obj[1].worldX = gp.tileSize * 18;
        gp.obj[1].worldY = gp.tileSize * 32;

        gp.obj[2] = new res.objects.OBJ_Chest();
        gp.obj[2].worldX = gp.tileSize * 18;
        gp.obj[2].worldY = gp.tileSize * 43;


        gp.obj[3] = new res.objects.OBJ_Boots();
        gp.obj[3].worldX = gp.tileSize * 3;
        gp.obj[3].worldY = gp.tileSize * 3;

        gp.obj[4] = new res.objects.OBJ_Door();
        gp.obj[4].worldX = gp.tileSize * 18;
        gp.obj[4].worldY = gp.tileSize * 34;

        gp.obj[5] = new res.objects.OBJ_Door();
        gp.obj[5].worldX = gp.tileSize * 18;
        gp.obj[5].worldY = gp.tileSize * 36;

        gp.obj[6] = new res.objects.OBJ_Key();
        gp.obj[6].worldX = gp.tileSize *  29;
        gp.obj[6].worldY = gp.tileSize * 14;

        gp.obj[7] = new res.objects.OBJ_Key();
        gp.obj[7].worldX = gp.tileSize *  29;
        gp.obj[7].worldY = gp.tileSize * 26;




        
    }

    
}
