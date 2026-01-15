package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;




public class UI {
    GamePanel gp;
    Font arial_40, arial_80B;
    Graphics2D g2;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;

    public UI(GamePanel gp){
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        
        

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
        
        this.g2 = g2;
        g2.setFont(arial_40);
        g2.setColor(Color.white);
        if(gp.gameState == gp.playState){

        }
        if(gp.gameState == gp.pauseState){
                drawPauseScreen();
        }
        


    }

    public void drawPauseScreen(){
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int y = gp.screenHeight/2;
        

        int x = getXforCenteredText(text);
        

        g2.drawString(text, x, y);
    }

    public void drawPlayScreen(){

    }

    public int getXforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length/2;
        return x;
    }
}
