package com.androidstudio.snowman;

import com.androidstudio.snowman.auxiliary.AnimationFactory;
import com.androidstudio.snowman.auxiliary.Card;
import com.androidstudio.snowman.auxiliary.AnimationFactory.FlipDirection;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.ViewAnimator;

public class CardFragment extends Fragment {
	public static enum FaceOfCard { KEEPOLD, SHOWFRONT, SHOWBACK  }
	
	private TextView frontText;
	private TextView backText;
	private int indexOfFront;
	private int indexOfBack;
	private Card card;
	
	private ViewAnimator viewAnimator;

	private FaceOfCard faceOfCard = FaceOfCard.KEEPOLD;

	private static final String CARD = "com.androidstudio.snowman.CARD";
	private static final String FACEOFCARD = "com.androidstudio.snowman.FACEOFCARD";
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Get message
		card = (Card) getArguments().getParcelable(CARD);
		
		// Inflate view
		View view = (View) inflater.inflate(R.layout.card_fragment, container, false);
		
		// initialize variables
		viewAnimator = (ViewAnimator) view.findViewById(R.id.cardViewFlipper);
		frontText = (TextView) view.findViewById(R.id.frontText);
		backText = (TextView) view.findViewById(R.id.backText);
		
		// get index of child
		indexOfFront = viewAnimator.indexOfChild(frontText);
		indexOfBack = viewAnimator.indexOfChild(backText);
		
		// set scrollbar movement on the textview
		frontText.setMovementMethod(new ScrollingMovementMethod());
		backText.setMovementMethod(new ScrollingMovementMethod());
		
		// set onclicklistener
		myClickListener myClick = new myClickListener();
		frontText.setOnClickListener(myClick);
		backText.setOnClickListener(myClick);

		// Set text on the TextView
		frontText.setText(card.getFront());
		backText.setText(card.getBack());
		
		// display the front or back of the card 
		// depending on the faceOfCard
		switch(faceOfCard) {
		case SHOWBACK: 
			viewAnimator.setDisplayedChild(indexOfBack);
			break;
		case SHOWFRONT:
			viewAnimator.setDisplayedChild(indexOfFront);
			break;
		default:
			viewAnimator.setDisplayedChild(indexOfFront);
		}
		
		return view;
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// if the faceOfCard is KEEPOLD, keep the old face of the card
		if(savedInstanceState != null)
			if(faceOfCard == FaceOfCard.KEEPOLD)
				switch(savedInstanceState.getInt(FACEOFCARD)) {
				case 1:
					faceOfCard = FaceOfCard.SHOWFRONT;
					break;
				case 2:
					faceOfCard = FaceOfCard.SHOWBACK;
					break;
				default:
				}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// save the face of the card
		// whether its showing the front or back
		switch(faceOfCard) {
		case SHOWFRONT:
			outState.putInt(FACEOFCARD, 1);
			break;
		case SHOWBACK: 
			outState.putInt(FACEOFCARD, 2);
			break;
		default:
			outState.putInt(FACEOFCARD, 0);
		}
		
		super.onSaveInstanceState(outState);
	}

	public static CardFragment newInstance(Card card) {
		CardFragment cardFrag = new CardFragment();
		Bundle args = new Bundle();
		
		// Put extra
		args.putParcelable(CARD, card);
		cardFrag.setArguments(args);
		
		return cardFrag;
	}

	public ViewAnimator getViewAnimator() {
		return viewAnimator;
	}

	public TextView getFrontText() {
		return frontText;
	}

	public TextView getBackText() {
		return backText;
	}

	public int getIndexOfFront() {
		return indexOfFront;
	}


	public int getIndexOfBack() {
		return indexOfBack;
	}

	// getter for faceOfCard
	public FaceOfCard getFaceOfCard() {
		return faceOfCard;
	}
	
	// setter for faceOfCard
	public void setFaceOfCard(FaceOfCard newFace) {
		faceOfCard = newFace;
	}

	private class myClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			flipCard();
		}
		
		public void flipCard(){
			AnimationFactory.flipTransition(viewAnimator, FlipDirection.LEFT_RIGHT);
			// switch the faceOfCard == flip the face of the card
			switch(faceOfCard) {
			case SHOWBACK:
				faceOfCard = FaceOfCard.SHOWFRONT;
				break;
			case SHOWFRONT:
				faceOfCard = FaceOfCard.SHOWBACK;
				break;
			default:
				faceOfCard = FaceOfCard.SHOWBACK;
			}
		}
	}


}
