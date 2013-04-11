package pacific.hero;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.opengl.TextureLoader;

public class PatrolBoat extends Entity {
	public PatrolBoat(ArrayList<Entity> en,int size,CCoord ws) throws FileNotFoundException, IOException {
		super(en,size,ws,TextureLoader.getTexture("PNG", new FileInputStream(new File("res/enemy_01.png"))));
		friendly = false;
	}
	public PatrolBoat(ArrayList<Entity> en,int size,CCoord ws,CCoord position) throws FileNotFoundException, IOException {
		super(en,size,ws,TextureLoader.getTexture("PNG", new FileInputStream(new File("res/enemy_01.png"))));
		friendly = false;
		pos = position;
	}
	public void fire(){
	}
	public void hit(){
		System.out.println("TEST");
		entities.remove(this);
	}
}