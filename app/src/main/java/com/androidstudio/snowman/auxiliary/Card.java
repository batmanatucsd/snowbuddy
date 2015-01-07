package com.androidstudio.snowman.auxiliary;

import android.os.Parcel;
import android.os.Parcelable;

public class Card implements Parcelable {
	private String group;
	private String front;
	private String back;
	private int id;
	
	private boolean print;

	
	private static Card instance;

	/*
	 * Get Singleton instance of this list.
	 */
	public static Card getInstance() {
		if (instance == null) {
			instance = new Card();
		}

		return instance;
	}
	
	public void setPrint(boolean bool) {
		this.print = bool;
	}

	//used when make new card 
	public Card(String group, String front, String back) {
		this.group = group;
		this.front = front;
		this.back = back;
	}

	/**
	 * Default Constructor
	 */
	public Card() {}

	/**
	 * Getter and setters for all instance variables
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	//get groups
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}

	//get front values
	public String getFront() {
		return front;
	}

	public void setFront(String front){
		this.front = front;
	}

	//get back values
	public String getBack() {
		return back;
	}

	public void setBack(String back) {
		this.back = back;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(group);
		dest.writeString(front);
		dest.writeString(back);
	}

	public Card(Parcel in) {
		this.group = in.readString();
		this.front = in.readString();
		this.back = in.readString();
	}

	public static final Parcelable.Creator<Card> CREATOR 
	= new Parcelable.Creator<Card>() {

		public Card createFromParcel(Parcel in) {
			return new Card(in);
		}

		public Card[] newArray(int size) {
			return new Card[size];
		}
	};

}