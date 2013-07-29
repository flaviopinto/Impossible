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
	private boolean running = false;
	private Thread renderThread = null;
	private Paint paint;
	private SurfaceHolder holder;
	private int playerX = 300, playerY = 300, playerRadius = 50;
	private int enemyX, enemyY, enemyRadius = 50;
	private double distance;
	private boolean gameOver;
	private int score;

	public Impossible(Context context) {
		super(context);
		paint = new Paint();
		holder = getHolder();
	}

	@Override
	public void run() {
		while (running) {
			
			//System.out.println("Impossible!!");
			//verifica se a tela está pronta
			if(!holder.getSurface().isValid()){
				continue;
			}
			
			//bloqueia canvas
			Canvas canvas = holder.lockCanvas();
			//canvas.drawColor(Color.BLACK);
			canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.sky),0, 0, null);
			
			
			
			//desenha o player e o enemy
			drawPlayer(canvas);
			drawEnemy(canvas);
			
			//detecta colisão
			checkCollision(canvas);
			
			
			//atualiza o placar
			drawScore(canvas);
			
			//Restart e Exit
			drawButton(canvas);
			
			if(gameOver){
				stopGame(canvas);
				running = false;
			}
			
			//atualiza e libera o canvas
			holder.unlockCanvasAndPost(canvas);
			
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
		enemyRadius++;
		canvas.drawCircle(enemyX, enemyY, enemyRadius, paint);
	}
	
	/**
	 * Desenha bolinhas
	 * @param canvas
	 */
	private void drawBall(Canvas canvas){
		
	}
	
	private void checkCollision(Canvas canvas){
		distance = Math.pow(playerY - enemyY, 2) + Math.pow(playerX - enemyX, 2);
		distance = Math.sqrt(distance);
		
		if(distance <= playerRadius + enemyRadius){
			gameOver = true;
		}
	}
	
	public void resume(){
		running = true;
		renderThread = new Thread(this);
		renderThread.start();
	}
	
	public void moveDown(int pixel){
		playerY += pixel;
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
	
	public void init(){
		enemyX = enemyY = enemyRadius = 0;
		playerX = playerY = 300;
		playerRadius = 50;
		score = 0;
		gameOver = false;
	}
	
	

}
