package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;







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
    BufferedImage heart_full,heart_half,heart_blank;
    BufferedImage img_beserk, img_mage, img_archer,img_tank,img_healer;

    public UI(GamePanel gp){
        this.gp = gp;

        
       pixel = new Font("Bodoni MT", Font.PLAIN, 80);
        
        //CREATE HUD OBJECT
        try{
            UtilityTool uTool = new UtilityTool();
            heart_full = ImageIO.read(getClass().getResourceAsStream("/res/objects/heart_full.png"));
            heart_half = ImageIO.read(getClass().getResourceAsStream("/res/objects/heart_half.png"));
            heart_blank = ImageIO.read(getClass().getResourceAsStream("/res/objects/heart_blank.png"));
            
            heart_full = uTool.scaleImage(heart_full,gp.tileSize,gp.tileSize);
            heart_half = uTool.scaleImage(heart_half,gp.tileSize,gp.tileSize);
            heart_blank = uTool.scaleImage(heart_blank,gp.tileSize,gp.tileSize);

        }catch(IOException e){
        e.printStackTrace();}
        
        /* >not working<:(
        img_beserk = setup("beserk");
        img_mage = setup("mage");
        img_archer = setup("archer");
        img_tank = setup("tank");
        img_healer = setup("healer");
        */

        
    }
    //This method is for class images
    public BufferedImage setup(String imageName){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getResourceAsStream("/res/class" + imageName + ".png")); //school pc
            //image = ImageIO.read(new File("res/class" + imageName + ".png")); //home pc
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
            
        } catch(IOException e){
            e.printStackTrace();
        }
        return image;
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
            drawPlayerLife();
        }
        if(gp.gameState == gp.pauseState){
                drawPauseScreen();
                drawPlayerLife();
        }
        if(gp.gameState == gp.dialogueState){
            drawDialogueScreen();
            
        }


    }

    public void drawPlayerLife(){
        
        
        
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;
        while(i < gp.player.maxLife/2){
            g2.drawImage(heart_blank, x , y, null);
            i++;
            x+=gp.tileSize;
        }
        //RESET
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;

        //DRAW CURRENT LIFE
        while(i<gp.player.life){
            g2.drawImage(heart_half,x,y,null);
            i++;
            if(i < gp.player.life){
                g2.drawImage(heart_full,x,y,null);
            }
            i++;
            x+=gp.tileSize;
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
            g2.drawImage(img_beserk,x-gp.tileSize,y,gp.tileSize,gp.tileSize,null);

            text = "Mage";
            x = getXforCenteredText(text);
            y += gp.tileSize*1;
            g2.drawString(text,x,y);
            if(commandNum == 1){
                g2.drawString(">",x-gp.tileSize,y);
            }
            g2.drawImage(img_mage,x-gp.tileSize/2,y,gp.tileSize,gp.tileSize,null);

            text = "Healer";
            x = getXforCenteredText(text);
            y += gp.tileSize*1;
            g2.drawString(text,x,y);
            if(commandNum == 2){
                g2.drawString(">",x-gp.tileSize,y);
            }
            g2.drawImage(img_healer,x-gp.tileSize/2,y,gp.tileSize,gp.tileSize,null);

            text = "Tank";
            x = getXforCenteredText(text);
            y += gp.tileSize*1;
            g2.drawString(text,x,y);
            if(commandNum == 3){
                g2.drawString(">",x-gp.tileSize,y);
            }
            g2.drawImage(img_tank,x-gp.tileSize/2,y,gp.tileSize,gp.tileSize,null);

            text = "Archer";
            x = getXforCenteredText(text);
            y += gp.tileSize*1;
            g2.drawString(text,x,y);
            if(commandNum == 4){
                g2.drawString(">",x-gp.tileSize,y);
            }
            g2.drawImage(img_archer,x-gp.tileSize/2,y,gp.tileSize,gp.tileSize,null);

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
