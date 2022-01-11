package com.jiukon.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.jiukon.main.Game;
import com.jiukon.world.Camera;

public class BShoot extends Entity{

	private double dx, dy;
	private double speed = 4;
	
	private int Life = 40, curlLife = 0;
	
	public BShoot(int x, int y, int height, int width, BufferedImage sprite,double dx,double dy) {
		super(x, y, height, width, sprite);
		
		this.dx = dx;
		this.dy = dy;
		
	}

	public void tick() {
		x+=dx*speed;
		y+=dy*speed;
		curlLife++;
		if(curlLife == Life) {
			
			Game.shoots.remove(this);
			
			return;
		}
		
	}
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, width, height);
	}
	
}
