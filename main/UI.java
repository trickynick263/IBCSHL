package main;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import entity.Entity;
import objects.OBJ_Boots;
import objects.OBJ_GoldCoin;
import objects.OBJ_Heart;
/* 
import java.awt.FontFormatException;
import java.io.File;
import java.io.InputStream;
import javax.sound.sampled.Line;*/
import objects.OBJ_ManaCrystal;


public class UI {
    GamePanel gp;
    Font purisaB, pixel;
    Graphics2D g2;
    public boolean messageOn = false;
    
    
    public boolean gameFinished = false;
    public String currentDialogue;
    public int commandNum = 0;
    public int titleScreenState = 0;// 0: the first screen 1: second screen
    BufferedImage heart_full,heart_half,heart_blank, crystal_full, crystal_blank;
    BufferedImage img_beserk, img_mage, img_archer,img_tank,img_healer;
    BufferedImage titleScreenImage;
    BufferedImage coin;
    ArrayList<Integer> messageCounter = new ArrayList<Integer>();
    ArrayList<String> message = new ArrayList<String>();
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int subState = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;
    int counter = 0;
    public Entity npc;

    public UI(GamePanel gp){
        this.gp = gp;

        
       pixel = new Font("Bodoni MT", Font.PLAIN, 80);
        
        //CREATE HUD OBJECT
        Entity hrt = new OBJ_Heart(gp);
            heart_full = hrt.image;
            heart_half = hrt.image2;
            heart_blank = hrt.image3;

        Entity crs = new OBJ_ManaCrystal(gp);
            crystal_full = crs.image;
            crystal_blank = crs.image2;

        
        Entity c = new OBJ_GoldCoin(gp);
            coin = c.down1;
        
        
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
            if(gp.player.mana != gp.player.maxMana){
                drawManaLoading();
            }
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
            drawInventory(gp.player, true);
        }
        if(gp.gameState == gp.optionsState){
            drawOptionsScreen();
        }
        if(gp.gameState == gp.gameOverState){
            drawDeathScreen();
        }
        if(gp.gameState == gp.transitionState){
            drawTransition();
        }
        if(gp.gameState == gp.tradeState){
            drawTradeScreen();
        }


    }

    public void drawTradeScreen(){
        switch(subState){
            case 0: trade_select(); break;
            case 1: trade_buy(); break;
            case 2: trade_sell(); break;
        }
        gp.keyH.enterPressed = false;
    }


    public void trade_select(){

        drawDialogueScreen();
        //Draw Window

        
        int x = gp.tileSize * 15;
        int y = gp.tileSize * 4;
        int width = gp.tileSize * 3;
        int height = (int)(gp.tileSize * 3.5);
        drawSubWindow(x,y,width,height);

        //Draw Texts
        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString("Buy", x, y);
        if(commandNum == 0){
            g2.drawString(">", x-gp.tileSize/2, y);
            if(gp.keyH.enterPressed == true){
                subState = 1;
            }
        }
        y += gp.tileSize;
        g2.drawString("Sell", x, y);
        if(commandNum == 1){
            g2.drawString(">", x-gp.tileSize/2, y);
            if(gp.keyH.enterPressed == true){
                subState = 2;
            }
        }
        y += gp.tileSize;
        g2.drawString("Leave", x, y);
        if(commandNum == 2){
            g2.drawString(">", x-gp.tileSize/2, y);
            if(gp.keyH.enterPressed == true){
                commandNum = 0;
                gp.gameState = gp.dialogueState;
                currentDialogue = "See you around...";
            }
        }
        y += gp.tileSize;
        


    }

    public void trade_buy(){
        //INVENTORY DRAWINGS
        drawInventory(npc,true);
        drawInventory(gp.player, false);


        //HINT WINDOW
        int x = gp.tileSize*2;
        int y = gp.tileSize*9 + 4;
        int width = gp.tileSize*6;
        int height = gp.tileSize*2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESCAPE] Back", x + 24, y + 60);
        
        //Draw player coins
        x = gp.tileSize*12;
        y = gp.tileSize*9 + 4;
        width = gp.tileSize*6;
        height = gp.tileSize*2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Your Coins: " + gp.player.coin, x + 24, y + 60);

        //Draw Price
        int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
        if(itemIndex < npc.inventory.size()){
            x = (int)(gp.tileSize*5.5);
            y = (int)(gp.tileSize*5.5);
            width = (int)(gp.tileSize*2.5);
            height = (int)(gp.tileSize*1);
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin,x+10,y+8,32,32,null);

            int price = npc.inventory.get(itemIndex).price;
            String text =  "" + price;
            x = getXforAlignToRightText(text,gp.tileSize*8-20);
            g2.drawString(text,x,y+32);

            //BUY ITEM
            if(gp.keyH.enterPressed == true){
                if(npc.inventory.get(itemIndex).price > gp.player.coin){
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "You need more coins to buy this!";
                    drawDialogueScreen();
                }
                else if(gp.player.inventory.size() == gp.player.maxInventorySize){
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "Your inventory is full!";
                }
                else{
                    gp.player.coin -= npc.inventory.get(itemIndex).price;
                    gp.player.inventory.add(npc.inventory.get(itemIndex));
                }
            }
        }
    }

    public void trade_sell(){
        //DRAW INVENTORY of PLAYER
        drawInventory(gp.player, true);
        int x;
        int y;
        int width;
        int height;

        
        //HINT WINDOW
        x = gp.tileSize;
        y = gp.tileSize*9 + 4;
        width = gp.tileSize*6;
        height = gp.tileSize*2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESCAPE] Back", x + 24, y + 60);
        
        //Draw player coins
        x = gp.tileSize*11;
        y = gp.tileSize*9;
        width = gp.tileSize*6;
        height = gp.tileSize*2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Your Coins: " + gp.player.coin, x + 24, y + 60);

        //Draw Price
        int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
        if(itemIndex < gp.player.inventory.size()){
            x = (int)(gp.tileSize*15.5);
            y = (int)(gp.tileSize*5.5);
            width = (int)(gp.tileSize*2.5);
            height = (int)(gp.tileSize*1);
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin,x+10,y+8,32,32,null);

            int price = gp.player.inventory.get(itemIndex).price/2;
            String text =  "" + price;
            x = getXforAlignToRightText(text,gp.tileSize*18-20);
            g2.drawString(text,x,y+32);

            //BUY ITEM
            if(gp.keyH.enterPressed == true){
               if(gp.player.inventory.get(itemIndex) == gp.player.currentWeapon 
               || gp.player.inventory.get(itemIndex) == gp.player.currentShield){
                    commandNum = 0;
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "You can't sell an equipped item!";
                }
                else{
                    gp.player.inventory.remove(itemIndex);
                    gp.player.coin += price;
                }
            }
    }
    }

    public void drawTransition(){
        counter++;
        g2.setColor(new Color(0,0,0,counter*5));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);
        if(counter == 50){
            counter = 0;
            gp.gameState = gp.playState;
            gp.currentMap = gp.eHandler.tempMap;
            gp.player.worldX = gp.eHandler.tempCol * gp.tileSize;
            gp.player.worldY = gp.eHandler.tempRow * gp.tileSize;
            gp.eHandler.previousEventX = gp.player.worldX;
            gp.eHandler.previousEventY = gp.player.worldY;
        }
    }

    public void drawDeathScreen(){
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,110f));

        text = "Game Over!";
        //Shadow
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gp.tileSize*4;
        g2.drawString(text,x,y);
        //Main Color
        g2.setColor(Color.white);
        g2.drawString(text,x-5,y-5);

        //Retry
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,50f));
        text = "Retry";
        x = getXforCenteredText(text);
        y += gp.tileSize*4;
        g2.setColor(Color.black);
        g2.drawString(text,x,y);
        g2.setColor(Color.white);
        g2.drawString(text,x-4,y-4);
        if(commandNum == 0){
            g2.drawString(">",x-72,y);
        }

        //Back to Title Screen
        text = "Back To Title Screen";
        x = getXforCenteredText(text);
        y += 55;
        g2.setColor(Color.black);
        g2.drawString(text,x,y);
        g2.setColor(Color.white);
        g2.drawString(text,x-4,y-4);

        if(commandNum == 1){
            g2.drawString(">",x-72,y);
        }
    }

    public void drawOptionsScreen(){
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));
        //SUBWINDOW
        int frameX = gp.tileSize*6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*8;
        int frameHeight = gp.tileSize*10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch(subState){
            case 0: options_Top(frameX, frameY); break;
            case 1: options_fullScreenNotification(frameX, frameY);  break;
            case 2: options_Controls(frameX,frameY); break;
            case 3: options_QuitGame(frameX,frameY); break;
        }
        if(titleScreenState != 0){
            titleScreenState = 0;
        }
        
        gp.keyH.enterPressed = false;
    }

    public void options_Top(int frameX, int frameY){
        int textX;
        int textY;

        //TITLE
        String text = "Options";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text,textX,textY);

        //FULL SCREEN ON/OFF
        textX = frameX + gp.tileSize;
        textY += gp.tileSize*2;
        g2.drawString("Full Screen",textX,textY);
        if(commandNum == 0){
        g2.drawString(">",textX-gp.tileSize/2,textY);
        if(gp.keyH.enterPressed == true){
            if(gp.fullScreenOn == false){
                gp.fullScreenOn = true;
            }
            else if(gp.fullScreenOn == true){
                gp.fullScreenOn = false;
            }
            subState = 1;
        }
        }

        //MUSIC
        textY += gp.tileSize;
        g2.drawString("Music",textX,textY);
        if(commandNum == 1){
            g2.drawString(">",textX-gp.tileSize/2,textY);
        }
        
        //SE
        textY += gp.tileSize;
        g2.drawString("SE",textX,textY);
        if(commandNum == 2){
            g2.drawString(">",textX-gp.tileSize/2,textY);
        }

        //CONTROLS
        textY += gp.tileSize;
        g2.drawString("Controls",textX,textY);
        if(commandNum == 3){
            g2.drawString(">",textX-gp.tileSize/2,textY);
            if(gp.keyH.enterPressed == true){
                subState = 2;
                commandNum = 0;
            }
        }


        //END GAME
        textY += gp.tileSize;
        g2.drawString("Quit Game",textX,textY);
        if(commandNum == 4){
            g2.drawString(">",textX-gp.tileSize/2,textY);
            if(gp.keyH.enterPressed == true){
                subState = 3;
                commandNum = 0;
            }
        }

        //BACK OPTION
        textY += gp.tileSize*2;
        g2.drawString("Back",textX,textY);
        if(commandNum == 5){
            g2.drawString(">",textX-gp.tileSize/2,textY);
            if(gp.keyH.enterPressed == true){
                gp.gameState = gp.playState;
                subState = 0;
                commandNum = 0;
            }
        }


        //FULL SCREEN TOGGLE
        textX = frameX + (int)(gp.tileSize*4.5);
        textY = frameY + gp.tileSize*2 + 24;
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(textX, textY, gp.tileSize/2, gp.tileSize/2);
        if(gp.fullScreenOn == true){
            g2.fillRect(textX, textY, gp.tileSize/2, gp.tileSize/2);
        }


        //MUSIC VOLUME SLIDER
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, gp.tileSize/2);
        g2.fillRect(textX, textY, 120 * gp.music.volumeScale / 5, gp.tileSize/2);

        //SE VOLUME SLIDER
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, gp.tileSize/2);
        g2.fillRect(textX, textY, 120 * gp.se.volumeScale / 5, gp.tileSize/2);

        gp.config.saveConfig();//saves our information we change in game

    }

    public void options_QuitGame(int frameX,int frameY){
        g2.setFont(g2.getFont().deriveFont(28F));
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize;
        currentDialogue = "Are you sure you want \nto quit? \n \nThis action will return \nyou to the Title Screen.";
        for(String line : currentDialogue.split("\n")){
            g2.drawString(line,textX,textY);
            textY+=40;
        }
        g2.setFont(g2.getFont().deriveFont(32F));
        //RETURN TO MAIN MENU
        String text = "Return to Title Screen";
        textX = getXforCenteredText(text);
        textY += gp.tileSize*3;
        g2.drawString(text,textX,textY);
        if(commandNum == 0){
            g2.drawString(">",textX-25,textY);
            if(gp.keyH.enterPressed == true){
                gp.gameState = gp.titleState;
                subState = 0;
                commandNum = 0;
            }
        }
        
        //KEEP PLAYING
        text = "Keep Playing";
        textX = getXforCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text,textX,textY);
        if(commandNum == 1){
            g2.drawString(">",textX-25,textY);
            if(gp.keyH.enterPressed == true){
                subState = 0;
                commandNum = 4;
                titleScreenState = 0;
                gp.music.stop();
            }
        }






    }

    public void options_Controls(int frameX, int frameY){
        g2.setFont(g2.getFont().deriveFont(28F));
        int textX;
        int textY;

        //TITLE
        String text = "Controls";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text,textX,textY);

        textX = frameX + gp.tileSize; textY += gp.tileSize;
        g2.drawString("Move",textX,textY); textY += gp.tileSize;
        g2.drawString("Confirm/Attack",textX,textY); textY += gp.tileSize;
        g2.drawString("Shoot/Cast",textX,textY); textY += gp.tileSize;
        g2.drawString("Character Screen",textX,textY); textY += gp.tileSize;
        g2.drawString("Pause",textX,textY); textY += gp.tileSize;
        g2.drawString("Options",textX,textY); textY += gp.tileSize;

        textX = frameX + (int)(gp.tileSize*5.5);
        textY = frameY + gp.tileSize*2;
        g2.drawString("WASD",textX,textY); textY += gp.tileSize;
        g2.drawString("ENTER",textX,textY); textY += gp.tileSize;
        g2.drawString("F",textX,textY); textY += gp.tileSize;
        g2.drawString("C",textX,textY); textY += gp.tileSize;
        g2.drawString("P",textX,textY); textY += gp.tileSize;
        g2.drawString("ESCAPE",textX,textY); textY += gp.tileSize;
        
        //BACK BUTTON
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize*9;
        g2.drawString("Back",textX,textY);
        if(commandNum == 0){
            g2.drawString(">",textX-gp.tileSize/2,textY);
            if(gp.keyH.enterPressed == true){
                subState = 0;
            }
        }

        
        
    }   

    public void options_fullScreenNotification(int frameX, int frameY){

        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize*3;

        currentDialogue = "The change will take \neffect after restarting\n the game \n \nPlease restart now...";
        for(String line : currentDialogue.split("\n")){
            g2.drawString(line,textX,textY);
            textY+=40;
        }

        //BACK
        textY = frameY + gp.tileSize*9;
        g2.drawString("Back",textX,textY);
        if(commandNum == 0){
            g2.drawString(">",textX-gp.tileSize/2,textY);
            if(gp.keyH.enterPressed == true){
                subState = 0;
            }
        }
    }

    public void drawManaLoading(){
        int x = gp.tileSize/2+5;
        int y = gp.tileSize*3-5;
        int width = gp.tileSize*2;
        int height = 15;
        String text = "Recharging Mana...";
        g2.setColor(Color.gray.darker().darker().darker());
        g2.fillRoundRect(x, y, width+10, height+10,7,7);
        g2.setColor(Color.lightGray);
        g2.fillRoundRect(x+5, y+5, width, height,7,7);
        g2.setColor(Color.cyan.darker());
        g2.fillRoundRect(x+5, y+5, 2*((int)(48 * gp.player.manaRegenCounter/(double)300)), height,7,7);
        g2.setColor(Color.DARK_GRAY);
        g2.setFont(g2.getFont().deriveFont(11F));
        g2.drawString(text,x+ 10,y+ 15);
        
        
    }

    public void drawInventory(Entity entity, boolean cursor){
        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol = 0;
        int slotRow = 0;

        if(entity == gp.player){
            frameX = gp.tileSize*11;
            frameY = gp.tileSize;
            frameWidth =  gp.tileSize* 6;
            frameHeight = gp.tileSize * 5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        }
        else{
            frameX = gp.tileSize*2;
            frameY = gp.tileSize;
            frameWidth =  gp.tileSize* 6;
            frameHeight = gp.tileSize * 5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //SLOTS
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;
        //DRAW INVENTORY
        for(int i = 0; i < entity.inventory.size(); i ++){
            //EQUIP CURSOR
            if(entity.inventory.get(i)  == entity.currentWeapon 
            || entity.inventory.get(i)  == entity.currentShield){
                g2.setColor(new Color(240,190,90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
                
            }
            g2.drawImage(entity.inventory.get(i).down1,slotX,slotY,null);
            slotX += slotSize;
            if(i == 4 || i == 9 || i == 14){
                slotY += slotSize;
                slotX = slotXstart;
            }
        }

        //CURSOR
        if(cursor == true){
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

        
        //DRAW DESCRIPTION TEXT
        int textY = dFrameY + gp.tileSize;
        int textX = dFrameX + 20;
        g2.setFont(g2.getFont().deriveFont(26F));
        
        int itemIndex = getItemIndexOnSlot(slotCol, slotRow);
        if(itemIndex < entity.inventory.size()){//we have to manually split \n in our descriptions
            drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
            for(String line: entity.inventory.get(itemIndex).description.split("\n")){
                g2.drawString(line,textX, textY);
                textY+=32;
            }
        }
        }
        

    }

    public int getItemIndexOnSlot(int slotCol, int slotRow){
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
        final int frameX = gp.tileSize * 3;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = gp.tileSize * 10 + 24;

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

        g2.drawString("Mana", textX, textY);
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

        value = String.valueOf(gp.player.mana) + "/" + String.valueOf(gp.player.maxMana);
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
        textY += lineHeight;

        
        
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

        //DRAW MAX MANA
        x = gp.tileSize/2;
        y = gp.tileSize*2-15;
        i = 0;
        while(i < gp.player.maxMana){
            g2.drawImage(crystal_blank, x , y, null);
            i++;
            x+=gp.tileSize/2 + 14;
        }
        //DRAW CURRENT MANA
        x = gp.tileSize/2;
        y = gp.tileSize*2-15;
        i = 0;
        while(i<gp.player.mana){
        g2.drawImage(crystal_full,x,y,null);
            i++;
            x+=gp.tileSize/2 + 14;
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
