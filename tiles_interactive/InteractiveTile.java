package tiles_interactive;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class InteractiveTile extends Entity{
    GamePanel gp;
    public boolean destructible = false;
    public InteractiveTile(GamePanel gp,int col,int row){
        super(gp);
        this.gp = gp;
        worldX = gp.tileSize * col;
        worldY = gp.tileSize * row;
    }
    public void update(){
        if(invincible == true){
            invincibleCounter++;
            if(invincibleCounter > 20){
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }
    public void playSE(){
    }
    public InteractiveTile getDestroyedForm(){
        InteractiveTile tile = null;
        return tile;
    }
    public boolean isCorrectItem(Entity entity){
    
    return false;
    }

    public void draw(Graphics2D g2){//by overiding the draw method from the entity class, we can get rid of things we dont like in the draw method like the half transparent effect.d
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;//copy from tilemanager draw method

        //draw object only if it is in the visible area of the screen
        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
           worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
           worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
           worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
            g2.drawImage(down1, screenX, screenY, null);   
        }
    }
    
    
}
