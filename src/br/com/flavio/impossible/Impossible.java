package br.com.flavio.impossible;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Impossible extends SurfaceView implements Runnable{
	private Thread renderThread = null;
	private Paint paint;
	private SurfaceHolder holder;
	private int playerX = 300, playerY = 300, playerRadius = 50;
	private int enemyX, enemyY, enemyRadius = 50;
	private double distance;
	private boolean gameOver = false;
	private int score;
	private Enemy[] vectEnemy = new Enemy[5];
	
	private int ballY1 = 0;

	public Impossible(Context context) {
		super(context);
		paint = new Paint();
		holder = getHolder();
	}

	@Override
	public void run() {
		while (gameOver) {
			
			//check screen
			if(!holder.getSurface().isValid()){
				continue;
			}
			
			processCanvas();
		}
	}
	
	/**
	 * 
	 */
	private void processCanvas(){
		
		//block canvas
		Canvas canvas = holder.lockCanvas();
		
		playGame(canvas);
		
		//update and release canvas
		holder.unlockCanvasAndPost(canvas);
	}

	/**
	 * @param canvas
	 */
	private void playGame(Canvas canvas) {
		canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.sky),0, 0, null);
		
		//paint player, enemy and balls
		drawPlayer(canvas);
		drawEnemy(canvas);
		//drawBall(canvas);
		
		//detect collision
		checkCollision(canvas);
		
		//update score
		drawScore(canvas);
		
		//Restart and Exit
		drawButton(canvas);
		
		if(gameOver){
			//stopGame(canvas);
		}
		
	}
	
	private void drawPlayer(Canvas canvas){
		paint.setColor(Color.GREEN);
//		canvas.drawCircle(playerX, playerY, playerRadius, paint);
		canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.nave ), 
				playerX - 50, playerY - 50, null);
	}
	
	private void drawEnemy(Canvas canvas){
		paint.setColor(Color.GRAY);
		//enemyRadius++;
		
		for (int i = 0; i < vectEnemy.length; i++) {
			drawEnemy(canvas, i);
		}
		
	}
	
	private void drawEnemy(Canvas canvas, int i) {
		canvas.drawCircle(vectEnemy[i].getEnemyX(), vectEnemy[i].getEnemyY(), vectEnemy[i].getEnemyRadius(), paint);
		vectEnemy[i].addEnemyY();
	}

	/**
	 * Paint ball
	 * @param canvas
	 */
	private void drawBall(Canvas canvas){
		paint.setColor(Color.RED);
		canvas.drawCircle(200, ballY1, 10, paint);
		ballY1 = ballY1 + 10;
	}
	
	private void checkCollision(Canvas canvas){
		distance = Math.pow(playerY - enemyY, 2) + Math.pow(playerX - enemyX, 2);
		distance = Math.sqrt(distance);
		
		if(distance <= playerRadius + enemyRadius){
			gameOver = true;
		}
	}
	
	public void resume(){
		addEnemy();
		gameOver = true;
		renderThread = new Thread(this);
		renderThread.start();
	}
	
	private void addEnemy() {
		for (int i = 0; i < vectEnemy.length; i++) {
			Enemy enemy = new Enemy();
			vectEnemy[i] = enemy; 
		}
	}

	public void moveDown(int pixel){
		playerY += pixel;
	}
	
	public void moveUp(int pixel){
		playerY -= pixel;
	}
	
	public void moveLeft(int pixel){
		playerX -= pixel;
	}
	
	public void moveRight(int pixel){
		playerX += pixel;
	}
	
	public int getPlayerX(){
		return playerX;
	}
	
	public int getPlayerY(){
		return playerY;
	}
	
	private void stopGame(Canvas canvas){
		paint.setStyle(Style.FILL);
		paint.setColor(Color.RED);
		paint.setTextSize(70);
		canvas.drawText("GAME OVER", 200, 200, paint);
	}
	
	public void addScore(int points){
		score += points;
	}
	
	private void drawScore(Canvas canvas){
		paint.setStyle(Style.FILL);
		paint.setColor(Color.WHITE);
		paint.setTextSize(50);
		canvas.drawText(String.valueOf(score), 50, 200, paint);
	}
	
	private void drawButton(Canvas canvas){
		paint.setStyle(Style.FILL);
		paint.setColor(Color.WHITE);
		paint.setTextSize(30);
		canvas.drawText("RESTART", 50, 300, paint);
		
		paint.setStyle(Style.FILL);
		paint.setColor(Color.WHITE);
		paint.setTextSize(30);
		canvas.drawText("EXIT", 50, 500, paint);
	}
	
	public void restart(){
		enemyX = enemyY = enemyRadius = 0;
		playerX = playerY = 300;
		playerRadius = 50;
		score = 0;
		gameOver = false;
	}
	
	

}
