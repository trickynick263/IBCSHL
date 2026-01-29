package objects;

import main.GamePanel;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class OBJ_Door extends SuperObject {

    GamePanel gp;

    public OBJ_Door(GamePanel gp) {
        this.gp = gp;
        name = "Door";

        try {
            //image = ImageIO.read(new File("res/objects/door.png"));
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/door.png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

