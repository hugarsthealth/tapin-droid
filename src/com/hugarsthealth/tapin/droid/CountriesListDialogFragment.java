package com.hugarsthealth.tapin.droid;

import java.util.ArrayList;

import se761.bestgroup.vsm.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class CountriesListDialogFragment extends DialogFragment {

	private ArrayList<Integer> _selectedItems;
	private final ArrayList<CountriesListDialogListener> _listeners;
	private boolean[] _checked;

	public CountriesListDialogFragment() {
		this._selectedItems = new ArrayList<Integer>(); // Where we track the
		// selected items
		this._listeners = new ArrayList<CountriesListDialogFragment.CountriesListDialogListener>();

	}

	public interface CountriesListDialogListener {
		public void onPositiveClick(CountriesListDialogFragment dialog);

		public void onNegativeClick(CountriesListDialogFragment dialog);
	}

	public void addListener(final CountriesListDialogListener listener) {
		this._listeners.add(listener);
	}

	public void removeListener(final CountriesListDialogListener listener) {
		this._listeners.remove(listener);
	}

	@Override
	public Dialog onCreateDialog(final Bundle savedInstanceState) {
		final boolean[] checkedOriginal = new boolean[getResources().getStringArray(
				R.array.countries).length];
		if (this._checked != null) {
			for (int i = 0; i < this._checked.length; i++) {
				checkedOriginal[i] = this._checked[i];
			}
		}
		final ArrayList<Integer> selectedOriginal = new ArrayList<Integer>(
				this._selectedItems);

		final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		if (this._checked == null) {
			this._checked = new boolean[getResources().getStringArray(
					R.array.countries).length];
		}
		// Set the dialog title
		builder.setTitle(R.string.pickCountries)
				// Specify the list array, the items to be selected by default
				// (null for none),
				// and the listener through which to receive callbacks when
				// items are selected
				.setMultiChoiceItems(R.array.countries, this._checked,
						new DialogInterface.OnMultiChoiceClickListener() {
							@Override
							public void onClick(final DialogInterface dialog,
									final int which, final boolean isChecked) {
								if (isChecked) {
									// If the user checked the item, add it to
									// the selected items
									CountriesListDialogFragment.this._selectedItems.add(which);
									CountriesListDialogFragment.this._checked[which] = true;
								} else if (CountriesListDialogFragment.this._selectedItems.contains(which)) {
									// Else, if the item is already in the
									// array, remove it
									CountriesListDialogFragment.this._selectedItems.remove(Integer
											.valueOf(which));
									CountriesListDialogFragment.this._checked[which] = false;
								}
							}
						})
				// Set the action buttons
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(final DialogInterface dialog, final int id) {
						// User clicked OK, so save the mSelectedItems results
						// somewhere
						// or return them to the component that opened the
						// dialog
						for (final CountriesListDialogListener listener : CountriesListDialogFragment.this._listeners) {
							listener.onPositiveClick(CountriesListDialogFragment.this);
						}

					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(final DialogInterface dialog, final int id) {
								CountriesListDialogFragment.this._selectedItems = selectedOriginal;
								CountriesListDialogFragment.this._checked = checkedOriginal;
								for (final CountriesListDialogListener listener : CountriesListDialogFragment.this._listeners) {
									listener.onNegativeClick(CountriesListDialogFragment.this);
								}
							}
						});

		return builder.create();
	}

	public ArrayList<Integer> getSelectedCountries() {
		return this._selectedItems;
	}
}
