package res.objects;

public class OBJ_Chest extends SuperObject {//a chest object that can be opened to find items inside
    public OBJ_Chest() {
    name = "Chest";//sets name to Chest as it is a chest object
    try {
        image = javax.imageio.ImageIO.read(new java.io.File("res/objects/chest.png"));
    } catch (java.io.IOException e) {
        e.printStackTrace();


    }
    }
}
