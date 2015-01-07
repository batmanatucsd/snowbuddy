package com.androidstudio.snowman;

import com.androidstudio.snowman.auxiliary.Card;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;

import android.os.Build;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class AddCardActivity extends Activity {

	private String currentGroup;
	ParseObject studybuddy;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_card);

		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		// get current group name
		currentGroup = getIntent().getStringExtra(MainActivity.CURRENTGROUP);
	}
	

	public void addCard(View view){
		EditText cardBack = (EditText) findViewById(R.id.enter_back);
		EditText cardFront = (EditText) findViewById(R.id.enter_front);
		
		// instantiate every time to create new class on parse.com
//		ParseObject studybuddy = new ParseObject(currentGroup);
		
		 studybuddy = new ParseObject("studybuddies");

		//add card to database and show on screen based on clicked button
		switch(view.getId()){
		case R.id.click_post:
			Card newCard = new Card(currentGroup, cardFront.getText().toString(),
					cardBack.getText().toString());
			MainActivity.cardhandler.createCard(newCard);
			MainActivity.changeInDatabase = true;
			
			// add the data to parse.com
			studybuddy.put("DECK", currentGroup);
			studybuddy.put("FRONT_PAGE", cardFront.getText().toString());
			studybuddy.put("BACK_PAGE", cardBack.getText().toString());
			studybuddy.saveInBackground();

			break;
		case R.id.click_cancel:
			break;
		}	
		finish();		
	}
	
	/*
	private class addCardtoParseServer extends AsyncTask<Void, Void, Void>{

		EditText cardBack = (EditText) findViewById(R.id.enter_back);
		EditText cardFront = (EditText) findViewById(R.id.enter_front);

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			ParseObject studybuddy = new ParseObject(currentGroup);
			// Save data to Parse.com :]
			studybuddy.put("DECK", currentGroup);
			studybuddy.put("FRONT_PAGE", cardFront.getText().toString());
			studybuddy.put("BACK_PAGE", cardBack.getText().toString());
			try {
				studybuddy.save();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}*/
	
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
//		NavUtils.navigateUpFromSameTask(this);
//		return true;
		finish();
		return true;
//		return super.onOptionsItemSelected(item);
	}


	

}
