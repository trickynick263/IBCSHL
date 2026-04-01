package objects;


import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_BlueEnergyOrb extends Projectile{
    GamePanel gp;
    
    public OBJ_BlueEnergyOrb(GamePanel gp){
        super(gp);
        this.gp = gp;
        name = "Blue Orb";
        speed = 12;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage(){//MANIUPLATE SIZE LATER
        up1 = setup("/projectile/fireball up 1", gp.tileSize, gp.tileSize);
        up2 = setup("/projectile/fireball up 2", gp.tileSize, gp.tileSize);
        down1 = setup("/projectile/fireball down 1", gp.tileSize, gp.tileSize);
        down2 = setup("/projectile/fireball down 2", gp.tileSize, gp.tileSize);
        left1 = setup("/projectile/fireball left 1", gp.tileSize, gp.tileSize);
        left2 = setup("/projectile/fireball left 2", gp.tileSize, gp.tileSize);
        right1 = setup("/projectile/fireball right 1", gp.tileSize, gp.tileSize);
        right2 = setup("/projectile/fireball right 2", gp.tileSize, gp.tileSize);
    }

    public boolean hasSufficientMana(Entity user){
        boolean hasMana = false;
        if(user.mana >= useCost){
            hasMana = true;
        }
        return hasMana;
    }

    public void subtractMana(Entity user){
        user.mana -= useCost;
    }
}

