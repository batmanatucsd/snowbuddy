package com.androidstudio.snowman.auxiliary;


import java.util.ArrayList;

import com.androidstudio.snowman.CardFragment;
import com.androidstudio.snowman.MainActivity;
import com.androidstudio.snowman.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GridViewAdapter extends BaseAdapter implements OnItemClickListener {

	Context context;
	ArrayList<Card> cards;
	MainActivity main;

	// Constructor
	public GridViewAdapter(Context context, MainActivity main) {
		this.context = context;
		this.main = main;
		this.cards = main.getCards();
	}
	
	@Override
	public int getCount() {
		return 2*cards.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	// generate view for gridView layout
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		TextView cardDescription;
		
		if(convertView == null) {
			cardDescription = new TextView(context);
			
			// set layout parameters for the textView
			cardDescription.setGravity(Gravity.CENTER); // align at the center
			cardDescription.setBackgroundResource(R.color.white); 
			cardDescription.setEllipsize(TruncateAt.END); // if the words are too long change to "..."
			cardDescription.setLines(4); // allow only 4 lines per card to show		
			
		} else {
			cardDescription = (TextView) convertView;
		}
				
		// from the arrayList of the cards, get front and back descriptions
		if(position%2 == 0)
			cardDescription.setText(cards.get(position/2).getFront());
		else 
			cardDescription.setText(cards.get(position/2).getBack());
		
		return cardDescription;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		ViewPager pager = main.getViewPager();
	
		CardFragment card = (CardFragment) ((PagerAdapter)pager.getAdapter()).getItem(position/2);
		
		// if the backside is chosen, show back of the card in the fragment
		if(position%2 != 0) {
			if(card.getFrontText() != null) { // if the fragment doesn't have views initialized
				card.getViewAnimator().setDisplayedChild(card.getIndexOfBack()); // display the back of the card
			}
			// set the faceOfCard as back
			card.setFaceOfCard(CardFragment.FaceOfCard.SHOWBACK);
			
		} else {
			if(card.getFrontText() != null) { // if the fragment doesn't have views initialized
				card.getViewAnimator().setDisplayedChild(card.getIndexOfFront()); // display the front of the card
			}
			// set the faceOfCard as front
			card.setFaceOfCard(CardFragment.FaceOfCard.SHOWFRONT); 
		}

		// set the current card to the one we want
		pager.setCurrentItem(position/2);

		// change the main view 
		MainActivity.isGridViewOn = false;
		main.getMainPage().removeView(main.getGridView());
		main.getMainPage().addView(pager, main.getIndexOfView());
		main.invalidateOptionsMenu();

	}

}
