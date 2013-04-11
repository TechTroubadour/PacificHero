package pacific.hero;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.opengl.TextureLoader;

public class Water extends Entity {
	double y;
	public Water(CCoord ws) throws FileNotFoundException, IOException {
		super(null,(int)(ws.x),ws,TextureLoader.getTexture("PNG", new FileInputStream(new File("res/water_02.png"))));
		vel = new CCoord(0,-7);
	}
	
	public void act(){
		pos.add(vel);
		if(pos.y<-wS.y){
			pos.y = 0;
		}
	}
}
