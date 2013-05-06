package com.fun_with_beam;

import android.app.Activity;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

/**
 * User: tomerweller
 * Date: 4/22/13
 * Time: 2:46 PM
 */
public class BeamSenderActivity extends Activity implements NfcAdapter.CreateNdefMessageCallback {
    NfcAdapter mNfcAdapter;
    TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        textView = (TextView) findViewById(R.id.textview);
        textView.setText("http://aux.shenkar.ac.il/media/dev/g2/dev_62/exercises/32.jpg");
        // Check for available NFC Adapter
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        //TODO: also check if enabled: mNfcAdapter.isEnabled()

        // Register callback
        mNfcAdapter.setNdefPushMessageCallback(this, this);
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        String text = ("Beam me up, Android!\n\n" +
                "Beam Time: " + System.currentTimeMillis());
        NdefMessage msg = new NdefMessage(
                new NdefRecord[] {
                        NdefRecord.createMime("application/com.fun_with_beam", text.getBytes())
                });
        return msg;
    }

    /**
     * The Android Application Record (AAR). Optional.
     *
     *,NdefRecord.createApplicationRecord("com.fun_with_beam")
     **/

}