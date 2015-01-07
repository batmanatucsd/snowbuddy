package com.androidstudio.snowman.auxiliary;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CardHandler {

  // Database fields
  private SQLiteDatabase database;
  private MySQLHelper dbHelper;
  private String[] allColumns = { MySQLHelper.CARD_ID,
      MySQLHelper.CARD_FRONT, 
      MySQLHelper.CARD_BACK, 
      MySQLHelper.CARD_GROUP };

  public CardHandler(Context context) {
    dbHelper = new MySQLHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  //create new card
  public void createCard(Card card) {
    ContentValues values = new ContentValues();
    values.put(MySQLHelper.CARD_FRONT, card.getFront());
    values.put(MySQLHelper.CARD_BACK, card.getBack());
    values.put(MySQLHelper.CARD_GROUP, card.getGroup());
    database.insert(MySQLHelper.DATABASE_TABLE, null, values);
  }

  //delete existing card
  public void deleteCard(Card card) {
    long id = card.getId();
    System.out.println("Comment deleted with id: " + id);
    database.delete(MySQLHelper.DATABASE_TABLE, MySQLHelper.CARD_ID
        + " ='" + id +"'", null);
  }
  
  //delete existing card
  public void deleteGroup(ArrayList<Card> cards) {
    for(int i=0; i<cards.size(); ++i) {
      deleteCard(cards.get(i));
    }
  }

  //get lists of cards
  public ArrayList<Card> getAllCards() {
    ArrayList<Card> cards = new ArrayList<Card>();

    Cursor cursor = database.query(MySQLHelper.DATABASE_TABLE,
        allColumns, null, null, null, null, null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      Card card = cursorToCard(cursor);
      cards.add(card);
      cursor.moveToNext();
    }
    // make sure to close the cursor
    cursor.close();
    return cards;
  }

  public ArrayList<Card> getGroupCards(String group) {
	  ArrayList<Card> cards = new ArrayList<Card>();
	  
	  Cursor cursor = database.query(MySQLHelper.DATABASE_TABLE, 
			  allColumns, null, null, null, null, null);
	  
	  cursor.moveToFirst();
	  while(!cursor.isAfterLast()) {
		  Card card = cursorToCard(cursor);
		  if(card.getGroup().equals(group)){
			  cards.add(card);
		  }
		  cursor.moveToNext();
	  }
	  cursor.close();
	  return cards;
  }
  
  // return the last card in the database
  public Card lastCard() {
	  Cursor cursor = database.query(MySQLHelper.DATABASE_TABLE,
		        allColumns, null, null, null, null, null);
	  Card card = new Card();
	  
	  if(cursor.moveToLast() != false) {  
		  card.setId(cursor.getInt(0));
		  card.setFront(cursor.getString(1));
		  card.setBack(cursor.getString(2));
	  }
	 
	  return card;
  }
  
  //move cursor to the card to get certain card information
  private Card cursorToCard(Cursor cursor) {
    Card card = new Card();
    card.setId(cursor.getInt(0));
    card.setFront(cursor.getString(1));
    card.setBack(cursor.getString(2));
    card.setGroup(cursor.getString(3));
    return card;
  }
} 
