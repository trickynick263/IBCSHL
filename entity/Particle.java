package entity;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;

public class Particle extends Entity{
    Entity generator;//the entity that created the particle
    Color color;
    int xd;
    int yd;
    int size;

    public Particle(GamePanel gp, Entity generator, Color color,int size, int speed, int maxLife, int xd, int yd){
        super(gp);

        this.generator = generator;
        this.size = size;
        this.speed = speed;
        this.maxLife = maxLife;
        this.color = color;
        this.xd = xd;
        this.yd = yd;
        life = maxLife;
        int offset = (gp.tileSize)/2 - size/2;//offset to make the particle appear in the center of the tile)
        worldX = generator.worldX + offset;
        worldY = generator.worldY + offset;
    }

    public void update(){
        life--;

        if(life < maxLife/3){
            yd++;//makes the particle fall down after reaching the halfway point of its life
        }
        worldX += xd * speed;
        worldY += yd * speed;
        if(life <= 0){
            alive = false;
        }
    }

    public void draw(Graphics2D g2){
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        g2.setColor(color);
        g2.fillRect(screenX, screenY, size, size);//if you want to use a sprite image, use draw image instead of fill rect and add the image as a parameter to the constructor and use that parameter here instead of color 
    }
    
}
