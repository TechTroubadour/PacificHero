package pacific.hero;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class DefaultBang extends Entity {
	int cooldown = 17;
	public DefaultBang(ArrayList<Entity> en,CCoord ws,CCoord position) throws FileNotFoundException, IOException {
		super(en,new CCoord(100,100),ws,TextureLoader.getTexture("PNG", new FileInputStream(new File("res/bang_01.png"))));
		pos = position;
		setFrameMaxCooldown(4);
	}
	public void act(){
		super.act();
		System.out.println(frame+"_");
		cooldown--;
		if(cooldown<0){
			entities.remove(this);
		}
	}

}
