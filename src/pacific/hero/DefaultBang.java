package pacific.hero;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class DefaultBang extends Entity {
	int cooldown = 10;
	public DefaultBang(ArrayList<Entity> en,CCoord ws,CCoord position) throws FileNotFoundException, IOException {
		super(en,new CCoord(100,100),ws,TextureLoader.getTexture("PNG", new FileInputStream(new File("res/bang_01.png"))));
		pos = position;
	}
	public void act(){
		cooldown--;
		if(cooldown<0){
			entities.remove(this);
		}
	}

}
