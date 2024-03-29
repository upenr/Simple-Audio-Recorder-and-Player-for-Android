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
		label2 = (TextView) findViewById(R.id.item1);
		label2.setText("1) You can touch and hold your idea to share it, for ex. to SoundCloud, from which sharing audio to Facebook and Twitter become possible.\n2) Invite your friends to download the app by double tapping anywhere in the app (other than this page) or using the Invite button.\n3) You may be able to share your ideas to several applications depending on what you have installed.\n4) You can rename or delete an idea by holding it. \n5) Send an audio from another app to IdeaBook by simply picking IdeaBook from a Share popup on another app. \n6) If things don't work or you need more features, please leave a feedback with details and your Android OS version.");
		/*label2.append("2) This file will be deleted when its size reaches 1MB. \n");
		label2.append("3) Use a tool like File Manager to locate the file and move it if necessary. \n");
		label2.append("4) The Ping Range application requires you to enter a start IPv4 address, an end IPv4 address and the number of packets you would like to send to each of these addresses.\n");*/
		label2.setMovementMethod(new ScrollingMovementMethod());
		label2.setTextSize(16);
		label2.setTextColor(Color.rgb(0,0,0));
		label2.setBackgroundColor(Color.rgb(255,198,93));
		button3 = (Button) findViewById(R.id.button3);
		button3.setTextColor(Color.rgb(0,0,0));
		button3.setTextSize(18);
		interstitial = new InterstitialAd(InformationActivity.this);
		interstitial.setAdUnitId("ca-app-pub-5578845458712640/9337280439");
		AdRequest adRequest = new AdRequest.Builder()
				.build();
		interstitial.loadAd(adRequest);
 
		interstitial.setAdListener(new AdListener() {
			public void onAdLoaded() {
				displayInterstitial();
			}
		});
		AdView adView = (AdView) this.findViewById(R.id.adView);
				adView.loadAd(adRequest);
	}
	@Override
	   public boolean onCreateOptionsMenu(Menu menu) {
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