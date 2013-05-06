package com.fun_with_beam;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * User: tomerweller
 * Date: 4/22/13
 * Time: 2:46 PM
 */
public class BeamRecieverActivity extends Activity{
    TextView textView;
    ImageView image_view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        textView = (TextView) findViewById(R.id.textview);
        image_view = (ImageView)findViewById(R.id.imageview);
        textView.setText("Waiting...");
    }

    @Override
    public void onResume() {
        super.onResume();
        // Check to see that the Activity started due to an Android Beam
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            processIntent(getIntent());
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        // onResume gets called after this to handle the intent
        setIntent(intent);
    }

    /**
     * Parses the NDEF Message from the intent and prints to the TextView
     */
    void processIntent(Intent intent) {
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        // only one message sent during the beam
        NdefMessage msg = (NdefMessage) rawMsgs[0];
        // record 0 contains the MIME type, record 1 is the AAR, if present
        final String imageLocation=new String(msg.getRecords()[0].getPayload());
        loadImage(imageLocation);
        textView.setText(new String(msg.getRecords()[0].getPayload()));
    }
    Bitmap bitmap;
    void loadImage(String image_location){
    	 
          URL imageURL = null;
          
          try {
        	  imageURL = new URL(image_location);
          	} 
          
          catch (MalformedURLException e) {
              e.printStackTrace();
          	}
          
          try {
        	  HttpURLConnection connection= (HttpURLConnection)imageURL.openConnection();
        	  connection.setDoInput(true);
        	  connection.connect();
              InputStream inputStream = connection.getInputStream();
               
              bitmap = BitmapFactory.decodeStream(inputStream);//Convert to bitmap
            //  image_view.setImageBitmap(bitmap);
              Intent intent = new Intent(this, FullImage.class);
              intent.putExtra("image", bitmap);
              startActivity(intent);
          }
          catch (IOException e) {
              
               e.printStackTrace();
          }
     }
}