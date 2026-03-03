package objects;
import entity.Entity;
import main.GamePanel;
//"/res/objects/heart_full.png"
///res/objects/heart_half.png"
//"/res/objects/heart_blank.png"
public class OBJ_Heart extends Entity{

    GamePanel gp;

    public OBJ_Heart(GamePanel gp) {
        super(gp);
        name = "Heart";

        image = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
        image2 = setup("/objects/heart_half", gp.tileSize, gp.tileSize);
        image3 = setup("/objects/heart_blank", gp.tileSize, gp.tileSize);
        description =  "["+ name+ "]\n" ;
    }
    
}
