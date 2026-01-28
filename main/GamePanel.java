package main;

import entity.Entity;
import entity.Player;
import objects.SuperObject;
import tile.TileManager;

import javax.swing.JPanel;
import java.awt.Dimension;//imports dimension for the screen we use to play the game
import java.awt.Graphics2D;

import java.awt.Color;

public class GamePanel extends JPanel implements Runnable{ //subclass of jpanel
    // SCREEN SETTINGS
    final int originalTileSize = 16; //16 pixels by 16 pixels tile(default size of the player character and map tiles)
    final int scale = 3;//since modern computers have much higher resolutions, 16 by 16 looks small, so we can scale the size to make it bigger
    
    public final int tileSize = originalTileSize * scale;//48 by 48 | allows us to get our actual tile size
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;//4 to 3 ratio, 16 tiles horizontally 12 tiles vertically
    public final int screenWidth = tileSize * maxScreenCol;//768 pixels horizontally
    public final int screenHeight = tileSize * maxScreenRow;//576 pixels vertically

    //World settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    



    int FPS = 60;//frames per second, limits our game fps so the rectangle doesnt update too fast
    // and basically just dissapear

 //GAME STATE
    public int gameState;
    public final int playState = 3;             //basically we tell the program about what state the game is in
    public final int pauseState = 1;            //this can result in switching keybinds that are for other uses when
                                                //in different menus
    public final int dialogueState = 2;
    public final int titleState = 0;

    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);//creates a new keyhandler object to handle keyboard inputs
    Thread gameThread;//The existence of time in the construction of 2D games basically starts with this
    //Thread is something you can start and stop but will keep you program running until something special happens
    //Like closing the program or pausing in certain cases
    public CollisionChecker cChecker = new CollisionChecker(this);
    public Player player = new Player(this,keyH);//creates a new player object
    public SuperObject obj[] = new SuperObject[10];//array to hold objects in the game, like keys, doors, etc
    //10 different objects can be stored in the array, if we need more we can increase the size of the array
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public Entity npc[] = new Entity[10];
    
   

    
    
    //SOUND
    public Sound music = new Sound();
    public Sound se = new Sound();
    

    public GamePanel(){//constructor for game panel
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));//commands to set the size of the panel 
        this.setBackground(Color.black);//background color
        this.setDoubleBuffered(true);//if set to true, drawing from this component will be done in an offscreen painting buffer
        this.addKeyListener(keyH);//adds keyhandler to gampepanel so we can listen to keys
        this.setFocusable(true);
        setupGame();
    }

    public void setupGame(){
        aSetter.setObject();
        aSetter.setNPC();
        //playMusic(0);
        
        gameState = titleState;//initializes what state the game is in
    }

    public void startGameThread(){
        gameThread = new Thread(this);//since we are in the gamepanel class the gamepanel object(i.e in the Main class)
        //will be used in here and passed through the Thread constructor
        gameThread.start();
    }
     
    @Override// gamepanel has an error when first implemented runnable, a overriding run method is needed as
    public void run() {//when thread is called, run is also called and if run does not exist, an error is thrown
        //      >GAME LOOP<
        
        
        
        double drawInterval = 1000000000/FPS;//1 second divided by fps we want gives us the time we need to wait between frames
        //we will draw the screen every 0.01666667 seconds if fps is 60
        double delta = 0;
        long lastTime = System.nanoTime();//gets the current time in nanoseconds
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null){//as long as gamethread exists the game will keep running
            
            
            // we are going to update information such as character positions
            
            // we are going to draw the screen with the updated information
            /* lets say that we press a down arrow and that should make the player character go down
            , what we first do is we update the player information and then we redraw the screen 
            according to the new player information */

            //long currentTime = System.nanoTime();//gets the current time in nanoseconds to manage updating the game
            //above shows how to get the current time and how long the system has been running for
            
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if(delta >= 1){
            
            update();
            repaint();
            delta--;
            drawCount++;//calls the paintcomponent method, confusing but its how java works
        }

        if(timer >= 1000000000){
        
            System.out.println("FPS: " + drawCount);
            drawCount = 0;
            timer = 0;  
        }
            
            
            
            
            
            
            
            
            
            
            
            
            
            //SLEEP METHOD TO CONTROL FPS
                /*
                try{
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;//converts remaining time to milliseconds
                if(remainingTime < 0){
                    remainingTime = 0;//if remaining time is less than 0, set it to 0 so we dont have negative sleep time
                }
                Thread.sleep((long) remainingTime);//sets the thread to sleep for the remaining time, ALSO only accepts milliseconds so we have to convert above
                // in the interval that is left before drawing again in the sixty fps interval
                nextDrawTime += drawInterval;//sets the next draw time to the current next draw time plus the draw interval
                drawCount++;
                } 
                catch(InterruptedException e){
                    e.printStackTrace();
                }*/

            
        }
        
    }
    public void update(){
        
        if(gameState == playState){
                                            //we will change player position in this method on key preses for KeyHandler
        player.update();                    //calls the update method from the player class
        for(int i = 0;i < npc.length;i++){
            if(npc[i] != null){
                npc[i].update();
            }
        }
        }
        if(gameState == pauseState){
            
        }
        

    }

    public void paintComponent(java.awt.Graphics g){
        //DEBUG
        
            long drawStart = 0;
        if(keyH.debugPressed == true){
            drawStart = System.nanoTime();
        }
        
        
        super.paintComponent(g);//calls the paintcomponent method of the superclass(jpanel)
        //so that we can have the default behavior of jpanel and then add our custom behavior on top of it
        Graphics2D g2 = (Graphics2D)g;//casting g to graphics2D type so we can use graphics2D methods
        //draws a white rectangle that fills the screen
        
        //TITLE SCREEN
        if(gameState == titleState){
            ui.draw(g2);
        }
        else{
        //TILE
        tileM.draw(g2);//draws the tile manager first so the tiles are in the background
        //OBJECT
        for(int i = 0; i < obj.length; i++){//draws all objects in the obj array
            if(obj[i] != null){
                obj[i].draw(g2, this);
            }
        }
        //NPC
        for(int i = 0; i < npc.length;i++){
            if(npc[i] != null){
                npc[i].draw(g2);
            }
        }
        
        
        //PLAYER
        player.draw(g2);//calls the draw method from the player class
        //java asks what the dimensions of the rectangle are so we set them in the parameters

        ui.draw(g2);

        }
        

        //DEBUG
        if(keyH.debugPressed == true){
            long drawEnd = 0;
            drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Y: " + player.worldY, 10, 350);
            g2.drawString("X: " + player.worldX, 10, 300);
            g2.drawString("Draw Time: " + passed, 10, 400);
            System.out.println("Draw Time: " + passed);
        }

        g2.dispose();//disposes if graphics context and releases system resources that it is using
        //good practice to call dispose when done with graphics context as it helps to prevent memory leaks
        
    }

    public void playMusic(int i){

        music.setFile(i);
        music.play();
        music.loop();



    }

    public void stopMusic(){
        music.stop();
    }
    public void playSE(int i){
        se.setFile(i);
        se.play();
    }

}
