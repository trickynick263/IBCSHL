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
    public int mapTileNum[][][];//2d array to store the map information from the text file
    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[50];//array of tiles, we can have 10 different tiles like grass,water,brick
        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];//initializing the 2d array with the size of the screen in tiles

        getTileImage();


        loadMap("res/maps/map.txt",0);
        loadMap("res/maps/interior.txt",1);
        //THIS NEEDS TO BE CHANGED WITH A SLASH AT HOME AND BETWEEN SCHOOL
        
        
    }

    public void getTileImage(){
    
        
        //default tile is no collsion so you dont need to set it to false
        
        //Scaling images to fit tile size, saves drawing time during the game loop
        /* 
        BufferedImage scaledImage = new BufferedImage(gp.tileSize, gp.tileSize, tile[0].image.getType());//starts as a blank canvas and you pass width and height with an image type
        Graphics2D g2d = scaledImage.createGraphics();//creates a graphics2D object to draw the image
        g2d.drawImage(tile[0].image, 0, 0, gp.tileSize, gp.tileSize, null);
        tile[0].image = scaledImage;*/
        
        
        
        
        setup(0, "grass v0", false);
        setup(1, "grass v1", false);
        setup(2, "grass v2", false);
        setup(3, "brick", true);
        setup(4, "earth", false);
        setup(5, "road v0", false);
        setup(6, "water v0", true);
        setup(7, "water v1", true);
        setup(8, "water v2", true);
        setup(9, "land to road bottom left corner v0", false);
        setup(10, "land to road bottom left corner v2", false);
        setup(11, "land to road top left corner v0", false);
        setup(12, "land to road top left corner v2", false);
        setup(13, "land to road bottom right corner v0", false);
        setup(14, "land to road bottom right corner v2", false);
        setup(15, "land to road top right corner v0", false);
        setup(16, "land to road top right corner v2", false);
        setup(17, "land to road up to down" , false);
        setup(18, "land to road down to up", false);
        setup(19, "bottom left land to water", false);
        setup(20, "bottom left land to water corner", true);
        setup(21, "bottom right land to water", false);
        setup(22, "bottom right land to water corner", true);
        setup(23, "land to road left to right", false);
        setup(24, "land to road right to left", false);
        setup(25, "tree", true);
        setup(26, "land to water down to up", true);
        setup(27, "land to water up to down", true);
        setup(28, "land to water left to right", true);
        setup(29, "land to water right to left", true);
        setup(30, "top left land to water corner", true);
        setup(31, "top right land to water corner", true);
        setup(32, "top left land to water", false);
        setup(33, "top right land to water", false);
        setup(34, "land to water left to right v1", true);
        setup(35, "land to water right to left v1", true);
        setup(36, "top right land to water corner v1", true);
        setup(37, "top left land to water corner v1", true);
        setup(38, "bottom left land to water corner v1", true);
        setup(39, "bottom right land to water corner v1", true);
        setup(40,"table",true);
        setup(41,"floorboard",false);
        setup(42,"hut",false);
       

    }
public void setup(int index, String imagePath, boolean collision){
UtilityTool uTool = new UtilityTool();


try{
    tile[index] = new Tile();
    //tile[index].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/" + imagePath + ".png")); //school pc
    tile[index].image = ImageIO.read(new File("res/tiles/" + imagePath + ".png")); //home pc
    tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
    tile[index].collision = collision;

}
catch(IOException e){
    e.printStackTrace();
}

}

    public void loadMap(String filePath, int mapNum){
        //we will load the map from a text file
        try{
            //InputStream is = getClass().getResourceAsStream(filePath); //school
            InputStream is = new FileInputStream(new File(filePath)); //home pc
            BufferedReader br = new BufferedReader(new InputStreamReader(is));//we gonna use this bufferedreader
            int col = 0;
            int row = 0;
            while(col < gp.maxWorldCol && row < gp.maxWorldRow){
                String line = br.readLine();//reads one line of the text file at a time
                while(col < gp.maxWorldCol){
                    String numbers[] = line.trim().split(" ");
                    if(numbers.length != gp.maxWorldCol){
                        System.out.println("❌ ERROR at row " + row + " → length = " + numbers.length);
                    }
                    int num = Integer.parseInt(numbers[col]);//converts the string number to an integer
                    mapTileNum[mapNum][col][row] = num;//stores the number in the 2d array at the correct position
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
    
        int worldCol = 0;
        int worldRow = 0;
        
        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){
            
            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];//gets the tile number from the 2d array
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
            }

        }

        

    }


}
