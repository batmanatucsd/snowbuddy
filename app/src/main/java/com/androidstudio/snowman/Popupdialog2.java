package com.androidstudio.snowman;

import com.androidstudio.snowman.auxiliary.Card;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class Popupdialog2 extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		//		requestWindowFeature(Window.FEATURE_LEFT_ICON);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_popupdialog2);
		//		getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.sbbox);

		TextView text = (TextView) findViewById(R.id.text2); // initialize view
		
		Card card = getIntent().getParcelableExtra(NotiService.POPUPCARD);
		// set the back card
		text.setText(card.getBack());
		
		overridePendingTransition(R.anim.popupfront, R.anim.popupback);	// do the flip animation.

		// start the touch listener 
		text.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				// TODO Auto-generated method stub
				// on click touch down do the ...
				if(event.getAction() == MotionEvent.ACTION_DOWN){

					// restart service at the end of finishing the popup
//					int progress = getSharedPreferences(SeekbarActivity.NOTIPREFS, Context.MODE_PRIVATE)
//							.getInt(SeekbarActivity.NOTIINT, 20);
//					Intent notiIntent = new Intent(getApplicationContext(), NotiService.class);
//					notiIntent.putExtra(SeekbarActivity.NOTIINT, progress);
//					if(progress != 0)
//						startService(notiIntent);
					finish();	// on touch exit the popup


					return true;
				}
				return false;
			}
		});
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
		getMenuInflater().inflate(R.menu.popupdialog2, menu);
		return true;
	}
}
