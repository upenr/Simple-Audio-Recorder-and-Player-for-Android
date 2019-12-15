package com.ideabook;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.security.Provider.Service;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.ideabook.SimpleGestureFilter.SimpleGestureListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;


public class PlayActivity extends ListActivity implements SimpleGestureListener {
	private static final String dirPath= Environment.getExternalStorageDirectory()+File.separator+"Idea Book" + "/";
	private List<String> songs = new ArrayList<String>();
	private MediaPlayer mp = new MediaPlayer();
	private Button stopPlay, btnSpread;
	ArrayAdapter<String> songList;
	Context context;
	private InterstitialAd interstitial;
	ListView mListView;
	int clicked =0;
	List<String> songsPaths = new ArrayList<String>();
	String[] ideas;
	String dir = Environment.getExternalStorageDirectory()+File.separator+"Idea Book"+ "/";
	String ext = ".3gp";
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", java.util.Locale.getDefault());
    String date1 = df.format(Calendar.getInstance().getTime());
    private SimpleGestureFilter detector;
    int adLoad = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		try{
			
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_play);
		 // Detect touched area 
            detector = new SimpleGestureFilter(this,this);

			stopPlay=(Button) findViewById(R.id.button5);
			btnSpread = (Button) findViewById(R.id.button6);
			mListView=(ListView) findViewById(android.R.id.list);
			Log.e("IAM", "HERE-0");
			updatePlaylist();
			
			// Prepare the Interstitial Ad
			interstitial = new InterstitialAd(PlayActivity.this);
			// Insert the Ad Unit ID
			interstitial.setAdUnitId("ca-app-pub-5578845458712640/9337280439");
			// Request for Ads
			AdRequest adRequest = new AdRequest.Builder()
			// Add a test device to show Test Ads
			// .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
			// .addTestDevice("A6117FBB1EA571F3A17452FCCAD085B9")
					.build();
			// Load ads into Interstitial Ads
			// Load ads into Interstitial Ads
			interstitial.loadAd(adRequest);
	 
			// Prepare an Interstitial Ad Listener
			interstitial.setAdListener(new AdListener() {
				public void onAdLoaded() {
					// Call displayInterstitial() function
					if (adLoad==1){
						displayInterstitial();
						adLoad=0;
					}
					else{
						
					}
					
				}
			});
			//Locate the Banner Ad in activity_main.xml
			AdView adView = (AdView) this.findViewById(R.id.adView);
			// Load ads into Banner Ads
					adView.loadAd(adRequest);
			this.getListView().setLongClickable(true);
			ListView lv = getListView();
			lv.setOnItemLongClickListener(new OnItemLongClickListener() {
			    @Override
			    public boolean onItemLongClick(AdapterView<?> list, final View view,int position, long id) {
			        // your code
			    	//registerForContextMenu(lv);
			    	final int infoPosition = position;
			    	Log.e("LONG clicked","pos: " + infoPosition);
			    	 PopupMenu popup = new PopupMenu(PlayActivity.this, view);
		             popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());
		
		             popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {
		        @Override
		        public boolean onMenuItemClick(MenuItem item) {
		          //Toast.makeText(getBaseContext(), "You selected the action: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                    Log.e("LONG clicked","IDEA pos: " + songs.get(infoPosition));
                    Log.e("LONG clicked","IDEA itemID: " + item.getItemId());
                    String title1=item.getTitle().toString();
                    final String nameFile = songs.get(infoPosition).toString();
                    Log.e("LONG clicked","IDEA itemTitle: " + title1);
                    if (title1.equalsIgnoreCase("Rename")){
                    	//Logic to rename
		                        	
            	Log.e("LONG clicked","IDEA NAME NOW: " + nameFile);
            	 AlertDialog.Builder alert = new AlertDialog.Builder(PlayActivity.this);
                 alert.setTitle("Rename");
                 final EditText input = new EditText(PlayActivity.this);
                 alert.setView(input);

                 alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int whichButton) {
                         String srt1 = input.getEditableText().toString();
                         File file2=new File(dir+srt1+ext);
                         File file = new File(dir+nameFile+ext);
                         Log.e("LONG clicked","IDEA NAME NOW-1: " + nameFile);
                         if(!file2.exists())
                         {
                        	 boolean renamed=file.renameTo(new File(dir,srt1+ext));
                             Log.e("LONG clicked","IDEA RENAMED TO: " + srt1+ext);
                             if (renamed == true) {Toast.makeText(getApplicationContext(), "Renamed to "+srt1+".", Toast.LENGTH_LONG).show();}
      					   else {Toast.makeText(getApplicationContext(), "Can not be Renamed. Try again.", Toast.LENGTH_LONG).show(); }				   
                         }
                         else{
                        	 Toast.makeText(getApplicationContext(), "Can not be Renamed. Already exists.", Toast.LENGTH_LONG).show(); 
                         }
                     //update your listview here 
                         Intent intent = getIntent();
                         intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                         finish();
                         startActivity(intent);

                     }
                 });

                 alert.setNegativeButton("CANCEL",
                     new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialog, int whichButton) {
                             dialog.cancel();
                         }
                     });
             AlertDialog alertDialog = alert.create();
             alertDialog.show();
             return false;
        }
        else if (title1.equalsIgnoreCase("Delete")) {
        	AlertDialog.Builder alert = new AlertDialog.Builder(PlayActivity.this);
             alert.setTitle("Delete");
             final TextView input = new TextView(PlayActivity.this);
             input.setText(" Are you sure you want to delete\n \""+nameFile+ "\"?\n");
             input.setTextColor(Color.rgb(0,0,0));
             input.setTextSize(20);
             alert.setView(input);

             alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int whichButton) {
                     String srt1 = nameFile;
                     File file2=new File(dir+srt1+ext);
                     //File file = new File(dir+nameFile+ext);
                     Log.e("LONG clicked","TO DELETE IDEA NAME NOW-1: " + nameFile);
                     if(file2.exists())
                     {
                    	 boolean deleted=file2.delete();
                         if (deleted == true) {Toast.makeText(getApplicationContext(), "Deleted "+srt1+".", Toast.LENGTH_LONG).show();}
  					   else {Toast.makeText(getApplicationContext(), "Can not be Deleted. Try again.", Toast.LENGTH_LONG).show(); }				   
                     }
                     else{
                    	 Toast.makeText(getApplicationContext(), "Can not be deleted. Does not exist.", Toast.LENGTH_LONG).show(); 
                     }
                 //update your listview here 
                     Intent intent = getIntent();
                     intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                     finish();
                     startActivity(intent);

                 }
             });

             alert.setNegativeButton("CANCEL",
                     new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialog, int whichButton) {
                             dialog.cancel();
                         }
                     });
             AlertDialog alertDialog = alert.create();
             alertDialog.show();
             return false;
        	
        }
        else if (title1.equalsIgnoreCase("Share")){
        	
        	Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND); 
        	//sharingIntent.setClassName("com.android.mms", "com.android.mms.ui.ComposeMessageActivity");
            //sharingIntent.setType("text/plain");
        	sharingIntent.putExtra("sms_body", "if you are sending text"); 
        	//sharingIntent.setType("audio/3gp");
        	sharingIntent.setType("audio/*");
        	sharingIntent.setAction(Intent.ACTION_SEND);
            final File file3 = new File(dir+nameFile+ext);
            Uri uri = Uri.fromFile(file3);
           //Uri uri = Uri.parse(dir+nameFile+ext);
            //sharingIntent.setDataAndType(uri,getContentResolver().getType(uri));
            
            String shareBody = "My new idea recording! Recorded with IdeaBook Recorder for Android.";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "#IdeaBook");
            sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
                        	
                        }
                        return true;
                    }
                });
 
              /** Showing the popup menu */
		             popup.show();
		             return true;
			    }
			});
			
			
			stopPlay.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v){
				mp.stop();
				Toast.makeText(getApplicationContext(), "Stopped.", Toast.LENGTH_LONG).show();
				}
				
				
			});
			
			btnSpread.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v){
					
					//code to spread the word
					//String packageName = getApplicationContext().getPackageName();
					Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND); 
		            sharingIntent.setType("text/plain");
		        	sharingIntent.putExtra("sms_body", "Download #IdeaBook Recorder for Android from the Play Store: https://play.google.com/store/apps/details?id=com.ideabook"); 
		        	sharingIntent.setAction(Intent.ACTION_SEND);
		            String shareBody = "Download #IdeaBook Recorder for Android from the Play Store: https://play.google.com/store/apps/details?id=com.ideabook";
		            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "#IdeaBook");
		            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
		            startActivity(Intent.createChooser(sharingIntent, "Share via"));
					
				}
				
			});
			
			
		}catch (NullPointerException e) {
            Log.e(getString(R.string.app_name), e.getMessage());
        }
	}
	   @Override
	   public boolean onCreateOptionsMenu(Menu menu) {
	      // Inflate the menu; this adds items to the action bar if it is present.
	      getMenuInflater().inflate(R.menu.play, menu);
		   /*menu.add(1,INFORMATION,1,"Information");
			menu.add(2,EXIT,2,"Exit");*/
			
	      return true;
	   }
		public boolean onOptionsItemSelected (MenuItem item){
			switch (item.getItemId()){
			case R.id.info:
				Log.w("I AM HERE ?!", "YES03");
				//Toast.makeText(getApplicationContext(), "This works - tested.", Toast.LENGTH_LONG).show();
				Intent infoIntent = new Intent(this,InformationActivity.class);
		        startActivity(infoIntent);  
		        return true;
			case R.id.exit:
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			case R.id.reco:
				Log.w("I AM HERE ?!", "YES03");
				//Toast.makeText(getApplicationContext(), "This works - tested.", Toast.LENGTH_LONG).show();
				Intent infoIntent1 = new Intent(this,MainActivity.class);
		        startActivity(infoIntent1);  
		        return true;
		    default:
		        return super.onOptionsItemSelected(item);
			}
			}
		public void displayInterstitial() {
			// If Ads are loaded, show Interstitial else show nothing.
			if (interstitial.isLoaded()) {
				interstitial.show();
			}
		}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	    //user has long pressed your TextView
		
	}
	
	@Override
	protected void onListItemClick(ListView list, View view, int position, long id)
	{
		//super.onListItemClick(list, view, position, id);
		Log.e("IAM", "HERE");
		try{
			 view.setSelected(true);
			String dir = Environment.getExternalStorageDirectory()+File.separator+"Idea Book"+ "/";
			String ext = ".3gp";
			Log.e("PATH", dir);
			mp.reset();
			Log.e("IAM", songs.get(position));
			mp.setDataSource(dir + songs.get(position)+ext);
			if(mp!=null){
				mp.prepare();
				mp.start();
				Toast.makeText(getApplicationContext(), "Playing your idea", Toast.LENGTH_LONG).show();
			}
			else{
				Toast.makeText(getApplicationContext(), "Can't play. Leave a feedback with your Android OS version for testing.", Toast.LENGTH_LONG).show();
			}
			
			
		} catch (IOException e) {
	        Log.e(getString(R.string.app_name), e.getMessage());
	    }
	}	
	
	private void updatePlaylist() {
		File home = new File (dirPath);
		if (home.listFiles( new gpFilter()).length>0){
			
			for (File file : home.listFiles( new gpFilter())){
				String finName=file.getName();
				String theName=finName.substring(0, finName.lastIndexOf('.'));
				songs.add (theName);
				songsPaths.add(file.getAbsolutePath());
			}
			
			songList = new ArrayAdapter<String>(this,R.layout.song_item,songs);
			setListAdapter (songList);
			
		}
			
	}

	@Override
    public boolean dispatchTouchEvent(MotionEvent me){
        // Call onTouchEvent of SimpleGestureFilter class
	 //Log.e("19","YES");
         this.detector.onTouchEvent(me);
       return super.dispatchTouchEvent(me);
    }
@Override
public void onSwipe(int direction) {
	String str = "";
      switch (direction) {
      
      case SimpleGestureFilter.SWIPE_RIGHT : str = "Swipe Right";
      //Log.e("20","YES");
      Intent intent = new Intent(this, MainActivity.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	    startActivity(intent);
                                               break;
      case SimpleGestureFilter.SWIPE_LEFT :  str = "Swipe Left";
      //Log.e("21","YES");
      Intent intent3 = new Intent(this, MainActivity.class);
      intent3.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	    startActivity(intent3);
                                                     break;
      case SimpleGestureFilter.SWIPE_DOWN :  str = "Swipe Down";
      //Log.e("22","YES");
                                                     break;
      case SimpleGestureFilter.SWIPE_UP :    str = "Swipe Up";
      //Log.e("23","YES");
                                                     break;
      
      }
      //Log.e("24","YES");Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	
}
@Override
public void onDoubleTap() {
	Log.e("25","YES");
	 //Toast.makeText(this, "Double Tap", Toast.LENGTH_SHORT).show();
	//code to spread the word
	//String packageName = getApplicationContext().getPackageName();
	Intent sharingIntent2 = new Intent(android.content.Intent.ACTION_SEND); 
	sharingIntent2.setType("text/*");
	sharingIntent2.setAction(Intent.ACTION_SEND);
    String shareBody = "Download #IdeaBook Recorder for Android from the Play Store: https://play.google.com/store/apps/details?id=com.ideabook";
    sharingIntent2.putExtra(android.content.Intent.EXTRA_SUBJECT, "#IdeaBook");
    sharingIntent2.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
    sharingIntent2.putExtra("sms_body", "Download #IdeaBook Recorder for Android from the Play Store: https://play.google.com/store/apps/details?id=com.ideabook");
    startActivity(Intent.createChooser(sharingIntent2, "Share via"));
	
	
}
}

class gpFilter implements FilenameFilter {
	public boolean accept(File dir, String name)
	{
		return (name.endsWith(".3gp"));
	}
}

