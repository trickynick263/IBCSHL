package tiles_interactive;

import main.GamePanel;
import entity.Entity;

public class IT_DryTree extends InteractiveTile{
    GamePanel gp;
    public IT_DryTree(GamePanel gp,int col,int row) {
        super(gp, col, row);
        this.gp = gp;
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
        down1 = setup("/interactive_tiles/dry tree", gp.tileSize, gp.tileSize);
        destructible = true;
        life = 3;
    }

    public boolean isCorrectItem(Entity entity){
        //check if the correct item is used on the tile
        boolean isCorrect = false;
        if(entity.currentWeapon.type == type_axe){
            isCorrect = true;
        }
        return isCorrect;
    }
    public void playSE(){
        gp.playSE(10);
    }
    public InteractiveTile getDestroyedForm(){
        InteractiveTile tile = new IT_Trunk(gp, worldX/gp.tileSize, worldY/gp.tileSize);
        return tile;
    }
}
