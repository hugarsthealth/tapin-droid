package se761.bestgroup.vsm;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends Activity {

	private String _pin;
	private String _correctPin;
	private List<TextView> _textViews;
	private boolean _pinExists;
	private boolean _confirming;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// inflate the layout
		setContentView(R.layout.activity_login);

		this._textViews = new ArrayList<TextView>();
		this._textViews.add((TextView) findViewById(R.id.pin1));
		this._textViews.add((TextView) findViewById(R.id.pin2));
		this._textViews.add((TextView) findViewById(R.id.pin3));
		this._textViews.add((TextView) findViewById(R.id.pin4));
		this._textViews.add((TextView) findViewById(R.id.pin5));

		// Bind the button click
		final OnClickListener buttonClickListener = new OnClickListener() {
			@Override
			public void onClick(final View view) {
				final int id = view.getId();

				if (id == R.id.buttonBackspace) {
					if (LoginActivity.this._pin.length() == 0) {
						return;
					}
					LoginActivity.this._pin = LoginActivity.this._pin.substring(0, LoginActivity.this._pin.length() - 1);
				} else if (id == R.id.buttonForgotConfirmPin) {
					if (LoginActivity.this._pinExists) {
						LoginActivity.this._pin = LoginActivity.this._correctPin;
					} else {
						if (LoginActivity.this._confirming) {

							// Store the confirmed pin
							final Editor editor = getPreferences(MODE_PRIVATE).edit();
							editor.putString("pin", LoginActivity.this._pin);
							editor.apply();

							final Intent i = new Intent(getApplicationContext(),
									MenuActivity.class);
							startActivity(i);
						} else {
							LoginActivity.this._pin = "";
							LoginActivity.this._confirming = true;
							((Button) findViewById(R.id.buttonForgotConfirmPin))
									.setText("Confirm");
						}
					}
				} else if (LoginActivity.this._pin.length() == 5) {
					return; // The textviews are full
				}

				switch (id) {
					case R.id.button0:
						LoginActivity.this._pin += "0";
						break;
					case R.id.button1:
						LoginActivity.this._pin += "1";
						break;
					case R.id.button2:
						LoginActivity.this._pin += "2";
						break;
					case R.id.button3:
						LoginActivity.this._pin += "3";
						break;
					case R.id.button4:
						LoginActivity.this._pin += "4";
						break;
					case R.id.button5:
						LoginActivity.this._pin += "5";
						break;
					case R.id.button6:
						LoginActivity.this._pin += "6";
						break;
					case R.id.button7:
						LoginActivity.this._pin += "7";
						break;
					case R.id.button8:
						LoginActivity.this._pin += "8";
						break;
					case R.id.button9:
						LoginActivity.this._pin += "9";
						break;
				}

				for (final TextView tv : LoginActivity.this._textViews) {
					tv.setText("");
				}
				for (int i = 0; i < LoginActivity.this._pin.length(); i++) {
					LoginActivity.this._textViews.get(i).setText("" + LoginActivity.this._pin.charAt(i));
				}

				if (LoginActivity.this._pin.length() == 5) { // The textviews are full
					if (LoginActivity.this._pinExists) {
						if (LoginActivity.this._pin.equals(LoginActivity.this._correctPin)) {
							final Intent i = new Intent(getApplicationContext(),
									MenuActivity.class);
							startActivity(i);
						}
					} else {
						// Get them to confirm it
						((Button) findViewById(R.id.buttonForgotConfirmPin))
								.setEnabled(true);
					}
				} else {
					if (!LoginActivity.this._pinExists) {
						((Button) findViewById(R.id.buttonForgotConfirmPin))
								.setEnabled(false);
					}
				}
			}
		};

		((Button) findViewById(R.id.button0))
				.setOnClickListener(buttonClickListener);
		((Button) findViewById(R.id.button1))
				.setOnClickListener(buttonClickListener);
		((Button) findViewById(R.id.button2))
				.setOnClickListener(buttonClickListener);
		((Button) findViewById(R.id.button3))
				.setOnClickListener(buttonClickListener);
		((Button) findViewById(R.id.button4))
				.setOnClickListener(buttonClickListener);
		((Button) findViewById(R.id.button5))
				.setOnClickListener(buttonClickListener);
		((Button) findViewById(R.id.button6))
				.setOnClickListener(buttonClickListener);
		((Button) findViewById(R.id.button7))
				.setOnClickListener(buttonClickListener);
		((Button) findViewById(R.id.button8))
				.setOnClickListener(buttonClickListener);
		((Button) findViewById(R.id.button9))
				.setOnClickListener(buttonClickListener);
		((Button) findViewById(R.id.buttonBackspace))
				.setOnClickListener(buttonClickListener);
		((Button) findViewById(R.id.buttonForgotConfirmPin))
				.setOnClickListener(buttonClickListener);
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Load the actual pin from sharedpreferences
		final String storedPin = getPreferences(MODE_PRIVATE).getString("pin", null);

		// If the stored pin exists make that the one the user needs to enter
		this._pinExists = storedPin != null;
		if (this._pinExists) {
			this._correctPin = storedPin;
			findViewById(R.id.firstTimeContainer).setVisibility(View.INVISIBLE);
			((Button) findViewById(R.id.buttonForgotConfirmPin)).setText("FORGOT PIN");
		} else {

			findViewById(R.id.firstTimeContainer).setVisibility(View.VISIBLE);
			((Button) findViewById(R.id.buttonForgotConfirmPin)).setText("Go");
			((Button) findViewById(R.id.buttonForgotConfirmPin))
					.setEnabled(false);
			this._confirming = false;
			this._correctPin = "";
		}

		this._pin = "";

		for (final TextView tv : this._textViews) {
			tv.setText("");
		}
		for (int i = 0; i < this._pin.length(); i++) {
			this._textViews.get(i).setText("" + this._pin.charAt(i));
		}
	}
}
