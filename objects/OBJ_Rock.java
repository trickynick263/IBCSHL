package objects;


import java.awt.Color;

import entity.Projectile;
import main.GamePanel;

public class OBJ_Rock extends Projectile{
    GamePanel gp;
    
    public OBJ_Rock(GamePanel gp){
        super(gp);
        this.gp = gp;
        name = "Rock";
        speed = 5;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }

     public Color getParticleColor(){
        Color color = new Color(128,128,128);
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

    public void getImage(){//MANIUPLATE SIZE LATER
        up1 = setup("/projectile/rock", gp.tileSize, gp.tileSize);
        up2 = setup("/projectile/rock", gp.tileSize, gp.tileSize);
        down1 = setup("/projectile/rock", gp.tileSize, gp.tileSize);
        down2 = setup("/projectile/rock", gp.tileSize, gp.tileSize);
        left1 = setup("/projectile/rock", gp.tileSize, gp.tileSize);
        left2 = setup("/projectile/rock", gp.tileSize, gp.tileSize);
        right1 = setup("/projectile/rock", gp.tileSize, gp.tileSize);
        right2 = setup("/projectile/rock", gp.tileSize, gp.tileSize);
    }
}
