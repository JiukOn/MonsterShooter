package com.jiukon.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.jiukon.graficos.UI;
import com.jiukon.main.Game;
import com.jiukon.main.Sound;
import com.jiukon.world.Camera;
import com.jiukon.world.World;

public class Enemy1 extends Entity{
	
	private double speed = 0.4;

	private int maskx = 8 , masky = 8, maskw = 10, maskh = 10;
	
	public int Enemy1Life = 2;
	//public static int Dano = 1;
	
	private int frames = 0,maxFrames = 10, index = 0,maxIndex = 1;
	private BufferedImage[] Enemy1spr;
	
	private BufferedImage Enemy1Damagespr;
	private int DamageFrames= 10, DamageCurrent = 0;
	private boolean isDamage = false;
	
	public Enemy1(int x, int y, int height, int width, BufferedImage sprite) {
		super(x, y, height, width, null);
		
		Enemy1spr = new BufferedImage [2];
		Enemy1spr [0] = Game.spritesheet.getSprite(32,32, 16 , 16);
		Enemy1spr [1] = Game.spritesheet.getSprite(48,32, 16 , 16);
		
		Enemy1Damagespr = Game.spritesheet.getSprite(32,64, 16 , 16);
	}
public void tick() {
	
	/*
variaveis do bloco de colisão
maskx = 5 ;
masky = 5;
maskw =8;
maskh = 8;
	*/
	
	if(isCollidingwithPlayer() == false) {
	if(Game.rand.nextInt(100) < 80) {
		
	
	if((int)x < Game.player.getX()  && World.isfree((int)(x+speed), this.getY()) && !isColliding((int)(x+speed), this.getY())) {
		x += speed;
	}else if((int)x > Game.player.getX()  && World.isfree((int)(x-speed), this.getY()) && !isColliding ((int)(x-speed), this.getY())) {
		x -= speed;
	}
	if((int)y < Game.player.getY() && World.isfree(this.getX(), (int)(y + speed)) && !isColliding (this.getX(), (int)(y + speed))) {
		y += speed;
	}else if((int)y > Game.player.getY() && World.isfree(this.getX(), (int)(y - speed)) && !isColliding(this.getX(), (int)(y - speed))) {
		y -= speed;
	 }
	}
	
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if(index> maxIndex) 
				index = 0;	
			}
	}else {
		if(Game.rand.nextInt(100) < 18) {
			     Sound.HurtSound.play();
			Game.player.life --;
		Player.isDamage = true;
		}
	}
	
	
	isCollidingwithBullet();
	
	if(isDamage) {
		DamageCurrent ++;
		if(DamageCurrent >= DamageFrames) {
			DamageCurrent = 0;
			isDamage = false;
		}
	}
		
	if(Enemy1Life <= 0) {
		DestroySelf();
		UI.Points += 1 * Game.player.life;
		return;
	}
	
}

public void DestroySelf() {

	Game.enemies.remove(this);
	Game.entities.remove(this);
}

public void isCollidingwithBullet() {
	for(int i = 0; i < Game.shoots.size(); i++) {
		Entity e = Game.shoots.get(i);
		if(e instanceof BShoot) {
			if(Entity.isColliding(this , e)) {
				Sound.EnemyHurtSound.play();
				isDamage = true;
				Enemy1Life -= Player.Dano;
	            Game.shoots.remove(i);
				return;
			}
		}
	}
}


public boolean isCollidingwithPlayer () {
	Rectangle enemy1Current = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
	Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(),16,16);

	return enemy1Current.intersects(player);
}

public boolean isColliding(int xnext, int ynext) {
	Rectangle enemy1Current = new Rectangle(xnext + maskx, ynext + masky, maskw, maskh);
	for(int i = 0; i< Game.enemies.size(); i++) {
		Enemy1 e  = Game.enemies.get(i);
		if (e == this) 
			continue;
		Rectangle enemy1Target = new Rectangle(e.getX() + maskx,e.getY() + masky, maskw, maskh);
		if(enemy1Current.intersects(enemy1Target)) {
			return true;
		}
	}
	return false;
	
}
	public void render(Graphics g) {
		if(!isDamage) {
		g.drawImage(Enemy1spr[index],this.getX() - Camera.x ,this.getY() - Camera.y, null);
		}else {
			g.drawImage(Enemy1Damagespr,this.getX() - Camera.x ,this.getY() - Camera.y, null);
		}
		
		//vizualizar bloco de colisão
		/* g.setColor(Color.CYAN);
		g.fillRect(this.getX() + maskx - Camera.x , this.getY() + masky  - Camera.y , maskw, maskh );*/
	}

	
}
