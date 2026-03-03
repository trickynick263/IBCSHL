package main;
import java.awt.event.KeyListener;

import java.awt.event.KeyEvent;

public class KeyHandler implements KeyListener{
    
    public boolean upPressed, downPressed, leftPressed, rightPressed, debugPressed,enterPressed;
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
        
        
    }
    
    public void titleState(int code){
        if(gp.gameState == gp.titleState){
        if(gp.ui.titleScreenState == 0){
            if(code == KeyEvent.VK_W){
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0){
                    gp.ui.commandNum = 2;
                }
            }
            if(code == KeyEvent.VK_S){
                gp.ui.commandNum +=1;
                if(gp.ui.commandNum > 2){
                    gp.ui.commandNum = 0;
                }        
            }
            if(code == KeyEvent.VK_ENTER){
                if(gp.ui.commandNum == 0){
                    gp.ui.titleScreenState = 1;
                    
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
                if(gp.ui.commandNum < 0){
                    gp.ui.commandNum = 5;
                }
            }
            if(code == KeyEvent.VK_S){
                gp.ui.commandNum +=1;
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
        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;; 
        }
        if(code == KeyEvent.VK_C){
            gp.gameState = gp.characterState;
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
        
    }
    
}
