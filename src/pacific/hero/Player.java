package pacific.hero;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Player extends Entity {
	int speed;
	int maxSpeed;
	int cooldown = 7;
	int currentCooldown = 0;
	public Player(ArrayList<Entity> en, CCoord ws) throws FileNotFoundException, IOException {
		super(en,new CCoord(40,64),ws,TextureLoader.getTexture("PNG", new FileInputStream(new File("res/player_01.png"))));
		speed = 10;
		maxSpeed = speed;
	}
	public void fire(){
		if(currentCooldown <= 0){
			try {
				entities.add(new DefaultPew(this));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			currentCooldown = cooldown;
		}
	}
	public void act(){
		super.act();
		currentCooldown--;
	}
	public void tempSlow(){
		speed = maxSpeed/2;
	}
	public void maxSpeed(){
		speed = maxSpeed;
	}
	public void move(int dir){
		switch(dir){
		case UP:
			vel.add(new CCoord(0,speed));
			break;
		case DOWN:
			vel.add(new CCoord(0,-speed));
			break;
		case LEFT:
			vel.add(new CCoord(-speed,0));
			break;
		case RIGHT:
			vel.add(new CCoord(speed,0));
			break;
		}
	}
}
