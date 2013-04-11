package pacific.hero;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public abstract class Entity{
	static final int RIGHT = 0;
	static final int UP = 1;
	static final int DOWN = 2;
	static final int LEFT = 3;
	
	ArrayList<Entity> entities;
	CCoord pos;
	CCoord vel;
	CCoord wS; // Window Size
	CCoord size;
	Texture tex;
	boolean friendly = true;
	public Entity(ArrayList<Entity> en, CCoord size, CCoord windowSize, Texture t){
		entities = en;
		pos = new CCoord(0,0);
		vel = new CCoord(0,0);
		this.size = size;
		wS = windowSize;
		tex = t;
	}
	public void setPos(CCoord p){
		pos = p;
	}
	public CCoord getPos(){
		return pos;
	}
	public double getLongestSide(){
		return size.getLargest();
	}
	public CCoord getSize() {
		return size;
	}
	public void reset(){
		vel = new CCoord(0,0);
	}
	public void act(){
		CCoord next = pos.copy();
		next.add(vel);
		vel = fixVelocity(next);
		pos.add(vel);
	}
	public CCoord fixVelocity(CCoord next){
		double x = vel.x;
		double y = vel.y;
		if(next.x<0){
			x = vel.x-next.x;
		}else if(next.x+size.x>wS.x){
			x = vel.x-(next.x+size.x-wS.x);
		}if(next.y<0){
			y = vel.y-next.y;
		}else if(next.y+size.y>wS.y){
			y = vel.y-(next.y+size.y-wS.y);
		}
		return new CCoord(x,y);
	}
	public Texture getTexture(){
		return tex;
	}
	public void hit(){
		
	}
	public Rectangle2D.Double getBB(){
		return new Rectangle2D.Double(pos.x + 0.5 * size.y - 0.5 * size.x, pos.y, size.x, size.y);
	}
}
