package tile;
import main.GamePanel;

import java.awt.Graphics2D;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;



public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];//2d array to store the map information from the text file

    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[10];//array of tiles, we can have 10 different tiles like grass,water,brick
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];//initializing the 2d array with the size of the screen in tiles

        getTileImage();
        loadMap("/res/maps/map.txt");
        
    }

    public void getTileImage(){
    
        try{

            tile[0] = new Tile();
            tile[0].image = tile[0].image = ImageIO.read(new File("res/tiles/grass.png"));
            //default tile is no collsion so you dont need to set it to false
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(new File("res/tiles/brick.png"));
            tile[1].collision = true;//makes brick tile collidable
            
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(new File("res/tiles/water.png"));
            tile[2].collision = true;//makes water tile collidable
            
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(new File("res/tiles/earth.png"));
            
            tile[4] = new Tile();
            tile[4].image = ImageIO.read(new File("res/tiles/tree.png"));
            tile[4].collision = true;//makes tree tile collidable  
            
            tile[5] = new Tile();
            tile[5].image = ImageIO.read(new File("res/tiles/sand.png"));

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(new File("res/tiles/log 1.png"));
            tile[6].collision = true;

            tile[7] = new Tile();
            tile[7].image = ImageIO.read(new File("res/tiles/log 2.png"));
            tile[7].collision = true;

            tile[8] = new Tile();
            tile[8].image = ImageIO.read(new File("res/tiles/log 3.png"));
            tile[8].collision = true;

            tile[9] = new Tile();
            tile[9].image = ImageIO.read(new File("res/tiles/fence.png"));
            tile[9].collision = true;




        }catch(IOException e){
            e.printStackTrace();
        }

    }
    public void loadMap(String filePath){
        //we will load the map from a text file
        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));//we gonna use this bufferedreader
            //to read the text file line by line so we can put whatever tiles on the map we want
            int col = 0;
            int row = 0;
            while(col < gp.maxWorldCol && row < gp.maxWorldRow){
                String line = br.readLine();//reads one line of the text file at a time
                while(col < gp.maxWorldCol){
                    String numbers[] = line.split(" ");//splits the line into individual numbers based on spaces
                    int num = Integer.parseInt(numbers[col]);//converts the string number to an integer
                    mapTileNum[col][row] = num;//stores the number in the 2d array at the correct position
                    col++;
                }
                if(col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
                
            }
        br.close();
            


        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2){
    
        //g2.drawImage(tile[1].image, 0, 0,gp.tileSize, gp.tileSize, null);//how we draw an image and we
        //also choose the coordinates as seen on the method parameters
        int worldCol = 0;
        int worldRow = 0;
        
        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){
            //we will use a while loop to pain the canvas as it is so much
            // more efficient and easier, this will allow us to basically automate the process
            //of drawing tiles across the screen
            
            
            int tileNum = mapTileNum[worldCol][worldRow];//gets the tile number from the 2d array
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;

            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;
            
            if(worldX> gp.player.worldX - gp.player.screenX - gp.tileSize &&
               worldX < gp.player.worldX + gp.player.screenX + gp.tileSize &&//only draws tiles that are within the screen
               worldY > gp.player.worldY - gp.player.screenY - gp.tileSize &&
               worldY < gp.player.worldY + gp.player.screenY + gp.tileSize){
            
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            }
            worldCol++;
            
            if(worldCol == gp.maxWorldCol)
            {
                worldCol = 0;
                worldRow++;
            }//We will use a txt file to let the game know which tiles to use, it should correspond 1 0 2 or others to
            //which tile we want to use at that position on the map

        }

        

    }


}
