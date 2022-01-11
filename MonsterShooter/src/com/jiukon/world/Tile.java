package com.jiukon.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.jiukon.main.Game;

public class Tile {

	public static BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(16,0,16,16);
    public static BufferedImage TILE_FLOOR2 = Game.spritesheet.getSprite(0,0,16,16);
	public static BufferedImage TILE_FLOOR3 = Game.spritesheet.getSprite(48,144,16,16);
	
	public static BufferedImage TILE_WALL = Game.spritesheet.getSprite(32,0,16,16);
	public static BufferedImage TILE_WALL2 = Game.spritesheet.getSprite(16,112,16,16);
	public static BufferedImage TILE_WALL3 = Game.spritesheet.getSprite(32,144,16,16);
	
	public BufferedImage sprite;
	public int x,y;
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite,x - Camera.x,y - Camera.y, null);
	}
	
}
