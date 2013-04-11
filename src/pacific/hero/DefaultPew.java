package pacific.hero;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class DefaultPew extends Entity {
	public DefaultPew(Entity firedFrom) throws FileNotFoundException, IOException{
		super(firedFrom.entities,new CCoord(20,32),firedFrom.wS,TextureLoader.getTexture("PNG", new FileInputStream(new File("res/pew_01.png"))));
		pos = firedFrom.pos.copy();
		double midP = firedFrom.getSize().x/2;
		midP -= 15;
		pos.x += midP;
		vel = new CCoord(0,30);
	}
	
	public void act(){
		pos.add(vel);
		if(pos.y>wS.y){
			entities.remove(this);
		}
		Rectangle2D.Double bb = new Rectangle2D.Double(pos.x, pos.y, size.x, size.y); 
		for(int i = 0; i < entities.size(); i++){
			if(!entities.get(i).friendly&&bb.intersects(entities.get(i).getBB())){
				entities.get(i).hit();
				try {
					entities.add(new DefaultBang(entities,wS,pos));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				entities.remove(this);
				break;
			}
		}
		System.out.println("DEFAULT PEW");
	}
}
