package com.ideabook;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
public class InformationActivity extends Activity {
	LinearLayout layout2;
	private InterstitialAd interstitial;
	TextView label2;
	Button button3;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_information);
		//Typeface type = Typeface.createFromAsset(getAssets(),"fonts/41370_WINDSORCONDENSED.TTF");
		label2 = (TextView) findViewById(R.id.item1);
		label2.setText("1) You can touch and hold your idea to share it, for ex. to SoundCloud, from which sharing audio to Facebook and Twitter become possible.\n2) Invite your friends to download the app by double tapping anywhere in the app (other than this page) or using the Invite button.\n3) You may be able to share your ideas to several applications depending on what you have installed.\n4) You can rename or delete an idea by holding it. \n5) Send an audio from another app to IdeaBook by simply picking IdeaBook from a Share popup on another app. \n6) If things don't work or you need more features, please leave a feedback with details and your Android OS version.");
		/*label2.append("2) This file will be deleted when its size reaches 1MB. \n");
		label2.append("3) Use a tool like File Manager to locate the file and move it if necessary. \n");
		label2.append("4) The Ping Range application requires you to enter a start IPv4 address, an end IPv4 address and the number of packets you would like to send to each of these addresses.\n");*/
		label2.setMovementMethod(new ScrollingMovementMethod());
		label2.setTextSize(16);
		label2.setTextColor(Color.rgb(0,0,0));
		//label2.setTypeface(type);
		label2.setBackgroundColor(Color.rgb(255,198,93));
		button3 = (Button) findViewById(R.id.button3);
		button3.setTextColor(Color.rgb(0,0,0));
		//button3.setBackgroundColor(Color.rgb(255,255,255));
		//button3.setBackgroundColor(Color.rgb(255,194,75));
		//button3.setTypeface(type);
		button3.setTextSize(18);
		//layout2.addView(button3);
		// Prepare the Interstitial Ad
		interstitial = new InterstitialAd(InformationActivity.this);
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
				displayInterstitial();
			}
		});
		//Locate the Banner Ad in activity_main.xml
		AdView adView = (AdView) this.findViewById(R.id.adView);
		// Load ads into Banner Ads
				adView.loadAd(adRequest);
	}
	@Override
	   public boolean onCreateOptionsMenu(Menu menu) {
	      // Inflate the menu; this adds items to the action bar if it is present.
	      getMenuInflater().inflate(R.menu.play, menu);
	      MenuItem item = menu.findItem(R.id.info);
	      item.setVisible(false);
		   /*menu.add(1,INFORMATION,1,"Information");
			menu.add(2,EXIT,2,"Exit");*/
			
	      return true;
	   }
		public boolean onOptionsItemSelected (MenuItem item){
			switch (item.getItemId()){
			case R.id.reco:
				Log.w("I AM HERE ?!", "YES03");
				//Toast.makeText(getApplicationContext(), "This works - tested.", Toast.LENGTH_LONG).show();
				Intent infoIntent = new Intent(this,MainActivity.class);
		        startActivity(infoIntent);  
		        return true;
			case R.id.exit:
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
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
	public void sendMessage(View view) 
	{
	    Intent intent = new Intent(this, MainActivity.class);
	    startActivity(intent);
	}
	
}