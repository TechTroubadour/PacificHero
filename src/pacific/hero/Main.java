package pacific.hero;

import java.awt.geom.Rectangle2D;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Main {
	static final int RIGHT = 0;
	static final int UP = 1;
	static final int DOWN = 2;
	static final int LEFT = 3;
	
	
	int wW = 800, wH = 800;
	Water water;
	ArrayList<Entity> entities;
	Player player;
	boolean click = false;
	boolean debug = false;
	boolean[] keyDown = new boolean[Keyboard.getKeyCount()];
	CCoord wS = new CCoord(wW,wW);
	public void start()	{
		try {
			Display.setDisplayMode(new DisplayMode(wW,wH));
			Display.create();
			Display.setTitle("Pacific Hero");
		}catch(LWJGLException e){
			e.printStackTrace();
			System.exit(0);
		}
		// init OpenGL here
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
//		GL11.glOrtho(-wW/2, wW/2, -wH/2, wH/2, 1, -1);
		GL11.glOrtho(0, wW, 0, wH, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		entities = new ArrayList<Entity>();
		try {
			player = new Player(entities, wS);
			water = new Water(wS);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		while(!Display.isCloseRequested()){
			
			player.reset();
			
			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
				player.tempSlow();
			}else{
				player.maxSpeed();
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_A)){
				player.move(LEFT);
			}if(Keyboard.isKeyDown(Keyboard.KEY_D)){
				player.move(RIGHT);
			}if(Keyboard.isKeyDown(Keyboard.KEY_S)){
				player.move(DOWN);
			}if(Keyboard.isKeyDown(Keyboard.KEY_W)){
				player.move(UP);
			}if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
				player.fire();
			}if(Keyboard.isKeyDown(Keyboard.KEY_TAB)){
				water.vel.y--;
			}if(Keyboard.isKeyDown(Keyboard.KEY_CAPITAL)){
				water.vel.y++;
			}if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)&&Keyboard.isKeyDown(Keyboard.KEY_W)){
				System.exit(0);
			}if(Keyboard.isKeyDown(Keyboard.KEY_F3)){
				if(!keyDown[Keyboard.KEY_F3]) {
					keyDown[Keyboard.KEY_F3]=true;
					if(debug)
						debug = false;
					else
						debug = true;
				}
			}else{keyDown[Keyboard.KEY_F3]=false;}
			
			if(Mouse.isButtonDown(0)){
				if(!click){
					click = true;
					System.out.println("CLICK "+Mouse.getX()+" "+Mouse.getY());
					try {
						entities.add(new PatrolBoat(entities,wS,new CCoord(Mouse.getX(),Mouse.getY())));
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}else{
				click = false;
			}
		
			player.act();
			water.act();
			
			for(int i = 0; i < entities.size(); i++){
				entities.get(i).act();
			}
			
			drawGame();
			Display.update();
			Display.sync(30);
		}
		Display.destroy();
	}
	public void drawGame() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		drawWater();
		drawPlayer();
		drawEntities();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		if(debug)
			drawDebug();
	}
	public void drawWater(){
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, water.getTexture().getTextureID());
		double s = water.getLongestSide();
		CCoord p = water.getPos();
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2d(0,0);
			GL11.glVertex2d(p.x+0, p.y+s);
			GL11.glTexCoord2d(1,0);
			GL11.glVertex2d(p.x+s, p.y+s);
			GL11.glTexCoord2d(1,1);
			GL11.glVertex2d(p.x+s, p.y+0);
			GL11.glTexCoord2d(0,1);
			GL11.glVertex2d(p.x+0, p.y+0);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2d(0,0);
			GL11.glVertex2d(p.x+0, p.y+s+s);
			GL11.glTexCoord2d(1,0);
			GL11.glVertex2d(p.x+s, p.y+s+s);
			GL11.glTexCoord2d(1,1);
			GL11.glVertex2d(p.x+s, p.y+0+s);
			GL11.glTexCoord2d(0,1);
			GL11.glVertex2d(p.x+0, p.y+0+s);
		GL11.glEnd();
	}
	public void drawPlayer(){
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, player.getTexture().getTextureID());
		double s = player.getLongestSide();
		CCoord p = player.getPos();
		Rectangle2D.Double tp = player.getTexPos();
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2d(tp.x,tp.y);
			GL11.glVertex2d(p.x+0, p.y+s);
			GL11.glTexCoord2d(tp.x+tp.width,tp.y);
			GL11.glVertex2d(p.x+s, p.y+s);
			GL11.glTexCoord2d(tp.x+tp.width,tp.y+tp.height);
			GL11.glVertex2d(p.x+s, p.y+0);
			GL11.glTexCoord2d(tp.x,tp.y+tp.height);
			GL11.glVertex2d(p.x+0, p.y+0);
		GL11.glEnd();
	}
	public void drawEntities(){
		for(int i = 0; i < entities.size(); i++){
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, entities.get(i).getTexture().getTextureID());
			double s = entities.get(i).getLongestSide();
			CCoord p = entities.get(i).getPos();
			Rectangle2D.Double tp = entities.get(i).getTexPos();
			GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2d(tp.x,tp.y);
				GL11.glVertex2d(p.x+0, p.y+s);
				GL11.glTexCoord2d(tp.x+tp.width,tp.y);
				GL11.glVertex2d(p.x+s, p.y+s);
				GL11.glTexCoord2d(tp.x+tp.width,tp.y+tp.height);
				GL11.glVertex2d(p.x+s, p.y+0);
				GL11.glTexCoord2d(tp.x,tp.y+tp.height);
				GL11.glVertex2d(p.x+0, p.y+0);
			GL11.glEnd();
		}
	}
	public void drawDebug(){
		drawHitBoxes();
	}
	public void drawHitBoxes(){
		double s = player.getLongestSide();
		Rectangle2D.Double p = player.getBB();
		GL11.glColor3f(1f, 1f, 1f);
		GL11.glBegin(GL11.GL_LINE_STRIP);
			GL11.glVertex2d(p.x+0, p.y+p.height);
			GL11.glVertex2d(p.x+p.width, p.y+p.height);
			GL11.glVertex2d(p.x+p.width, p.y+0);
			GL11.glVertex2d(p.x+0, p.y+0);
			GL11.glVertex2d(p.x+0, p.y+p.height);
		GL11.glEnd();
		for(int i = 0; i < entities.size(); i++){
			s = entities.get(i).getLongestSide();
			p = entities.get(i).getBB();
			GL11.glColor3f(1f, 1f, 1f);
			GL11.glBegin(GL11.GL_LINE_STRIP);
				GL11.glVertex2d(p.x+0, p.y+p.height);
				GL11.glVertex2d(p.x+p.width, p.y+p.height);
				GL11.glVertex2d(p.x+p.width, p.y+0);
				GL11.glVertex2d(p.x+0, p.y+0);
				GL11.glVertex2d(p.x+0, p.y+p.height);
			GL11.glEnd();
		}
	}
	public static void main(String[] args){
		Main display = new Main();
		display.start();
	}
}
