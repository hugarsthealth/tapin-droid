package com.hugarsthealth.tapin.droid.formpages;

import java.util.ArrayList;

import com.hugarsthealth.tapin.droid.CountriesListDialogFragment;
import com.hugarsthealth.tapin.droid.CountriesListDialogFragment.CountriesListDialogListener;
import com.hugarsthealth.tapin.droid.model.PatientModel;

import se761.bestgroup.vsm.R;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class Page4Fragment extends Fragment implements
		CountriesListDialogListener {

	private PatientModel _model;
	private CountriesListDialogFragment _selectCountriesDialog;
	private ArrayAdapter<String> _adapter;

	public Page4Fragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_model = (PatientModel) getArguments().get("model");
		_adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1);
		setUserVisibleHint(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_page_4,
				container, false);

		// Set up the countries selection dialog

		_selectCountriesDialog = new CountriesListDialogFragment();
		_selectCountriesDialog.addListener(this);

		Button editCountriesButton = (Button) root
				.findViewById(R.id.editCountriesButton);
		editCountriesButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Page4Fragment.this._selectCountriesDialog.show(
						getFragmentManager(), "VSM");

			}
		});

		ListView countriesListView = (ListView) root
				.findViewById(R.id.countriesListView);
		countriesListView.setAdapter(_adapter);
		_adapter.addAll(_model.getRecentCountries());

		return root;
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	public static Page4Fragment newInstance(PatientModel model) {
		Page4Fragment newFragment = new Page4Fragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable("model", model);
		newFragment.setArguments(bundle);
		return newFragment;
	}

	@Override
	public void onPositiveClick(CountriesListDialogFragment dialog) {
		_adapter.clear();
		_model.getRecentCountries().clear(); 
		ArrayList<Integer> selectedCountries = _selectCountriesDialog
				.getSelectedCountries();
		
		String[] countries = getResources().getStringArray(R.array.countries);
		for (Integer i : selectedCountries) {
			_adapter.add(countries[i]);
			_model.getRecentCountries().add(countries[i]);

			Log.d("VSM", "Number of recent COuntries: " + _model.getRecentCountries().size());
		}
	}

	@Override
	public void onNegativeClick(CountriesListDialogFragment dialog) {

	}
	

}
