package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.Line;

import entity.Entity;

import objects.OBJ_Heart;







public class UI {
    GamePanel gp;
    Font purisaB, pixel;
    Graphics2D g2;
    public boolean messageOn = false;
    
    
    public boolean gameFinished = false;
    public String currentDialogue;
    public int commandNum = 0;
    public int titleScreenState = 0;// 0: the first screen 1: second screen
    BufferedImage heart_full,heart_half,heart_blank;
    BufferedImage img_beserk, img_mage, img_archer,img_tank,img_healer;
    BufferedImage titleScreenImage;
    ArrayList<Integer> messageCounter = new ArrayList<Integer>();
    ArrayList<String> message = new ArrayList<String>();
    public int slotCol = 0;
    public int slotRow = 0;
    public UI(GamePanel gp){
        this.gp = gp;

        
       pixel = new Font("Bodoni MT", Font.PLAIN, 80);
        
        //CREATE HUD OBJECT
        Entity hrt = new OBJ_Heart(gp);
            heart_full = hrt.image;
            heart_half = hrt.image2;
            heart_blank = hrt.image3;

        /* >not working<:(
        img_beserk = setup("beserk");
        img_mage = setup("mage");
        img_archer = setup("archer");
        img_tank = setup("tank");
        img_healer = setup("healer");
        */
        
        
        try{
        titleScreenImage = ImageIO.read(getClass().getResourceAsStream("/res/playerimage/player attack right 2.png")); //school pc
        //titleScreenImage = ImageIO.read(new File("res/playerimage/player attack right 1.png")); //home pc
        }
        catch(IOException e){
            e.printStackTrace();
        }
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



    public void addMessage(String text){

        message.add(text);
        messageCounter.add(0);

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
            g2.setColor(new Color(0, 0, 0));//SETS BACKROUND COLOR OF TITLE SCREEN
            g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);
            
            drawTitleScreen();
        }
        if(gp.gameState == gp.playState){
            drawPlayerLife();
            drawMessage();
        }
        if(gp.gameState == gp.pauseState){
                drawPauseScreen();
                drawPlayerLife();
        }
        if(gp.gameState == gp.dialogueState){
            drawDialogueScreen();
            
        }
        if(gp.gameState == gp.characterState){
            drawCharacterScreen();
            drawInventory();
        }


    }

    public void drawInventory(){
        int frameX = gp.tileSize*8;
        int frameY = gp.tileSize;
        int frameWidth =  gp.tileSize* 6;
        int frameHeight = gp.tileSize * 5;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //SLOTS
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;
        //DRAW INVENTORY
        for(int i = 0; i < gp.player.inventory.size(); i ++){
            g2.drawImage(gp.player.inventory.get(i).down1,slotX,slotY,null);
            slotX += slotSize;
            if(i == 4 || i == 9 || i == 14){
                slotY += slotSize;
                slotX = slotXstart;
            }
        }

        //CURSOR
        int cursorX = slotXstart + (slotSize * slotCol);
        int cursorY = slotYstart + (slotSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;
        //DRAW CURSOR
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(cursorX,cursorY,cursorWidth,cursorHeight,10,10);


        //DESCRIPTION FRAME
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight;
        int dFrameHeight = gp.tileSize * 3;
        int dFrameWidth = frameWidth;

        drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
        //DRAW DESCRIPTION TEXT
        int textY = dFrameY + gp.tileSize;
        int textX = dFrameX + 20;
        g2.setFont(g2.getFont().deriveFont(26F));
        
        int itemIndex = getItemIndexOnSlot();
        if(itemIndex < gp.player.inventory.size()){//we have to manually split \n in our descriptions
            for(String line: gp.player.inventory.get(itemIndex).description.split("\n")){
                g2.drawString(line,textX, textY);
                textY+=32;
            }
        }

    }

    public int getItemIndexOnSlot(){
        int itemIndex = slotCol + (slotRow * 5);
        return itemIndex;
    }

    public void drawMessage(){
        int messageX = gp.tileSize;
        int messageY = gp.tileSize*4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
        for(int i = 0; i <message.size();i++){
            if(message.get(i)!=null){
                g2.setColor(Color.white);
                g2.drawString(message.get(i),messageX,messageY);
                
                int counter = messageCounter.get(i) + 1;//messageCounter ++ basically
                messageCounter.set(i, counter);//set counter to the modified version +1
                
                messageY+=50;

                if(messageCounter.get(i) > 180){
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }
    
    public void drawCharacterScreen(){
        //CREATE A FRAME
        final int frameX = gp.tileSize * 2;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = gp.tileSize * 10;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //TEXT
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 35;

        //NAMES
        g2.drawString("Level", textX, textY);
        textY += lineHeight;

        g2.drawString("Life", textX, textY);
        textY += lineHeight;

        g2.drawString("Strength", textX, textY);
        textY += lineHeight;

        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;

        g2.drawString("Attack", textX, textY);
        textY += lineHeight;

        g2.drawString("Defense", textX, textY);
        textY += lineHeight;

        g2.drawString("Exp", textX, textY);
        textY += lineHeight;

        g2.drawString("Next Level", textX, textY);
        textY += lineHeight;

        g2.drawString("Coin", textX, textY);
        textY += lineHeight + 15;

        g2.drawString("Weapon", textX, textY);
        textY += lineHeight + 20;

        g2.drawString("Shield", textX, textY);
        textY += lineHeight;


        //VALUE DISPLAY
        int tailX = (frameX + frameWidth) - 30;
        //Reset TextY
        textY = frameY + gp.tileSize;
        String value;
        
        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        
        value = String.valueOf(gp.player.life) + "/" + String.valueOf(gp.player.maxLife);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        
        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        
        value = String.valueOf(gp.player.dexterity);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        
        value = String.valueOf(gp.player.attack);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        
        value = String.valueOf(gp.player.defense);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        
        value = String.valueOf(gp.player.exp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        
        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        
        value = String.valueOf(gp.player.coin);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight + 15;
        
        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY - lineHeight, null);
        textY += lineHeight;
        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY, null);

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

        //PLAYER IMAGE
        x = gp.screenWidth/2-(gp.tileSize * 2)/2;
        y += 2 * gp.tileSize;
        g2.drawImage(titleScreenImage,(x-gp.tileSize*1),y,gp.tileSize *4,gp.tileSize * 2,null);

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

    public int getXforAlignToRightText(String text, int tailX){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }
}
