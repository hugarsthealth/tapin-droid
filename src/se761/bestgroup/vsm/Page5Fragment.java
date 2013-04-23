package se761.bestgroup.vsm;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Page5Fragment extends Fragment {
	
	private PatientModel _model;

	public Page5Fragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_model = (PatientModel) getArguments().get("model");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_page_5, container, false);
		
		return root;
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}

	public static Page5Fragment newInstance(PatientModel model) {
		Page5Fragment newFragment = new Page5Fragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable("model", model);
		newFragment.setArguments(bundle);
		return newFragment;
	}
}
