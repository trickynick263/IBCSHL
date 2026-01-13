package res.objects;

public class OBJ_Boots extends SuperObject {//a boots object that the player can pick up to increase speed
    public OBJ_Boots(){
        name = "Boots";//sets name to Boots as it is a boots object
        try{
            image = javax.imageio.ImageIO.read(new java.io.File("res/objects/boots.png"));
        }catch(java.io.IOException e){
            e.printStackTrace();
        }
    }
    
}
