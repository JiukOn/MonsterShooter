package com.jiukon.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.jiukon.graficos.UI;
import com.jiukon.main.Game;
import com.jiukon.main.Sound;
import com.jiukon.world.Camera;
import com.jiukon.world.World;

public class Enemy3 extends Entity {

	private double speed = 0.3;

	private int maskx = 8 , masky = 8, maskw = 12, maskh = 12;
	
	public int Enemy3Life = 20;
	//public static int Dano = 5;
	
	private int frames = 0,maxFrames = 10, index = 0,maxIndex = 1;
	private BufferedImage[] Enemy3spr;
	
	private BufferedImage Enemy3Damagespr;
	private int DamageFrames = 10, DamageCurrent = 0;
	private boolean isDamage = false;
	
	public Enemy3(int x, int y, int height, int width, BufferedImage sprite) {
		super(x, y, height, width, sprite);
		 
		Enemy3spr = new BufferedImage [2];
		Enemy3spr [0] = Game.spritesheet.getSprite(0, 160, 16, 16);
		Enemy3spr [1] = Game.spritesheet.getSprite(16, 160, 16, 16);
		
		Enemy3Damagespr = Game.spritesheet.getSprite(32, 160, 16, 16);
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
		if(Game.rand.nextInt(100) < 90) {
			
		
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
				Game.player.life -= 5;
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
		
		if(Enemy3Life <= 0) {
			DestroySelf();
			UI.Points += 5 * Game.player.life;
			return;
		}
		
	}

	public void DestroySelf() {

		Game.enemies3.remove(this);
		Game.entities.remove(this);
	}

	public void isCollidingwithBullet() {
		for(int i = 0; i < Game.shoots.size(); i++) {
			Entity e = Game.shoots.get(i);
			if(e instanceof BShoot) {
				isDamage = true;
				Enemy3Life -= Player.Dano;
	            Game.shoots.remove(i);
				return;
				}
			}
		}
	


	public boolean isCollidingwithPlayer () {
		Rectangle enemy3Current = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(),16,16);

		return enemy3Current.intersects(player);
	}

	public boolean isColliding(int xnext, int ynext) {
		Rectangle enemy3Current = new Rectangle(xnext + maskx, ynext + masky, maskw, maskh);
		for(int i = 0; i< Game.enemies3.size(); i++) {
			Enemy3 e  = Game.enemies3.get(i);
			if (e == this) 
				continue;
			Rectangle enemy3Target = new Rectangle(e.getX() + maskx,e.getY() + masky, maskw, maskh);
			if(enemy3Current.intersects(enemy3Target)) {
				return true;
			}
		}
		return false;
		
	}
		public void render(Graphics g) {
			if(!isDamage) {
			g.drawImage(Enemy3spr[index],this.getX() - Camera.x ,this.getY() - Camera.y, null);
			}else {
				g.drawImage(Enemy3Damagespr,this.getX() - Camera.x ,this.getY() - Camera.y, null);
			}
			
			//vizualizar bloco de colisão
			/* g.setColor(Color.CYAN);
			g.fillRect(this.getX() + maskx - Camera.x , this.getY() + masky  - Camera.y , maskw, maskh );*/
		}

		
	}

