package com.hugarsthealth.tapin.droid.formpages;

import com.hugarsthealth.tapin.droid.model.PatientModel;

import se761.bestgroup.vsm.R;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Page3Fragment extends Fragment {
	
	private PatientModel _model;

	public Page3Fragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_model = (PatientModel) getArguments().get("model");
		setUserVisibleHint(true);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_page_3, container, false);
		
		TextView familyHistory = (TextView) root.findViewById(R.id.familyHistory);
		familyHistory.setText(_model.getFamilyHistory());
		familyHistory.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
				_model.setFamilyHistory(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {}
			
			@Override
			public void afterTextChanged(Editable arg0) {}
		});
		
		TextView medicalConditions = (TextView) root.findViewById(R.id.medicalConditions);
		medicalConditions.setText(_model.getMedicalConditions());
		medicalConditions.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
				_model.setMedicalConditions(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
			}
		});
		return root;
	}
	

	public static Page3Fragment newInstance(PatientModel model) {
		Page3Fragment newFragment = new Page3Fragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable("model", model);
		newFragment.setArguments(bundle);
		return newFragment;
	}
}
