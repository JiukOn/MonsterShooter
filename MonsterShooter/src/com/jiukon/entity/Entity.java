package com.jiukon.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.jiukon.main.Game;
import com.jiukon.world.Camera;

public class Entity {
	
	public static BufferedImage LIFE_EN = Game.spritesheet.getSprite(48, 0, 16, 16);
	
	public static BufferedImage WEAPON1_EN = Game.spritesheet.getSprite(0, 32, 16, 16);
	public static BufferedImage RIGHT_GUN1 = Game.spritesheet.getSprite(0,80,16,16);
	public static BufferedImage LEFT_GUN1 = Game.spritesheet.getSprite(16,80,16,16);
	public static BufferedImage RIGHT_GUN1Damage = Game.spritesheet.getSprite(32,80,16,16);
	public static BufferedImage LEFT_GUN1Damage = Game.spritesheet.getSprite(48,80,16,16);
	
	public static BufferedImage WEAPON2_EN = Game.spritesheet.getSprite(32, 96, 16, 16);
	public static BufferedImage RIGHT_GUN2 = Game.spritesheet.getSprite(0,96,16,16);
	public static BufferedImage LEFT_GUN2 = Game.spritesheet.getSprite(16,96,16,16);
	public static BufferedImage RIGHT_GUN2Damage = Game.spritesheet.getSprite(48,96,16,16);
	public static BufferedImage LEFT_GUN2Damage = Game.spritesheet.getSprite(0,112,16,16);
	
	public static BufferedImage WEAPON3_EN = Game.spritesheet.getSprite(16, 128, 16, 16);
	public static BufferedImage RIGHT_GUN3 = Game.spritesheet.getSprite(32,128,16,16);
	public static BufferedImage LEFT_GUN3 = Game.spritesheet.getSprite(48,128,16,16);
	public static BufferedImage RIGHT_GUN3Damage = Game.spritesheet.getSprite(0,144,16,16);
	public static BufferedImage LEFT_GUN3Damage = Game.spritesheet.getSprite(16,144,16,16);
	
	public static BufferedImage WEAPON4_EN = Game.spritesheet.getSprite(32, 176, 16, 16);
	public static BufferedImage RIGHT_GUN4 = Game.spritesheet.getSprite(48,176,16,16);
	public static BufferedImage LEFT_GUN4 = Game.spritesheet.getSprite(0,192,16,16);
	public static BufferedImage RIGHT_GUN4Damage = Game.spritesheet.getSprite(16,192,16,16);
	public static BufferedImage LEFT_GUN4Damage = Game.spritesheet.getSprite(32,192,16,16);
	
	public static BufferedImage BULLET_EN = Game.spritesheet.getSprite(16, 32, 16, 16);
	
	public static BufferedImage ENEMY1_EN = Game.spritesheet.getSprite(32, 32, 16, 16);
	public static BufferedImage ENEMY2_EN = Game.spritesheet.getSprite(32, 112, 16, 16);
	public static BufferedImage ENEMY3_EN = Game.spritesheet.getSprite(0, 144, 16, 16);
	public static BufferedImage ENEMY4_EN = Game.spritesheet.getSprite(48, 144, 16, 16);
	
	public static BufferedImage TILE_DECO = Game.spritesheet.getSprite(0,208,16,16);
	public static BufferedImage TILE_DECO2 = Game.spritesheet.getSprite(16,208,16,16);
	public static BufferedImage TILE_DECO3 = Game.spritesheet.getSprite(32,208,16,16);
	public static BufferedImage TILE_DECO4 = Game.spritesheet.getSprite(48,208,16,16);
	public static BufferedImage TILE_DECO5 = Game.spritesheet.getSprite(0,224,16,16);
	public static BufferedImage TILE_DECO6 = Game.spritesheet.getSprite(16,224,16,16);
	
	private int maskx, masky, mwidth, mheight;
	
	protected double x;
	protected double y;
	protected int height;
	protected int width;
	
	private BufferedImage sprite;
	
	public Entity(int x, int y, int height, int width,BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.sprite = sprite;
		 
		this.maskx = 0;
		this.masky = 0;
		this.mwidth = width;
		this.mheight = height;
		
	}
	
	public void setMask(int maskx,int masky, int maskw , int maskh) {
		this.maskx = maskx;
		this.masky = masky;
		this.mwidth = maskw;
		this.mheight = maskh;
		
	}
public void setX(int newX) {
	this.x =newX;
}
public void setY(int newY) {
	this.y = newY;
}
	
	public int getX(){
		return (int)this.x;
	}
	public int getY(){
		return (int)this.y;
	}
	public int getHeight(){
		return this.height;
	}
	public int getWidth(){
		return this.width;
	}
	public void tick() {
		
	}
	
	public static boolean isColliding(Entity e1, Entity e2) {
		Rectangle e1mask = new Rectangle(e1.getX()+ e1.maskx, e1.getY() + e1.masky,e1.mwidth, e1.mheight);
		Rectangle e2mask = new Rectangle(e2.getX()+ e2.maskx, e2.getY() + e2.masky,e2.mwidth, e2.mheight);
		
		return e1mask.intersects(e2mask);
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite,this.getX() - Camera.x ,this.getY() - Camera.y , null);
		
		/* teste de mascara de colisão das entities
		g.setColor(Color.blue);
		g.fillRect(this.getX() + maskx - Camera.x ,this.getY() +masky - Camera.y , mwidth, mheight);*/
	}
	
}
