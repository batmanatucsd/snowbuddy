package com.androidstudio.snowman;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.androidstudio.snowman.auxiliary.Card;

public class NotiService extends Service{
	
	private int notiInt;
	private List<Card> cards;
	private ArrayList<Card> cardList;
	private Handler handler = new Handler(); // handler object
	private Runnable notiService;	// runnable object
	private int notiCounter;
	SeekbarActivity free; 
	private long freqs; 
	MainActivity mainActivity;
	SeekbarActivity seek;
	
	public static String POPUPCARD = "com.androidstudio.snowman.POPUPCARD";
	String mainName = "com.androidstudio.snowman.MainActivity";

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		
		return null;
	}

	public void onCreate(){

		// instantiate runnable
		notiService = new Runnable(){
			
		
			@Override
			/** Run the service. check for foreground, 
			 *  either status noti or pop up depend on foreground.
			 */
			public void run() {
				// TODO Auto-generated method stub
				freqs = calculateRate(notiInt);
			
				Toast toast = Toast.makeText(getApplicationContext(), Long.toString(freqs), Toast.LENGTH_SHORT);
				toast.show();

				// status bar noti if app is on foreground
				if( onForeground() == true){ // check if app is on foreground
					// if mainActivity is on top do the Status notificaton
					if(mainName.equalsIgnoreCase(topAct())){
						// call notification method
						
						// wait on the status noti after starting app.
						new CountDownTimer(freqs, freqs) {
							// on tick the pulled down will go back to active
							public void onTick(long millisUntilFinished) {
								// nothing need here.
							}	
							// on finish repost the place it. 
							public void onFinish() {
//								generateNotification(getApplicationContext(), "FUCK ANDROID", notiCounter);		
								
							}
						}.start();
					}
				}
				// if the app is not on the foreground call showdialog.
				if( onForeground() == false){
					
					showDialog();

				}
				// THIS IS THE PARAMTER call the freq method.
				handler.postDelayed(notiService, 5000);	
			}
		};		
	}
	
	
	public static Card getRandomCard(){
		
		Set<String> groups = MainActivity.selectedGroups;
		
		int size = groups.size();
		int groupChoice = new Random().nextInt(size);
		int cardChoice;
		
		Card randomCard;
		
	
		String group = (String) groups.toArray()[groupChoice];
		
		ArrayList<Card> cards = MainActivity.cardhandler.getGroupCards(group);
		cardChoice = new Random().nextInt(cards.size());
		
		randomCard = cards.get(cardChoice);
		
		return randomCard;
	}

	/** 
	 * Method to check if App is on foreground
	 * boolean true for onforeground and false if app is minimized
	 */
	protected boolean onForeground(){
		// instantiate activty manager to get current activity services.
		ActivityManager manager = 
				(ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		// get the very top task running.
		List<ActivityManager.RunningTaskInfo> tasks = manager.getRunningTasks(1);
		// if there is no task the app is not on foreground.
		if(tasks.isEmpty()){
			return false; 
		}
		// get top package name
		String topActivityName = tasks.get(0).topActivity.getPackageName();
		// get top class name.
		String classname = tasks.get(0).topActivity.getClassName();

		// return true if top activity == packagename.
		return topActivityName.equalsIgnoreCase(getPackageName());
	}

	/*
	 * get top activity name
	 */
	protected String topAct(){
		// instantiate activty manager to get current activity services.
		ActivityManager manager = 
				(ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		// get the very top task running.
		List<ActivityManager.RunningTaskInfo> tasks = manager.getRunningTasks(1);
		// check if the package name of current top matches our app.
		String className = manager.getRunningTasks(1).get(0).topActivity.getClassName();	
		// return the top class name
		return className;
	}

	/*
	 * This method calls an dialog theme activity when the app is minimized
	 * It will have question and answers 
	 */
	private void showDialog(){	
		
		Bundle bun = new Bundle();
		bun.putParcelable(POPUPCARD, getRandomCard());
		
		// create intent to start the popupdialog activity class
		Intent resInt = new Intent(this, Popupdialog.class);
		
		resInt.putExtra(POPUPCARD, getRandomCard());
		
		resInt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
				Intent.FLAG_ACTIVITY_CLEAR_TASK);
		// start the activity
		startActivity(resInt);
	}

	@Override
	// add parameter for user popup recurrence
	public void onStart(Intent intent, int startid) {
		
		// run ourService, from other class, this is where it starts.
		notiInt = intent.getIntExtra(SeekbarActivity.NOTIINT, 20);
		notiService.run();		
	}

	// calculate the frequencies to milisecond.
	public long calculateRate(long notiInt){
		long rate = 0;
		rate =  (long) (1000 * 3600)/ notiInt; 	
		return rate;
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
	}
	
	/**
	 * pass in title, description, and object for status bar notification.
	 * this method will notify when user has the app on the foreground.
	 * @param title
	 * @param text
	 */
	private void generateNotification(Context context, String message, int number) {

		int icon = R.drawable.sbbox; // set icon 
		long when = System.currentTimeMillis(); // current time.


		// instatiate notification manager
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		
		// create the notification with icon, message, and time stamp.
		Notification notification = new Notification(icon, message, when);
		String title = context.getString(R.string.app_name); // title is app name

		// create intent for clicking the notification, in this case spashscreen, beginning.
		// change the class, if we want it to goto the activity of the card.
		Intent notificationIntent = new Intent(context, SplashActivity.class);
		// when clicked clear all activities, and one activity at top stack
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);

		// pending intent on actualy clicking of the status noti.
		PendingIntent intent = PendingIntent.getActivity(context, 0,
				notificationIntent, 0);

		// now create the notification, with real info
		notification.setLatestEventInfo(this, title, message, intent);
		// auto cancel on click.
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		
		

		/* 	 for stacking?
		int notifyID = 1;
		NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(this)
		.setContentTitle("New Message")
		.setContentText("You've received new messages.")
		.setSmallIcon(R.drawable.ic_launcher);
		int numMessages = 0;

		mNotifyBuilder.setContentText("Study Buddy")
        .setNumber(++numMessages);

		notificationManager.notify(
	            notifyID,
	            mNotifyBuilder.build());
		 */


		// calculate random int for unique ID for different notifications.
		int seed = (int)(System.currentTimeMillis() % Integer.MAX_VALUE);
		Random r = new Random(seed);
		int notificationID = r.nextInt();
		// notify the device
		notificationManager.notify(notificationID, notification);

	}

}
