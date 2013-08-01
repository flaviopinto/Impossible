package br.com.flavio.impossible;

public class Enemy {
	private int enemyX, enemyY, enemyRadius;
	private int vectX[] = {20,40,60,80,100,120,140,160,180,200,220,240,260,280,300};
	private int vectRadius[] = {2,4,6,8,10,12,14,16,18,20,22,24,26,28,30};

	public Enemy() {
		super();
		this.enemyX = vectX[(int) (Math.random() * (vectX.length -1))];
		this.enemyY = 0;
		this.enemyRadius = vectRadius[(int) (Math.random() * (vectRadius.length -1))];;
	}

	public int getEnemyX() {
		return enemyX;
	}

	public int getEnemyY() {
		return enemyY;
	}

	public int getEnemyRadius() {
		return enemyRadius;
	}
	
	public void addEnemyY(){
		this.enemyY += 5;
	}
	
	
}
