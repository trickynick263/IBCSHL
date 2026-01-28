package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;

import java.io.IOException;
import java.io.InputStream;




public class UI {
    GamePanel gp;
    Font purisaB, pixel;
    Graphics2D g2;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue;
    public int commandNum = 0;
    public int titleScreenState = 0;// 0: the first screen 1: second screen


    public UI(GamePanel gp){
        this.gp = gp;

        /* 
        try{
            InputStream is = getClass().getResourceAsStream("/font/8514OEM.FON");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
        }catch(FontFormatException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }*/
       pixel = new Font("Bodoni MT", Font.PLAIN, 80);
        
        
        
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
        g2.setFont(pixel);
        g2.setColor(Color.white);
        if(gp.gameState == gp.titleState){
            g2.setColor(new Color(0, 0, 0));//SETS BACKROUND COLOR OF TILE SCREEN
            g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);
            
            drawTitleScreen();
        }
        if(gp.gameState == gp.playState){

        }
        if(gp.gameState == gp.pauseState){
                drawPauseScreen();
        }
        if(gp.gameState == gp.dialogueState){
            drawDialogueScreen();
        }


    }

    public void drawTitleScreen(){
        if(titleScreenState == 0){
        //TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,90F));
        String text = "The Golden Journey";
        int x;
        int y;
        x = getXforCenteredText(text);
        y = gp.tileSize * 3;

        //SHADOWDED TEXT
        g2.setColor(Color.gray);//we draw before so the main text overlaps
        g2.drawString(text,x+5,y+5);
        //MAIN COLOR
        g2.setColor(Color.WHITE);
        g2.drawString(text,x,y);

        //BLUE BOY IMAGE
        x = gp.screenWidth/2-(gp.tileSize * 2)/2;
        y += 2 * gp.tileSize;
        g2.drawImage(gp.player.down1,x,y,gp.tileSize *2,gp.tileSize * 2,null);

        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
        text = "NEW GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize*3.5;
        if(commandNum == 0){
            g2.drawString(">",x-gp.tileSize,y);
        }
        
        g2.drawString(text,x,y);
        text = "LOAD GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize*1;
        g2.drawString(text,x,y);
        if(commandNum == 1){
            g2.drawString(">",x-gp.tileSize,y);
        }

        text = "QUIT";
        x = getXforCenteredText(text);
        y += gp.tileSize*1;
        g2.drawString(text,x,y);
        if(commandNum == 2){
            g2.drawString(">",x-gp.tileSize,y);
        }
        }else if(titleScreenState == 1){
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));
            String text = "Choose Your Class!";
            int x = getXforCenteredText(text);
            int y = gp.tileSize*3;
            g2.drawString(text,x,y);
            
            text = "Berserker";
            x = getXforCenteredText(text);
            y += gp.tileSize*1;
            g2.drawString(text,x,y);
            if(commandNum == 0){
                g2.drawString(">",x-gp.tileSize,y);
            }

            text = "Mage";
            x = getXforCenteredText(text);
            y += gp.tileSize*1;
            g2.drawString(text,x,y);
            if(commandNum == 1){
                g2.drawString(">",x-gp.tileSize,y);
            }

            text = "Healer";
            x = getXforCenteredText(text);
            y += gp.tileSize*1;
            g2.drawString(text,x,y);
            if(commandNum == 2){
                g2.drawString(">",x-gp.tileSize,y);
            }

            text = "Tank";
            x = getXforCenteredText(text);
            y += gp.tileSize*1;
            g2.drawString(text,x,y);
            if(commandNum == 3){
                g2.drawString(">",x-gp.tileSize,y);
            }

            text = "Archer";
            x = getXforCenteredText(text);
            y += gp.tileSize*1;
            g2.drawString(text,x,y);
            if(commandNum == 4){
                g2.drawString(">",x-gp.tileSize,y);
            }

            text = "Back";
            x = getXforCenteredText(text);
            y += gp.tileSize*2;
            g2.drawString(text,x,y);
            if(commandNum == 5){
                g2.drawString(">",x-gp.tileSize,y);
            }
        }




    }

    public void drawDialogueScreen(){
        //WINDOW PARAMETERS
        int x = gp.tileSize * 2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize*4;

        drawSubWindow(x,y,width,height);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,28F));
        x += gp.tileSize;
        y += gp.tileSize;
        

        for(String line : currentDialogue.split("\n")){//this loop allows line breaks whenever we type \n in our program
            g2.drawString(line,x,y);
            y+=40;
        }
    }

    public void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color(0,0,0,200);//alpha value is last number and indicates transparency level
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);
        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25);



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
