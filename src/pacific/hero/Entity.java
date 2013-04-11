package pacific.hero;

import java.awt.Rectangle;
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
	int size;
	Texture tex;
	boolean friendly = true;
	public Entity(ArrayList<Entity> en, int size, CCoord windowSize, Texture t){
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
	public int getSize(){
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
		}else if(next.x+size>wS.x){
			x = vel.x-(next.x+size-wS.x);
		}if(next.y<0){
			y = vel.y-next.y;
		}else if(next.y+size>wS.y){
			y = vel.y-(next.y+size-wS.y);
		}
		return new CCoord(x,y);
	}
	public Texture getTexture(){
		return tex;
	}
	public void hit(){
		
	}
	public Rectangle getBB(){
		return new Rectangle((int)pos.x, (int)pos.y, size, size);
	}
}
