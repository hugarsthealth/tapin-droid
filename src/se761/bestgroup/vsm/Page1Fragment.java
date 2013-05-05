package se761.bestgroup.vsm;

import java.util.Arrays;

import se761.bestgroup.vsm.PatientModel.Gender;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Page1Fragment extends Fragment {

	private PatientModel _model;

	public Page1Fragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_model = (PatientModel) getArguments().get("model");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Inflate the first page layout
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_page_1,
				container, false);

		// Set up the edit texts
		final EditText firstNameInput = (EditText) root
				.findViewById(R.id.firstName);
		firstNameInput.setText(_model.getFirstName());
		firstNameInput.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				try {
					_model.setFirstName(s.toString().trim());
					firstNameInput.setError(null);
				} catch (IllegalArgumentException e) {
					firstNameInput
							.setError("Whitespace or non alphabetic characters are not allowed");
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		final EditText lastNameInput = (EditText) root
				.findViewById(R.id.lastName);
		lastNameInput.setText(_model.getLastName());
		lastNameInput.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				try {
					Log.d("VSM", "Editing Lastname");
					_model.setLastName(s.toString().trim());
					lastNameInput.setError(null);
				} catch (IllegalArgumentException e) {
					lastNameInput
							.setError("Whitespace or non alphabetic characters are not allowed");
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		final EditText occupationInput = (EditText) root
				.findViewById(R.id.occupation);
		occupationInput.setText(_model.getOccupation());
		occupationInput.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				try {
					_model.setOccupation(s.toString().trim());
					occupationInput.setError(null);
				} catch (IllegalArgumentException e) {
					occupationInput
							.setError("Whitespace or non alphabetic characters are not allowed");
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		final EditText nhiInput = (EditText) root.findViewById(R.id.nhi);
		nhiInput.setText(_model.getNhiNumber());
		nhiInput.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				try {
					_model.setNHINumber(s.toString().trim());
					nhiInput.setError(null);
				} catch (IllegalArgumentException e) {
					nhiInput.setError("Whitespace or non alphabetic characters are not allowed");
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		// Set up the gender spinner
		final Spinner genderSpinner = (Spinner) root.findViewById(R.id.gender);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				getActivity(), R.array.genders, R.layout.custom_spinner_list);
		adapter.setDropDownViewResource(R.layout.custom_spinner);

		genderSpinner.setAdapter(adapter);
		genderSpinner.setSelection(Arrays.asList(
				getResources().getStringArray(R.array.genders)).indexOf(
				_model.getGender().toString()));

		genderSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d("VSM", "Setting gender");
				_model.setGender(Gender.valueOf((String) genderSpinner
						.getItemAtPosition(position)));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		final TextView dateOfBirth = (TextView) root.findViewById(R.id.dob);
		dateOfBirth.setText(_model.getDob());
		
		
		Button changeDobButton = (Button) root
				.findViewById(R.id.changeDobButton);

		changeDobButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String[] date = _model.getDob().split("/");
				int day = Integer.parseInt(date[0]);
				int month = Integer.parseInt(date[1]);
				int year = Integer.parseInt(date[2]);
				Log.d("VSM", day + "/" + month + "/" + year);
				new DatePickerDialog(getActivity(), new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						_model.setDob(dayOfMonth+ "/" + (monthOfYear + 1) + "/" + year);
						dateOfBirth.setText(dayOfMonth+ "/" + (monthOfYear + 1) + "/" +year );
						
					}
				}, year, month - 1, day).show();
			}
		});

		return root;
	}

	@Override
	public void onPause() {
		super.onPause();

		// Save the state?
	}

	public static Page1Fragment newInstance(PatientModel model) {
		Page1Fragment newFragment = new Page1Fragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable("model", model);
		newFragment.setArguments(bundle);
		return newFragment;
	}
}