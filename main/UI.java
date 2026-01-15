package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import res.objects.OBJ_Key;

public class UI {
    GamePanel gp;
    Font arial_40, arial_80B;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;

    public UI(GamePanel gp){
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        OBJ_Key key = new OBJ_Key(gp);
        keyImage = key.image;

        arial_80B = new Font("Arial", Font.BOLD, 80);
        
        
    }

    public void showMessage(String text){

        message = text;
        messageOn = true;

        


    }

    public void draw(Graphics2D g2){
        //we will draw on the screen how many keys the player has
        //g2.setFont(new Font("Arial", Font.PLAIN, 40));// bad example because we create a new
        //instantiation on font 60 times every second, which takes lots of time
        //                   what font  /  what type, (bold,italics) / size
        
        if(gameFinished == true){
            
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            
            String text;
            int textLength;
            int x;
            int y;
            
            text = "You found the treasure";
            textLength = (int) g2.getFontMetrics().getStringBounds(text,g2).getWidth();//returns length of text
            


             x = gp.screenWidth/2 - textLength/2;//just putting the text alone like this will cause
            //it to be displayed to the right of the screen so that means that we need to move the
            //text by half its length and half its width up
            y = gp.screenHeight/2 - (gp.tileSize*3);//we dont want to hide the player in the center so we will decrease
            //y to not block the player
            g2.drawString(text,x,y);

            
            g2.setFont(arial_80B);
            g2.setColor(Color.yellow);

            text = "Congratulations!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text,g2).getWidth();//returns length of text
            


             x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + (gp.tileSize*2);

            g2.drawString(text,x,y);

            gp.gameThread = null;



        }else{
            g2.setFont(arial_40);
        g2.setColor(Color.white);
        
        ////////////////GENERIC DISPLAY WITH TEXT//////////////////////
        /* 
        g2.drawString("Key = " + gp.player.hasKey, 10 ,50 );
        if(gp.player.hasBoots == true){
            g2.drawString("Player has boots!",25,100);
        }
            */
        //we will display images here, so we must declare a buffered image
        g2.drawImage(keyImage, gp.tileSize/2,gp.tileSize/2, gp.tileSize,gp.tileSize, null);
        g2.drawString("x " + gp.player.hasKey, 74 , 65 );

        if(messageOn == true){
            g2.setFont(g2.getFont().deriveFont(30F));//to change an already existing font
            g2.drawString(message, gp.tileSize/2,gp.tileSize * 5);

            messageCounter++;
            if(messageCounter > 300){//since we run at 60 fps, after 5 seconds, message will dissapear
                //as we will reset message counter and put message on equal to false
                messageCounter = 0;
                messageOn = false;
            }
        }
        }
        
        
        
        


    }
}
