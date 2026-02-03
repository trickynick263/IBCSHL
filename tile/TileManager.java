package tile;
import main.GamePanel;
import main.UtilityTool;

import java.awt.Graphics2D;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
//import java.io.File;
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
    
        setup(0, "grass", false);
        //default tile is no collsion so you dont need to set it to false
        
        //Scaling images to fit tile size, saves drawing time during the game loop
        /* 
        BufferedImage scaledImage = new BufferedImage(gp.tileSize, gp.tileSize, tile[0].image.getType());//starts as a blank canvas and you pass width and height with an image type
        Graphics2D g2d = scaledImage.createGraphics();//creates a graphics2D object to draw the image
        g2d.drawImage(tile[0].image, 0, 0, gp.tileSize, gp.tileSize, null);
        tile[0].image = scaledImage;*/

        
        setup(1, "brick", true);
         
        setup(2, "water", true);
         
        setup(3, "earth", false);
         
        setup(4, "tree", true);
         
        setup(5, "sand", false);

         
        setup(6, "log 1", true);
        
        setup(7, "log 2", true);
        
        setup(8, "log 3", true);

        
        setup(9, "fence", true);

    }
public void setup(int index, String imagePath, boolean collision){
UtilityTool uTool = new UtilityTool();


try{
    tile[index] = new Tile();
    tile[index].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/" + imagePath + ".png")); //school pc
    //tile[index].image = ImageIO.read(new File("res/tiles/" + imagePath + ".png")); //home pc
    tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
    tile[index].collision = collision;

}
catch(IOException e){
    e.printStackTrace();
}

}

    public void loadMap(String filePath){
        //we will load the map from a text file
        try{
            InputStream is = getClass().getResourceAsStream(filePath); //school
            //InputStream is = new FileInputStream(new File("res/maps/map.txt")); //home pc
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
            
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);

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
