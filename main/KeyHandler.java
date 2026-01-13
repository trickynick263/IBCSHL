package main;
import java.awt.event.KeyListener;

import java.awt.event.KeyEvent;

public class KeyHandler implements KeyListener{
    
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    
    @Override
    public void keyTyped(java.awt.event.KeyEvent e) {
        //unused
        
    }

    @Override
    public void keyPressed(java.awt.event.KeyEvent e) {
        int code = e.getKeyCode();//gives a number based output from a key based input that tells which key is pressed
        
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
        
    }
    
}
