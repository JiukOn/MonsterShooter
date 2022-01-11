package com.jiukon.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.jiukon.graficos.UI;
import com.jiukon.main.Game;
import com.jiukon.main.Sound;
import com.jiukon.world.Camera;
import com.jiukon.world.World;

public class Enemy4 extends Entity {

	private double speed = 0.6;

	private int maskx = 8 , masky = 8, maskw = 10, maskh = 10;
	
	public int Enemy4Life = 1;
	//public static int Dano = 1;
	
	private int frames = 0,maxFrames = 10, index = 0,maxIndex = 1;
	private BufferedImage[] Enemy4spr;
	
	private BufferedImage Enemy4Damagespr;
	private int DamageFrames = 10, DamageCurrent = 0;
	private boolean isDamage = false;
	
	public Enemy4(int x, int y, int height, int width, BufferedImage sprite) {
		super(x, y, height, width, sprite);
		 
		Enemy4spr = new BufferedImage [2];
		Enemy4spr [0] = Game.spritesheet.getSprite(48, 160, 16, 16);
		Enemy4spr [1] = Game.spritesheet.getSprite(0,176, 16 , 16);
		
		Enemy4Damagespr = Game.spritesheet.getSprite(16,176, 16 , 16);
	
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
		if(Game.rand.nextInt(100) < 75) {
			
		
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
		
		if(Enemy4Life <= 0) {
			DestroySelf();
			UI.Points += 1 * Game.player.life;
			return;
		}
		
	}

	public void DestroySelf() {

		Game.enemies4.remove(this);
		Game.entities.remove(this);
	}

	public void isCollidingwithBullet() {
		for(int i = 0; i < Game.shoots.size(); i++) {
			Entity e = Game.shoots.get(i);
			if(e instanceof BShoot) {
				isDamage = true;
				Enemy4Life -= Player.Dano;
	            Game.shoots.remove(i);
				return;
				}
			}
		}
	


	public boolean isCollidingwithPlayer () {
		Rectangle enemy4Current = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(),16,16);

		return enemy4Current.intersects(player);
	}

	public boolean isColliding(int xnext, int ynext) {
		Rectangle enemy4Current = new Rectangle(xnext + maskx, ynext + masky, maskw, maskh);
		for(int i = 0; i< Game.enemies4.size(); i++) {
			Enemy4 e  = Game.enemies4.get(i);
			if (e == this) 
				continue;
			Rectangle enemy4Target = new Rectangle(e.getX() + maskx,e.getY() + masky, maskw, maskh);
			if(enemy4Current.intersects(enemy4Target)) {
				return true;
			}
		}
		return false;
		
	}
		public void render(Graphics g) {
			if(!isDamage) {
			g.drawImage(Enemy4spr[index],this.getX() - Camera.x ,this.getY() - Camera.y, null);
			}else {
				g.drawImage(Enemy4Damagespr,this.getX() - Camera.x ,this.getY() - Camera.y, null);
			}
			
			//vizualizar bloco de colisão
			/* g.setColor(Color.CYAN);
			g.fillRect(this.getX() + maskx - Camera.x , this.getY() + masky  - Camera.y , maskw, maskh );*/
		}

		
	}

