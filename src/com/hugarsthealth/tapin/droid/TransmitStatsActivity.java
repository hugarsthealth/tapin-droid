package com.hugarsthealth.tapin.droid;

import org.json.JSONException;

import com.hugarsthealth.tapin.droid.model.Keys;
import com.hugarsthealth.tapin.droid.model.PatientModel;

import se761.bestgroup.vsm.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.MenuItem;

public class TransmitStatsActivity extends Activity implements
		CreateNdefMessageCallback {

	private NfcAdapter mNfcAdapter;
	private PatientModel _model;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_transmit);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		this.mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		this.mNfcAdapter.setNdefPushMessageCallback(this, this);

		final SharedPreferences sharedPreferences = getSharedPreferences(Keys.VSM, MODE_PRIVATE);
		final String jsonModel = sharedPreferences.getString(Keys.MODEL, null);
		this._model = new PatientModel();

		if (jsonModel != null) {
			Log.d("VSM", "Deserializing saved model");
			try {
				this._model.fromJSON(jsonModel);
				// set check in time.
				this._model.setCheckInTime();
			} catch (final JSONException e) {
				e.printStackTrace();
			}
		} else {
			Log.d("VSM", "Blank Model");
		}
	}

	@Override
	public NdefMessage createNdefMessage(final NfcEvent arg0) {
		final Time time = new Time();
		time.setToNow();

		final String packageName = Keys.PACKAGE;
		final String json = this._model.toJSON().toString();

		final NdefMessage msg = new NdefMessage(
				NdefRecord.createMime(packageName, json.getBytes()));
		Log.v("NDEF", json);
		Log.v("NDEF", msg.getRecords().length + "");
		return msg;
	}

	@Override
	protected void onPause() {
		super.onPause();
		this.mNfcAdapter.setNdefPushMessageCallback(null, this);
		Log.d("nfc", "being called");
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		final Intent parentActivityIntent = new Intent(this, MenuActivity.class);
		parentActivityIntent.addFlags(
				Intent.FLAG_ACTIVITY_CLEAR_TOP |
						Intent.FLAG_ACTIVITY_NEW_TASK);

		switch (item.getItemId()) {
			case android.R.id.home:
				// This is called when the Home (Up) button is pressed
				// in the Action Bar.
				startActivity(parentActivityIntent);
				finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
