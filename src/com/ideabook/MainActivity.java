package com.ideabook;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.ideabook.SimpleGestureFilter.SimpleGestureListener;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SimpleGestureListener {
 private MediaRecorder myAudioRecorder;
 private String outputFile = null;
 private Button start, stop, play, view1;
 LinearLayout layout1;
 MediaPlayer m = new MediaPlayer();
 int num = 1;
 Context context;
 String dir = Environment.getExternalStorageDirectory() + File.separator + "Idea Book";
 String dir1 = dir + "/";
 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", java.util.Locale.getDefault());
 String date = df.format(Calendar.getInstance().getTime());
 ImageView imageview1;
 private SimpleGestureFilter detector;

 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);
  Intent intent = getIntent();
  String action = intent.getAction();
  String type = intent.getType();
  if (Intent.ACTION_SEND.equals(action) && type != null) {
   if (type.startsWith("audio/")) {
    handleSendAudio(intent);
   }
  }

  detector = new SimpleGestureFilter(this, this);
  /* RelativeLayout rel_layout=(RelativeLayout)findViewById(R.id.rel_layout1);
   rel_layout.setOnTouchListener(gestureListener);*/

  start = (Button) findViewById(R.id.button1);
  stop = (Button) findViewById(R.id.button2);
  play = (Button) findViewById(R.id.button3);
  view1 = (Button) findViewById(R.id.button4);
  TextView textView2 = (TextView) findViewById(R.id.textView1);
  imageview1 = (ImageView) findViewById(R.id.imageView1);
  imageview1.setImageResource(R.drawable.mic1);
  start.setEnabled(true);
  stop.setEnabled(false);
  play.setEnabled(false);
  view1.setEnabled(true);
  textView2.setTextColor(Color.rgb(0, 0, 0));
  String state = android.os.Environment.getExternalStorageState();
  if (!state.equals(android.os.Environment.MEDIA_MOUNTED)) {
   Toast.makeText(getApplicationContext(), "No SD card.", Toast.LENGTH_SHORT).show();
  }

  if (!folder.exists()) {
   if (folder.mkdirs() == true) {
    folder.mkdirs();
   } else {
    Toast.makeText(getApplicationContext(), "Can't create a storage path to your ideas.", Toast.LENGTH_SHORT).show();
   }
   copyAssets();
  } else {
  }
  AdRequest adRequest = new AdRequest.Builder()
   .build();
  AdView adView = (AdView) this.findViewById(R.id.adView);
  adView.loadAd(adRequest);
  String date3 = df.format(Calendar.getInstance().getTime());
  outputFile = Environment.getExternalStorageDirectory() + File.separator + "Idea Book" + "/Idea-" + date3 + ".3gp";

 }
 private void handleSendAudio(Intent intent) {
  Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
  if (imageUri != null) {
   saveFile(imageUri);
   Toast.makeText(getApplicationContext(), "Transferred successfully.", Toast.LENGTH_LONG).show();
  }

 }

 void saveFile(Uri sourceuri) {
  String sourceFilename = sourceuri.getPath();
  String destinationFilename = dir1 + "Idea Received-" + date + ".3gp";
  BufferedInputStream bis = null;
  BufferedOutputStream bos = null;

  try {
   bis = new BufferedInputStream(new FileInputStream(sourceFilename));
   bos = new BufferedOutputStream(new FileOutputStream(destinationFilename, false));
   byte[] buf = new byte[1024];
   bis.read(buf);
   do {
    bos.write(buf);
   } while (bis.read(buf) != -1);
  } catch (IOException e) {

  } finally {
   try {
    if (bis != null) bis.close();
    if (bos != null) bos.close();
   } catch (IOException e) {

   }
  }
 }


 public void start(View view) {
  try {
   String date = df.format(Calendar.getInstance().getTime());
   outputFile = Environment.getExternalStorageDirectory() + File.separator + "Idea Book" + "/Idea-" + date + ".3gp";
   myAudioRecorder = new MediaRecorder();
   myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
   myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
   myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
   myAudioRecorder.setOutputFile(outputFile);
   myAudioRecorder.prepare();
   myAudioRecorder.start();

   ImageView imgView = (ImageView) findViewById(R.id.imageView1);
   imgView.setClickable(false);
   imgView.startAnimation(animation);
   num++;
  } catch (IllegalStateException e) {
   e.printStackTrace();
  } catch (IOException e) {
   e.printStackTrace();
  }
  start.setEnabled(false);
  stop.setEnabled(true);
  play.setEnabled(false);
  view1.setEnabled(false);

  Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_SHORT).show();

 }

 public void stop(View view) {
  try {
   if (myAudioRecorder != null) {
    myAudioRecorder.stop();
    myAudioRecorder.release();
   } else {
    Toast.makeText(getApplicationContext(), "Can't stop. Leave a feedback with your Android OS version for testing.", Toast.LENGTH_LONG).show();
   }
   myAudioRecorder = null;
   stop.setEnabled(false);
   play.setEnabled(true);
   start.setEnabled(true);
   view1.setEnabled(true);
   view.clearAnimation();
   ImageView imgView = (ImageView) findViewById(R.id.imageView1);
   imgView.clearAnimation();
   Toast.makeText(getApplicationContext(), "Idea recorded successfully",
    Toast.LENGTH_SHORT).show();
   imgView.setClickable(true);
  } catch (IllegalStateException e) {
   e.printStackTrace();
  } catch (Exception e) {
   e.printStackTrace();
  }

 }
 @Override
 public boolean onCreateOptionsMenu(Menu menu) {
  MenuInflater inflater = getMenuInflater();
  inflater.inflate(R.menu.my_menu, menu);
  return true;
 }
 public boolean onOptionsItemSelected(MenuItem item) {
  switch (item.getItemId()) {
   case R.id.info:
    Log.w("I AM HERE ?!", "YES02");
    Intent infoIntent = new Intent(this, InformationActivity.class);
    startActivity(infoIntent);
    return true;
   case R.id.exit:
    Intent intent = new Intent(Intent.ACTION_MAIN);
    intent.addCategory(Intent.CATEGORY_HOME);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
    return true;
   default:
    return super.onOptionsItemSelected(item);
  }
 }
 public void play(View view) throws IllegalArgumentException,
  InvocationTargetException, SecurityException, IllegalStateException, IOException {

   File file = new File(outputFile);
   boolean deleted = file.delete();
   if (deleted == true) {
    Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
   } else {
    Toast.makeText(getApplicationContext(), "Can not be Deleted", Toast.LENGTH_LONG).show();
   }

   start.setEnabled(true);
   stop.setEnabled(false);
   play.setEnabled(false);
   view1.setEnabled(true);

  }
 public void sendMessage(View view) {
  Intent intent = new Intent(this, PlayActivity.class);
  startActivity(intent);
 }

 private void copyAssets() {
  AssetManager assetManager = this.getAssets();
  String[] files = null;
  try {
   files = assetManager.list("");
   for (String filename1: files) {
   }

  } catch (IOException e) {
  } catch (NullPointerException e) {
  }
  for (String filename: files) {
   if (filename.equalsIgnoreCase("Idea-Sample-Welcome.3gp")) {
    InputStream in = null;
    OutputStream out = null;
    try {
     in = assetManager.open(filename);
     File outFile = new File(dir1, filename);
     out = new FileOutputStream(outFile);
     copyFile( in , out); in .close();
     out.flush();
     out.close();
     out = null;
    } catch (IOException e) {
    }

   } else {
   }

  }
 }
 private void copyFile(InputStream in , OutputStream out) throws IOException {
  byte[] buffer = new byte[1024];
  int read;
  while ((read = in .read(buffer)) != -1) {
   out.write(buffer, 0, read);
  }
 }

 @Override
 public boolean dispatchTouchEvent(MotionEvent me) {
  this.detector.onTouchEvent(me);
  return super.dispatchTouchEvent(me);
 }
 @Override
 public void onSwipe(int direction) {
  String str = "";
  switch (direction) {

   case SimpleGestureFilter.SWIPE_RIGHT:
    str = "Swipe Right";
    Intent intent = new Intent(this, PlayActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    startActivity(intent);
    break;
   case SimpleGestureFilter.SWIPE_LEFT:
    str = "Swipe Left";
    Intent intent2 = new Intent(this, PlayActivity.class);
    intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    startActivity(intent2);
    break;
   case SimpleGestureFilter.SWIPE_DOWN:
    str = "Swipe Down";
    break;
   case SimpleGestureFilter.SWIPE_UP:
    str = "Swipe Up";
    break;
  }

 }
 @Override
 public void onDoubleTap() {
  Intent sharingIntent1 = new Intent(android.content.Intent.ACTION_SEND);
  sharingIntent1.setType("text/plain");
  sharingIntent1.setAction(Intent.ACTION_SEND);
  sharingIntent1.putExtra(android.content.Intent.EXTRA_SUBJECT, "#IdeaBook");
  sharingIntent1.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
  startActivity(Intent.createChooser(sharingIntent1, "Share via"));


 }

}