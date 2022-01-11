package com.jiukon.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.jiukon.graficos.UI;
import com.jiukon.main.Game;
import com.jiukon.world.Camera;
import com.jiukon.world.World;

public class Player extends Entity {
	
	public int right_dir = 0;
	public int left_dir = 1;
	public int dir = right_dir;
	public boolean right,left,up,down;
	public double speed = 0.6;
	public int shot = 1;
	public int shotsize = 3;
	public static int Dano =1;
	
	public double life = 100, maxlife = 100;
	public  int ammo = 0, maxammo = 204;
	
	private int frames = 0,maxFrames = 5, index = 0,maxIndex = 3;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	
	private BufferedImage rightDamage;
	private BufferedImage leftDamage;
	private int Damageframes;
	public static boolean isDamage = false;
	
	public boolean hasGun = false;
	
	public boolean hasGun2 = false;

	public boolean hasGun3 = false;

	public boolean hasGun4 = false;
	
	public boolean Shoot = false, MouseShoot = false;
	public int mx,my;

	public Player(int x, int y, int height, int width, BufferedImage sprite) {
		super(x, y, height, width, sprite);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		rightDamage = Game.spritesheet.getSprite(0, 64, 16,16);
		leftDamage =  Game.spritesheet.getSprite(16, 64, 16,16);
		
		for(int i = 0; i < 4; i++) {
		rightPlayer[i] = Game.spritesheet.getSprite(0+(i*16), 16, 16,16);
		}
		for(int i = 0; i < 4; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(0+(i*16), 48, 16,16);
			}
	}
public void tick() {
	moved = false;
	if(right && World.isfree((int)(x + speed),this.getY())) {
		moved = true;
		dir = right_dir;
		x+=speed;
	
	}else if(left && World.isfree((int)(x - speed),this.getY())) {
		moved = true;
		dir = left_dir;
		x-= speed;

	}
	if(up && World.isfree(this.getX(), (int) (y- speed))) {
		moved = true;
		y-= speed;

	}else if(down && World.isfree(this.getX(), (int) (y+ speed))) {
		moved = true;
		y+= speed;

	}
	
	//animação do player
	if(moved) {
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if(index> maxIndex) 
				index = 0;	
		}
	}	
	
	if(isDamage) {
		this.Damageframes ++;
		if(Damageframes == 8) {
			Damageframes = 0;
			isDamage = false;
		}
	}
		
	this.checkGunC();
	this.checkGun2C();
	this.checkGun3C();
	this.checkGun4C();
	
	this.GunVerify();
    this.ShotVerify();
	
	if(MouseShoot) {	
		MouseShoot = false;
		if(hasGun && ammo > 0 || hasGun2 && ammo > 0 || hasGun3 && ammo > 1 || hasGun4 && ammo > 4) {
		ammo--;
		//Criar bala e atirar!

		int px = 0,py = 10;
		double angle = 0;
		if(dir == right_dir) {
			px = 16;
			angle = Math.atan2(my - (this.getY()+py - Camera.y),mx - (this.getX()+px - Camera.x));
		}else {
			px = -6;
			angle = Math.atan2(my - (this.getY()+py - Camera.y),mx - (this.getX()+px - Camera.x));
		}
		
		double dx = Math.cos(angle);
		double dy = Math.sin(angle);
		
		BShoot shoot = new BShoot(this.getX()+px,this.getY()+py,shotsize,shotsize,null,dx,dy);
		Game.shoots.add(shoot);
		 
		 if(hasGun3) {
				BShoot shoot2 = new BShoot(this.getX() + px + 2,this.getY() + py + 10, shotsize, shotsize, null, dx, dy);
				Game.shoots.add(shoot2);
			}
		}
	}
	
	if(Shoot) {
		Shoot = false;
		if(hasGun && ammo > 0 || hasGun2 && ammo > 0 || hasGun3 && ammo > 1 || hasGun4 && ammo > 4) {
			
		ammo-=shot;
		//Criar bala e atirar
		
		int dx = 0;
		int px = 0;
		int py = 8;
		if(dir == right_dir) {
			px = 18;
			dx = 1;
		}else {
			px = -8;
			dx = -1;
		}
		
		BShoot tshoot = new BShoot(this.getX()+px,this.getY()+py,shotsize,shotsize,null,dx,0);
		Game.shoots.add(tshoot);
		 if(hasGun3) {
				BShoot tshoot2 = new BShoot(this.getX() + px + 2,this.getY() + py + 10, shotsize, shotsize, null, dx, 0);
				Game.shoots.add(tshoot2);
			}
		}
	}
	
	this.DamageVerify();
	
	this.checkLifeC();
	this.checkBulletC();
	
	if (life <= 0) {
 	//game over
		life = 0;
		if(UI.Points > UI.maxPoints) {
		   UI.maxPoints =UI.Points;
		}
		Game.gameStatus = "Game Over";
	}
updateCamera();

}

private void ShotVerify() {
	if(hasGun) {
		shot = 1;
		shotsize = 2;
	}else if(hasGun2) {
		shot = 1;
		shotsize = 3;
	}else if(hasGun3) {
			shot = 2;
			shotsize = 4;
		}else if(hasGun4) {
			shot = 5;
			shotsize = 6;
		}
}

private void DamageVerify() {
	if(hasGun) {
 Dano =1;
	}else if(hasGun2 || hasGun3){
Dano = 2;
	}else if(hasGun4){
Dano = 5;
	}
}

private void GunVerify() {
if(hasGun) {
	 hasGun2 = false;
	 hasGun3 = false;
	 hasGun4 = false;
}else if(hasGun2){
	 hasGun = false;
	 hasGun3 = false;
	 hasGun4 = false;
}else if(hasGun3){
	 hasGun = false;
	 hasGun2 = false;
	 hasGun4 = false;
}else if(hasGun4){
	 hasGun = false;
	 hasGun2 = false;
	 hasGun3 = false;
 }
}


private void updateCamera() {
	Camera.x = Camera.clamp(this.getX() - (Game.Width/2),0, World.WIDTH*16 - Game.Width) ;
	Camera.y = Camera.clamp(this.getY() - (Game.Height/2),0, World.HEIGHT*16 - Game.Height) ;
}

private void checkLifeC() {
	for(int i = 0; i < Game.entities.size(); i++) {
		Entity atual = Game.entities.get(i);
		if(atual instanceof Life) {
			if(Entity.isColliding(this, atual)) {
				int cura = 10;
				if(Game.CUR_LEVEL >= 6) {
					cura = 15;
				}
				if(Game.player.life <= Game.player.maxlife - cura) {
				life += cura;
				Game.entities.remove(atual);
				}
			}
		}
	}
}

private void checkBulletC() {
	for(int i = 0; i < Game.entities.size(); i++) {
		Entity atual = Game.entities.get(i);
		if(atual instanceof Bullet) {
			if(Entity.isColliding(this, atual)) {
				int addammo = 20;
				if(Game.player.ammo <= Game.player.maxammo - addammo) {
				ammo += addammo;
				Game.entities.remove(atual);
				}else if (Game.player.ammo >= Game.player.maxammo - addammo) {
					if(Game.player.ammo < Game.player.maxammo) {
					ammo = maxammo;
					Game.entities.remove(atual);
					}
				}
			}
		}
	}
}
private void checkGunC() {
	for(int i = 0; i < Game.entities.size(); i++) {
		Entity atual = Game.entities.get(i);
		if(atual instanceof Weapon1) {
			if(Entity.isColliding(this, atual)) {			
			 hasGun = true;;
				 
			 Game.entities.remove(atual);
				return;
			}
		}
	}
}

private void checkGun2C() {
	for(int i = 0; i < Game.entities.size(); i++) {
		Entity atual2 = Game.entities.get(i);
		if(atual2 instanceof Weapon2) {
			if(Entity.isColliding(this, atual2)) {
			hasGun2 = true;
				
			 Game.entities.remove(atual2);
			 return;
			}
		}
	}
}

private void checkGun3C() {
	for(int i = 0; i < Game.entities.size(); i++) {
		Entity atual3 = Game.entities.get(i);
		if(atual3 instanceof Weapon3) {
			if(Entity.isColliding(this, atual3)) {	
			hasGun3 = true;
				
			 Game.entities.remove(atual3);
			 return;
			}
		}
	}
}

private void checkGun4C() {
	for(int i = 0; i < Game.entities.size(); i++) {
		Entity atual4 = Game.entities.get(i);
		if(atual4 instanceof Weapon4) {
			if(Entity.isColliding(this, atual4)) {
			hasGun4 = true;
			 
			 Game.entities.remove(atual4);
			 return;
			}
		}
	}
}

public void render(Graphics g) {
	int GunDistanceX = 10;
	int GunDistanceY = 2;
	if(!isDamage) {
	  if(dir == right_dir) {
	  g.drawImage(rightPlayer[index],this.getX() - Camera.x ,this.getY() -Camera.y, null);
	   if(hasGun) {
		  // desenhar arma direita
		  g.drawImage(Entity.RIGHT_GUN1,this.getX()  - Camera.x + GunDistanceX ,this.getY()  - Camera.y + GunDistanceY, null);
	  }else if(hasGun2) {
		  g.drawImage(Entity.RIGHT_GUN2,this.getX()  - Camera.x + GunDistanceX ,this.getY()  - Camera.y + GunDistanceY, null);
	  }else if(hasGun3) {
		  g.drawImage(Entity.RIGHT_GUN3,this.getX()  - Camera.x + GunDistanceX ,this.getY()  - Camera.y + GunDistanceY, null);
	  }else if(hasGun4) {
		  g.drawImage(Entity.RIGHT_GUN4,this.getX()  - Camera.x + GunDistanceX ,this.getY()  - Camera.y + GunDistanceY, null);
	  }
	    }else if (dir == left_dir) {
		g.drawImage(leftPlayer[index],this.getX() - Camera.x  ,this.getY() - Camera.y, null);
		 if(hasGun) {
			  //desenhar arma esquerda
			  g.drawImage(Entity.LEFT_GUN1,this.getX()  - Camera.x - GunDistanceX ,this.getY()  - Camera.y + GunDistanceY, null);
		  } else if(hasGun2) {
			 g.drawImage(Entity.LEFT_GUN2,this.getX()  - Camera.x - GunDistanceX ,this.getY()  - Camera.y + GunDistanceY, null);
		 }else if(hasGun3) {
			 g.drawImage(Entity.LEFT_GUN3,this.getX()  - Camera.x - GunDistanceX ,this.getY()  - Camera.y + GunDistanceY, null);
		 }else if(hasGun4) {
			 g.drawImage(Entity.LEFT_GUN4,this.getX()  - Camera.x - GunDistanceX ,this.getY()  - Camera.y + GunDistanceY, null);
		 }
	    }
	}else {
		//Desenhar gun e player damaged
		 if(dir == right_dir) {
			  g.drawImage(rightDamage,this.getX() - Camera.x ,this.getY() -Camera.y, null);
				 if(hasGun) {
					 g.drawImage(Entity.RIGHT_GUN1Damage,this.getX()  - Camera.x + GunDistanceX ,this.getY()  - Camera.y + GunDistanceY, null);
				 }else if(hasGun2) {
					 g.drawImage(Entity.RIGHT_GUN2Damage,this.getX()  - Camera.x + GunDistanceX ,this.getY()  - Camera.y + GunDistanceY, null);
				 }else if(hasGun3) {
					 g.drawImage(Entity.RIGHT_GUN3Damage,this.getX()  - Camera.x + GunDistanceX ,this.getY()  - Camera.y + GunDistanceY, null);
				 }else if(hasGun4) {
					 g.drawImage(Entity.RIGHT_GUN4Damage,this.getX()  - Camera.x + GunDistanceX ,this.getY()  - Camera.y + GunDistanceY, null);
				 }
			    }else if (dir == left_dir) {
				g.drawImage(leftDamage,this.getX() - Camera.x ,this.getY() - Camera.y, null);
				 if(hasGun) {
					  g.drawImage(Entity.LEFT_GUN1Damage,this.getX()  - Camera.x - GunDistanceX ,this.getY()  - Camera.y + GunDistanceY, null);
				 }else if(hasGun2) {
					  g.drawImage(Entity.LEFT_GUN2Damage,this.getX()  - Camera.x - GunDistanceX ,this.getY()  - Camera.y + GunDistanceY, null);
				 }else if(hasGun3) {
					  g.drawImage(Entity.LEFT_GUN3Damage,this.getX()  - Camera.x - GunDistanceX ,this.getY()  - Camera.y + GunDistanceY, null);
				 }else if(hasGun4) {
					  g.drawImage(Entity.LEFT_GUN4Damage,this.getX()  - Camera.x - GunDistanceX ,this.getY()  - Camera.y + GunDistanceY, null);
				   }
			     }
	}
 }
}
