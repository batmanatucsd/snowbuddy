package com.androidstudio.snowman.auxiliary;

import com.androidstudio.snowman.CardFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import java.util.ArrayList;

public class PagerAdapter extends FragmentStatePagerAdapter {

	ArrayList<CardFragment> fragments;
	
	public PagerAdapter(FragmentActivity activity, ArrayList<CardFragment> fragments) {
		super(activity.getSupportFragmentManager());
		this.fragments = fragments;
	}
	
	@Override
	public Fragment getItem(int i) {
		return fragments.get(i);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

}
