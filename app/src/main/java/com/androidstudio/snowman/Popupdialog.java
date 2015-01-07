package com.androidstudio.snowman;

import java.util.Random;
import java.util.Set;

import com.androidstudio.snowman.auxiliary.Card;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


public class Popupdialog extends Activity {
	
	public static String back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		// set icon for popup
		requestWindowFeature(Window.FEATURE_NO_TITLE); // enabele 1
		setContentView(R.layout.activity_popupdialog);
		// initialize my activity to the view to use in the listener
		TextView text = (TextView) findViewById(R.id.text);
		
		final Card card = getIntent().getParcelableExtra(NotiService.POPUPCARD); //(Card) bun.getParcelable(NotiService.POPUPCARD);
		// set the front card
		text.setText(card.getFront());
		
		// stop service
//		stopService(new Intent(getApplicationContext(), NotiService.class));

//		seek.stopService();
		// start the touch listener 
		text.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_DOWN){		
						// intent to switch between to next activity
					Intent resInt = new Intent(Popupdialog.this, Popupdialog2.class);
					resInt.putExtra(NotiService.POPUPCARD, card);
					finish();	// finish the first activity while flipping.	
					startActivity(resInt); // start the next activity.
					return true; // return true if activity was touched
				}
				return false;	// return false if activity is not touched.
			}
		});
	}
	
	public static String getBack(){
		return back;
	}
	
	
	@Override
	protected void onStop(){
		super.onStop();
		this.finish();
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		this.finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.popupdialog, menu);
		return true;
	}


}
