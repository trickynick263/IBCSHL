package main;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args){

        

        JFrame window = new JFrame();//creates a window of show where we can see what is going on
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//so we are able to close the window properly
        window.setResizable(false);//So we cannot resize the window
        window.setTitle("2D Adventure");//sets title of our game

        GamePanel gamepanel = new GamePanel();//creates a new GamePanel object
        window.add(gamepanel);//adds to window

        window.pack();

        window.setLocationRelativeTo(null);//not specifiying the location(i.e setting null as the parameter), sets the window at the center of the screen
        window.setVisible(true);//so we can actually see the window

        gamepanel.startGameThread();//starts the game thread so the game can start running
    }
}
