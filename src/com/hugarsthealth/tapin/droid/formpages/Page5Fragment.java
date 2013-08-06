package com.hugarsthealth.tapin.droid.formpages;

import com.hugarsthealth.tapin.droid.AddAllergyDialogFragment;
import com.hugarsthealth.tapin.droid.AddAllergyDialogFragment.AddAlergyDiaglogListener;
import com.hugarsthealth.tapin.droid.model.PatientModel;

import se761.bestgroup.vsm.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Page5Fragment extends Fragment {

	private PatientModel _model;
	private ArrayAdapter<String> _adapter;
	private int _positionLongClicked;
	private ListView _listView;

	public Page5Fragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		_model = (PatientModel) getArguments().get("model");
		setHasOptionsMenu(true);
		setRetainInstance(true);
		setUserVisibleHint(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_page_5,
				container, false);

		_listView = (ListView) root.findViewById(R.id.allergiesListView);
		_adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1);

		_listView.setAdapter(_adapter);
		_adapter.addAll(_model.getAllergies());

		return root;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		registerForContextMenu(_listView);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		_positionLongClicked = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;
		menu.add(Menu.NONE, R.id.action_delete_allergy, Menu.NONE, "Delete");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_delete_allergy) {
			_model.getAllergies()
					.remove(_adapter.getItem(_positionLongClicked));
			_adapter.remove(_adapter.getItem(_positionLongClicked));
		}
		return true;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		MenuItem addAllergy = menu.add(Menu.NONE, R.id.action_add_allergy, 0,
				"");
		addAllergy.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		addAllergy.setIcon(R.drawable.content_new);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_add_allergy) {
			AddAllergyDialogFragment dialog = new AddAllergyDialogFragment();
			dialog.setAddAlergyDialogListener(new AddAlergyDiaglogListener() {

				@Override
				public void onPositiveClick(String value) {
					
					if (value == null || value.compareTo("") == 0) {
						Toast.makeText(Page5Fragment.this.getActivity(),
								"You can't add a blank allergy",
								Toast.LENGTH_LONG).show();
					} else {
						try {
							_model.addAllergy(value);
							_adapter.add(value);
						} catch (IllegalArgumentException e) {
							Toast.makeText(Page5Fragment.this.getActivity(),
									"You can't use semicolons",
									Toast.LENGTH_LONG).show();
						}
					}
				}
			});
			dialog.show(getFragmentManager(), "AddAlergyDialog");
		}
		return true;
	}

	public static Page5Fragment newInstance(PatientModel model) {
		Page5Fragment newFragment = new Page5Fragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable("model", model);
		newFragment.setArguments(bundle);
		return newFragment;
	}
}
