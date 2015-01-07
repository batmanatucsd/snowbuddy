package com.androidstudio.snowman.auxiliary;

import java.util.ArrayList;

import com.androidstudio.snowman.MainActivity;
import com.androidstudio.snowman.R;

import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

public class DrawerItemClickListener implements ListView.OnItemClickListener {

	private MainActivity main;
	private ListView list;
	private DrawerLayout drawer;
	
	// Constructor
	public DrawerItemClickListener(MainActivity main, ListView list, DrawerLayout drawer) {
		this.main = main;
		this.list = list;
		this.drawer = drawer;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// Highlight the selected item
	    list.setItemChecked(position, true);
	    ViewGroup mainPage = main.getMainPage();
	    String newGroup = list.getItemAtPosition(position).toString();
	    
	    // if the drawer is in lock mode, unlock it
	    if(main.getDrawer().getDrawerLockMode(Gravity.LEFT) == DrawerLayout.LOCK_MODE_LOCKED_OPEN)
	    	main.getDrawer().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
	    
	    // if the new group is not the same as the current group
	    if(!newGroup.equals(main.getCurrentGroup())) {
	    	// Change currentGroup
	    	main.setCurrentGroup(newGroup);

	    	// get cards of the new group from the database
	    	ArrayList<Card> cards = MainActivity.cardhandler.getGroupCards(main.getCurrentGroup());

	    	// regenerate cards in main activity
	    	main.setCards(cards);

	    	// regenerate fragments in main activity
	    	main.getViewPager().removeAllViews(); // remove all the old views
	    	main.getFragments(main.getCards());

	    	// notify the adapters that the data have changed 
	    	main.getViewPager().setAdapter(null);
	    	main.getViewPager().setAdapter(new PagerAdapter(main, main.getFragments()));

	    	main.getViewPager().getAdapter().notifyDataSetChanged();
	    	((GridViewAdapter) main.getGridView().getAdapter()).notifyDataSetChanged();
	    	
	    	if(cards.size() == 0) {
	    		main.findViewById(R.id.emptyDeckView).setVisibility(View.VISIBLE);
	    	}
	    }
	    
	    // flip the main view if needed
    	if(!MainActivity.isGridViewOn)
    		main.flipMainView();
	    
	    // Close the drawer
	    drawer.closeDrawer(list);
	}

}
