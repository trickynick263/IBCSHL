package main;

import entity.Entity;
import entity.Player;
import environment.EnvironmentManager;
import tile.TileManager;
import tiles_interactive.InteractiveTile;



import javax.swing.JPanel;

import AI.PathFinder;

import java.awt.Dimension;//imports dimension for the screen we use to play the game
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.awt.Color;


public class GamePanel extends JPanel implements Runnable{ //subclass of jpanel
    // SCREEN SETTINGS
    final int originalTileSize = 16; //16 pixels by 16 pixels tile(default size of the player character and map tiles)
    final int scale = 3;//since modern computers have much higher resolutions, 16 by 16 looks small, so we can scale the size to make it bigger
    
    public final int tileSize = originalTileSize * scale;//48 by 48 | allows us to get our actual tile size
    public final int maxScreenCol = 20;//number of columns in the screen, 20 tiles horizontally
    public final int maxScreenRow = 12;//4 to 3 ratio, 16 tiles horizontally 12 tiles vertically
    public final int screenWidth = tileSize * maxScreenCol;//960 pixels horizontally
    public final int screenHeight = tileSize * maxScreenRow;//576 pixels vertically

    //World settings
    public final int maxWorldCol = 100;
    public final int maxWorldRow = 100;
    public final int maxMap = 10;
    public int currentMap = 0;

    public boolean atHome = false;

    //FOR FULL SCREEN
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;
    



    int FPS = 60;

 //GAME STATE
    public int gameState;
    public final int playState = 3;             
    public final int pauseState = 1;
    public final int dialogueState = 2;
    public final int titleState = 0;
    public final int characterState = 4;
    public final int optionsState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;


    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public Player player = new Player(this,keyH);
    Config config = new Config(this);

    public Entity obj[][] = new Entity[maxMap][20];
    public Entity npc[][] = new Entity[maxMap][10];
    public Entity[][] monster = new Entity[maxMap][20];
    public InteractiveTile[][] iTile = new InteractiveTile[maxMap][20];
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    ArrayList<Entity> entityList = new ArrayList<>();
    public Entity[][] projectile = new Entity[maxMap][20];
    public ArrayList<Entity> particleList = new ArrayList<>();
    public AssetSetter aSetter = new AssetSetter(this);
    public PathFinder pFinder = new PathFinder(this);
    public EnvironmentManager eManager = new EnvironmentManager(this);
    
    
    //SOUND
    public Sound music = new Sound(this);
    public Sound se = new Sound(this);
    

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        setupGame();
    }

    public void setupGame(){
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
        eManager.setup();

        gameState = titleState;//initializes what state the game is in
        this.music.volumeScale = 2;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);//creates a new buffered image that we can draw on, this is used for full screen mode to draw the game on a smaller screen and then scale it up to fit the full screen
        g2 = (Graphics2D)tempScreen.getGraphics();//creates a graphics2D object to draw on the buffered image, this is used for full screen mode to draw the game on a smaller screen and then scale it up to fit the full screen

        
    }

    public void startGameThread(){
        gameThread = new Thread(this);//since we are in the gamepanel class the gamepanel object(i.e in the Main class)
        //will be used in here and passed through the Thread constructor
        gameThread.start();
    }
     
    @Override
    public void run() {
        
        
        
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if(delta >= 1){
                update();
                drawToTempScreen();
                drawToScreen();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;  
            }
        }  
    }
    public void update(){
        
        if(gameState == playState){
                                            
        player.update();
        for(int i = 0;i < npc[1].length;i++){
            if(npc[currentMap][i] != null){
                npc[currentMap][i].update();
            }
        }
        for(int i = 0;i < monster[1].length;i++){
            if(monster[currentMap][i] != null){
                if(monster[currentMap][i].alive == true && monster[currentMap][i].dying == false){    
                    monster[currentMap][i].update();
                }
                if(monster[currentMap][i].alive == false && monster[currentMap][i].dying == false){
                    monster[currentMap][i].checkDrop();
                    monster[currentMap][i] = null;
                }
            }
        }
        for(int i = 0;i < projectile[1].length;i++){
            if(projectile[currentMap][i] != null){
                if(projectile[currentMap][i].alive == true){    
                    projectile[currentMap][i].update();
                }
                if(projectile[currentMap][i].alive == false){
                    projectile[currentMap][i] = null;
                }
            }
        }
        for(int i = 0;i < particleList.size();i++){
            if(particleList.get(i) != null){
                if(particleList.get(i).alive == true){    
                    particleList.get(i).update();
                }
                if(particleList.get(i).alive == false){
                    particleList.remove(i);
                }
            }
        }
        for(int i = 0;i < iTile[1].length;i++){
            if(iTile[currentMap][i] != null){
                iTile[currentMap][i].update();
            }
        }
        }
        if(gameState == pauseState){
            
        }
        

    }

    public void drawToScreen(){
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

    public void setFullScreen(){
        
        //GET LOCAL SCREEN DEVICE
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();//gets the local graphics environment, which is the collection of graphics devices (monitors) that are available on the system
        GraphicsDevice gd = ge.getDefaultScreenDevice();//gets the default screen device, which is the monitor we are using
        gd.setFullScreenWindow(Main.window);//sets the window to full screen mode

        //GET FULLSCREEN SIZE FOR HEIGHT AND WIDTH
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }

    public void drawToTempScreen(){
        long drawStart = 0;
        if(keyH.debugPressed == true){
            drawStart = System.nanoTime();
        }
        
        //TITLE SCREEN
        if(gameState == titleState){
            ui.draw(g2);
        }
        else{
        //TILE
        tileM.draw(g2);//draws the tile manager first so the tiles are in the background

        //INTERACTIVE TILES
        for(int i = 0;i < iTile[1].length;i++){
            if(iTile[currentMap][i] != null){
                iTile[currentMap][i].draw(g2);
            }
        }
        
        //ADDING NPCS TO ENTITY LIST
        entityList.add(player);
        
        //NPC
        for(int i = 0;i < npc[1].length;i++){
            if(npc[currentMap][i] != null){
                entityList.add(npc[currentMap][i]);
            }
        }
        //MONSTERS
        for(int i = 0;i < monster[1].length;i++){
            if(monster[currentMap][i] != null){
                entityList.add(monster[currentMap][i]);
            }
        }
        
        
        //OBJECT
        for(int i = 0;i<obj[1].length;i++){
            if(obj[currentMap][i] != null){
                entityList.add(obj[currentMap][i]);
            }
        }
        //PROJECTILES
        for(int i = 0;i<projectile[1].length;i++){
            if(projectile[currentMap][i] != null){
                entityList.add(projectile[currentMap][i]);
            }
        }
        //PARTICLES
        for(int i = 0;i<particleList.size();i++){
            if(particleList.get(i) != null){
                entityList.add(particleList.get(i));
            }
        }
        //SORTS ENTITY LIST BASED OFF OF WORLD Y VALUES
        Collections.sort(entityList, new Comparator<Entity>(){
            public int compare(Entity e1, Entity e2){
                int result = Integer.compare(e1.worldY,e2.worldY); 
                return result;
            }
        });
        //DRAW ENTITIES
        for(int i = 0; i < entityList.size();i++){
            entityList.get(i).draw(g2);
        }
        //EMPTY THE ENTITY LIST
        entityList.clear();

        //ENVIRONMENT
        eManager.draw(g2);

        //UI
        ui.draw(g2);

        }
        

        //DEBUG
        if(keyH.debugPressed == true){
            long drawEnd = 0;
            drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.setFont(new Font("Arial",Font.PLAIN, 20));
            g2.drawString("WorldY: " + player.worldY, 10, 350);
            g2.drawString("WorldX: " + player.worldX, 10, 300);
            g2.drawString("Column: " + (player.worldX+ player.solidArea.x)/(tileSize), 10, 250);
            g2.drawString("Row: " + (player.worldY+ player.solidArea.y)/(tileSize), 10, 200);
            g2.drawString("Draw Time: " + passed, 10, 400);
            System.out.println("Draw Time: " + passed);

        //DEBUG
        }
    }

    public void retry(){
        player.setDefaultPositions();
        player.restoreLifeAndMana();
        aSetter.setNPC();
        aSetter.setMonster();
        
    }

    public void restart(){
        player.setDefaultPositions();
        player.setItems();
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();



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
