package main;
import java.awt.event.KeyListener;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;

public class KeyHandler implements KeyListener{
    
    public boolean upPressed, downPressed, leftPressed, rightPressed, debugPressed,enterPressed, shotKeyPressed;
    GamePanel gp;
    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }


    @Override
    public void keyTyped(java.awt.event.KeyEvent e) {
        //unused
        
    }

    @Override
    public void keyPressed(java.awt.event.KeyEvent e) {
        int code = e.getKeyCode();//gives a number based output from a key based input that tells which key is pressed
    //TITLE STATE
    if(gp.gameState == gp.titleState){
        titleState(code);
    }
    //PLAYSTATE
    else if(gp.gameState == gp.playState){
        playState(code);
    }
    //Pause state
    else if(gp.gameState == gp.pauseState){
        pauseState(code);
    }
    //Dialogue state
    else if(gp.gameState == gp.dialogueState){
        dialogueState(code);
    }
    //Character state
    else if(gp.gameState == gp.characterState){
        characterState(code);
    }
    //Options state
    else if(gp.gameState == gp.optionsState){
        optionsState(code);
    }

    }

    public void optionsState(int code){
        if(code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }

        int maxCommandNum = 0;
        switch(gp.ui.subState){//options substate will have different max command nums based on which substate it is in
            case 0: maxCommandNum = 5; break;
            case 1: maxCommandNum = 0; break;
            case 2: maxCommandNum = 0; break;
            case 3: maxCommandNum = 1; break;
        }

        if(code == KeyEvent.VK_W){
                gp.ui.commandNum --;
                gp.playSE(8);
                if(gp.ui.commandNum < 0){
                    gp.ui.commandNum = maxCommandNum;
                }
            }
        if(code == KeyEvent.VK_S){
                gp.ui.commandNum ++;
                gp.playSE(8);
                if(gp.ui.commandNum > maxCommandNum){
                    gp.ui.commandNum = 0;
                }        
        }
        if(code == KeyEvent.VK_A){
            if(gp.ui.subState == 0){
                if(gp.ui.commandNum == 1){
                    if(gp.music.volumeScale > 0){
                        gp.music.volumeScale--;
                        gp.music.checkVolume();
                        //playSE(SOMETHING IN THE FUTURE)
                    }
                }
                if(gp.ui.commandNum == 2){
                    if(gp.se.volumeScale > 0){
                        gp.se.volumeScale--;
                        //playSE(SOMETHING IN THE FUTURE)
                    }
                }
            }
        }
        if(code == KeyEvent.VK_D){
            if(gp.ui.subState == 0){
                if(gp.ui.commandNum == 1){
                    if(gp.music.volumeScale < 5){
                        gp.music.volumeScale++;
                        gp.music.checkVolume();
                        //playSE(SOMETHING IN THE FUTURE)
                    }
                }
                if(gp.ui.commandNum == 2){
                    if(gp.se.volumeScale < 5){
                        gp.se.volumeScale++;
                        //playSE(SOMETHING IN THE FUTURE)
                    }
                }
            }
        }
            
        
    }
    
    public void titleState(int code){
        if(gp.gameState == gp.titleState){
        if(gp.ui.titleScreenState == 0){
            if(code == KeyEvent.VK_W){
                gp.ui.commandNum--;
                gp.playSE(8);
                if(gp.ui.commandNum < 0){
                    gp.ui.commandNum = 2;
                }
            }
            if(code == KeyEvent.VK_S){
                gp.ui.commandNum +=1;
                gp.playSE(8);
                if(gp.ui.commandNum > 2){
                    gp.ui.commandNum = 0;
                }        
            }
            if(code == KeyEvent.VK_ENTER){
                if(gp.ui.commandNum == 0){
                    gp.ui.titleScreenState = 1;
                    
                    gp.ui.commandNum = 0;
                    
                }
                if(gp.ui.commandNum == 1){
                    //add later
                }
                if(gp.ui.commandNum == 2){
                    System.exit(0);
                }
            }
        }
        else if(gp.ui.titleScreenState == 1){
            if(code == KeyEvent.VK_W){
                gp.ui.commandNum --;
                gp.playSE(8);
                if(gp.ui.commandNum < 0){
                    gp.ui.commandNum = 5;
                }
            }
            if(code == KeyEvent.VK_S){
                gp.ui.commandNum +=1;
                gp.playSE(8);
                if(gp.ui.commandNum > 5){
                    gp.ui.commandNum = 0;
                }        
            }
            if(code == KeyEvent.VK_ENTER){
                if(gp.ui.commandNum == 0){
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
                    //add class specific stuff...  
                }
                if(gp.ui.commandNum == 1){
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
                    //add class specific stuff...
                    
                }
                if(gp.ui.commandNum == 2){
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
                    //add class specific stuff...
                }
                if(gp.ui.commandNum == 3){
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
                    //add class specific stuff...
                }
                if(gp.ui.commandNum == 4){
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
                    //add class specific stuff...
                }
                if(gp.ui.commandNum == 5){
                    gp.ui.titleScreenState = 0;
                    gp.ui.commandNum = 0;
                }
            }
        }
        
    }
    }

    public void playState(int code){
        if(gp.gameState == gp.playState){
        if(code == KeyEvent.VK_W){
            upPressed = true;
        }
        if(code == KeyEvent.VK_S){
            downPressed = true;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = true;
        }
        if(code == KeyEvent.VK_P){
            gp.gameState = gp.pauseState; 
        }
        if(code == KeyEvent.VK_F){
            shotKeyPressed = true;
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;; 
        }
        if(code == KeyEvent.VK_C){
            gp.gameState = gp.characterState;
        }
        if(code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.optionsState;
        }

        
        

        if(code == KeyEvent.VK_T){
            if(debugPressed == false){
            debugPressed = true;
            } 
            else if(debugPressed == true){
                debugPressed = false;
            }
        }
    }
    }

    public void pauseState(int code){
        if(code == KeyEvent.VK_P){
            gp.gameState = gp.playState;
        }
    }
    
    public void dialogueState(int code){
        if(code == KeyEvent.VK_ENTER){
            gp.gameState = gp.playState;
        }
    }

    public void characterState(int code){
        if(code == KeyEvent.VK_C){
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_W){
            if(gp.ui.slotRow <= 0){}
            else{
                gp.ui.slotRow--;
                gp.playSE(8);
            }
        }
        if(code == KeyEvent.VK_A){
            if(gp.ui.slotCol <= 0){}
            else{
                gp.ui.slotCol--;
                gp.playSE(8);
            }
            
        }
        if(code == KeyEvent.VK_S){
            if(gp.ui.slotRow >= 3){}
            else{
                gp.ui.slotRow++;
                gp.playSE(8);
            }
        }
        if(code == KeyEvent.VK_D){
            if(gp.ui.slotCol >= 4){}
            else{
                gp.ui.slotCol++;
                gp.playSE(8);
            }
        }
        if(code == KeyEvent.VK_ENTER){
            gp.player.selectItem();
        }
    }



    @Override
    public void keyReleased(java.awt.event.KeyEvent e) {
        int code = e.getKeyCode();
        
        if(code == KeyEvent.VK_W){
            upPressed = false;
        }
        if(code == KeyEvent.VK_S){
            downPressed = false;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed = false; 
        }
        if(code == KeyEvent.VK_F){
            shotKeyPressed = false;
        } 
        
    }
    
}
