package se761.bestgroup.vsm;

import se761.bestgroup.vsm.model.PatientModel;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class AddAllergyDialogFragment extends DialogFragment {

	private AddAlergyDiaglogListener _listener;

	public interface AddAlergyDiaglogListener {
		void onPositiveClick(String value);
	}

	@Override
	public Dialog onCreateDialog(final Bundle savedInstanceState) {

		final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		final LayoutInflater layoutInflater = getActivity().getLayoutInflater();
		final View view = layoutInflater.inflate(R.layout.add_allergy_dialog, null);
		final EditText alergyInput = (EditText) view
				.findViewById(R.id.addAlergyInput);
		builder.setView(view).setTitle("Add an Allergy")
				.setPositiveButton("Add", new OnClickListener() {

					@Override
					public void onClick(final DialogInterface dialog, final int which) {
						AddAllergyDialogFragment.this._listener.onPositiveClick(alergyInput.getText()
								.toString());
					}
				}).setNegativeButton("Cancel", new OnClickListener() {

					@Override
					public void onClick(final DialogInterface dialog, final int which) {
						getDialog().cancel();
						// Same as pressing the back button
					}
				});

		final AlertDialog dialog = builder.create();
		alergyInput.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(final Editable s) {
			}

			@Override
			public void beforeTextChanged(final CharSequence s, final int start, final int count,
					final int after) {
			}

			@Override
			public void onTextChanged(final CharSequence s, final int start, final int before,
					final int count) {
				if (PatientModel.isValidAllergy(s.toString())) {
					alergyInput.setError(null);
					dialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(
							true);
				} else {
					dialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(
							false);
					alergyInput.setError("You can't use semicolons");
				}
			}

		});

		return dialog;
	}

	public void setAddAlergyDialogListener(final AddAlergyDiaglogListener listener) {
		this._listener = listener;

	}
}
