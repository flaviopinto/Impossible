package br.com.flavio.impossible;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.app.Activity;

public class Game extends Activity implements OnTouchListener{

	private Impossible view;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(
//        		WindowManager.LayoutParams.FLAG_FULLSCREEN, 
//        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        view = new Impossible(this);
        view.setOnTouchListener(this);
        
        setContentView(view);
    }
    
    protected void onResume() {
		super.onResume();
		view.resume();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		view.moveDown(10);
		view.addScore(100);
		
		if(event.getX() < 100 && event.getY() > 290 && event.getY() < 310){
			view.restart();
		}
		
		if(event.getX() < 100 && event.getY() > 490 && event.getY() < 510){
			System.exit(0);
		}
		
		return true;
	}
    
    

    
}
