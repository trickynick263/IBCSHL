package tiles_interactive;

import main.GamePanel;

import java.awt.Color;

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
        life = 1;
    }
    //DONT FORGET TO ADD THESE METHODS TO THE SUPERCLASS(ENTITY)
    public boolean isCorrectItem(Entity entity){
        //check if the correct item is used on the tile
        boolean isCorrect = false;
        if(entity.currentWeapon.type == type_axe){
            isCorrect = true;
        }
        return isCorrect;
    }
    public void playSE(){
        gp.playSE(10);//play the sound effect for breaking a tree
    }
    public InteractiveTile getDestroyedForm(){
        InteractiveTile tile = new IT_Trunk(gp, worldX/gp.tileSize, worldY/gp.tileSize);
        return tile;//after the tree is destroyed, it becomes a trunk
    }

    public Color getParticleColor(){
        Color color = new Color(65,50,30);
        return color;//color of the particle
    }

    public int getParticleSize(){
        int size = 6;//size of the particle which is 6 pixels
        return size;
    }

    public int getParticleSpeed(){
        int speed = 1;//how fast the particle moves
        return speed;
    }

    public int getParticleMaxLife(){
        int maxLife = 20;
        return maxLife;//how long the particle lasts
    }
}
