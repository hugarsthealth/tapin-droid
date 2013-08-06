package com.hugarsthealth.tapin.droid.formpages;

import java.util.Arrays;

import com.hugarsthealth.tapin.droid.model.PatientModel;
import com.hugarsthealth.tapin.droid.model.PatientModel.BloodType;

import se761.bestgroup.vsm.R;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

public class Page2Fragment extends Fragment {

	private PatientModel _model;

	public Page2Fragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_model = (PatientModel) getArguments().get("model");
		setUserVisibleHint(true);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_page_2, container, false);
		
		// Restore the values out of the model
		final EditText contactNumber = (EditText) root.findViewById(R.id.contactNumber);
		contactNumber.setText("" + _model.getContactNumber());
		contactNumber.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				try{
					_model.setContactNumber(s.toString().trim());
					contactNumber.setError(null);
				}catch (IllegalArgumentException e){
					contactNumber.setError("Non numeric characters aren't allowed.");
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Switch citizenOrResident = (Switch) root.findViewById(R.id.citizenOrResident);
		citizenOrResident.setChecked(_model.isCitizenOrResident());
		citizenOrResident.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				_model.setCitizenOrResident(arg1);
			}
		});
		
		final EditText weightInput = (EditText) root.findViewById(R.id.weight);
		weightInput.setText("" + _model.getWeight());
		weightInput.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				try{
					_model.setWeight(Double.parseDouble(weightInput.getText().toString()));
					weightInput.setError(null);
				}catch(IllegalArgumentException e){
					weightInput.setError("Weight must be greater than 0kg and less than 1000kg");
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {}
			
			@Override
			public void afterTextChanged(Editable arg0) {}
		});
		
		final EditText heightInput = (EditText) root.findViewById(R.id.height);
		heightInput.setText("" + _model.getHeight());
		heightInput.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				try{
					_model.setHeight(Double.parseDouble(heightInput.getText().toString()));
					heightInput.setError(null);
				}catch(IllegalArgumentException e){
					heightInput.setError("Height must be greater than 0cm and less than 300cm");
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {}
			
			@Override
			public void afterTextChanged(Editable arg0) {}
		});
		
		// Set up the blood type spinner
		final Spinner bloodTypeSpinner = (Spinner) root.findViewById(R.id.bloodType);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.bloodTypes, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		bloodTypeSpinner.setAdapter(adapter);
		bloodTypeSpinner.setSelection(Arrays.asList(getResources().getStringArray(R.array.bloodTypes)).indexOf(_model.getBloodType().toString()));

		bloodTypeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				_model.setBloodType(BloodType.lookup((String)bloodTypeSpinner.getItemAtPosition(position)));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});

		Switch smoker = (Switch) root.findViewById(R.id.smoker);
		smoker.setChecked(_model.isSmoker());
		smoker.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				_model.setSmoker(arg1);
			}
		});
		Switch drinker = (Switch) root.findViewById(R.id.drinker);
		drinker.setChecked(_model.isDrinker());
		drinker.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				_model.setDrinker(arg1);
			}
		});
		
		return root;
	}


	public static Page2Fragment newInstance(PatientModel model) {
		Page2Fragment newFragment = new Page2Fragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable("model", model);
		newFragment.setArguments(bundle);
		return newFragment;
	}
}
