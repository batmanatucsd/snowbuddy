/**
 * 
 */
package com.androidstudio.snowman.auxiliary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author Dayoun
 *
 */

public class MySQLHelper extends SQLiteOpenHelper {
	public static final String CARD_ID = "_id";
    public static final String CARD_FRONT = "front";
    public static final String CARD_BACK = "back";
    public static final String CARD_GROUP = "_group";

	public static final String DATABASE_NAME = "card.db";
	public static final String DATABASE_TABLE = "Card";
	public static final int DATABASE_VERSION = 1;
	
	//create database that store informations in
	public static final String DATABASE_CREATE = 
		"CREATE TABLE " + DATABASE_TABLE + " ("
	    + CARD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
	    + CARD_FRONT + " TEXT NOT NULL, "
        + CARD_BACK + " TEXT NOT NULL, "
        + CARD_GROUP + " TEXT NOT NULL );";

	public MySQLHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	//upgrade the database version
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(MySQLHelper.class.getName(),
	            "Upgrading database from version " + oldVersion + " to "
	                + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
	    onCreate(db);
	}

}

