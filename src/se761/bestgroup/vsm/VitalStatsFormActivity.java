package se761.bestgroup.vsm;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import se761.bestgroup.vsm.formpages.Page1Fragment;
import se761.bestgroup.vsm.formpages.Page2Fragment;
import se761.bestgroup.vsm.formpages.Page3Fragment;
import se761.bestgroup.vsm.formpages.Page4Fragment;
import se761.bestgroup.vsm.formpages.Page5Fragment;
import se761.bestgroup.vsm.model.Keys;
import se761.bestgroup.vsm.model.PatientModel;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class VitalStatsFormActivity extends Activity {

	private ViewPager _viewPager;
	private PagerAdapter _pagerAdapter;
	private PatientModel _model;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_view_pager);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		final SharedPreferences sharedPreferences = getSharedPreferences(Keys.VSM,
				MODE_PRIVATE);

		final String jsonString = sharedPreferences.getString(Keys.MODEL,
				null);

		this._model = new PatientModel();

		if (jsonString != null) {
			Log.d("VSM", "Deserializing saved model");

			try {
				this._model.fromJSON(jsonString);

			} catch (final JSONException e) {
				e.printStackTrace();
			}
		} else {
			Log.d("VSM", "Blank Model");
		}

		this._viewPager = (ViewPager) findViewById(R.id.pager);
		this._pagerAdapter = new SliderAdapter(getFragmentManager());
		this._viewPager.setAdapter(this._pagerAdapter);
		this._viewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(final int position) {
						VitalStatsFormActivity.this.setTitle("Page "
								+ (position + 1) + "/5");
						invalidateOptionsMenu();
					}
				});
		setTitle("Page 1/5");
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.activity_screen_slide, menu);

		menu.findItem(R.id.action_previous).setEnabled(
				this._viewPager.getCurrentItem() > 0);

		// Add either a "next" or "finish" button to the action bar, depending
		// on which page
		// is currently selected.
		final MenuItem item = menu
				.add(Menu.NONE, (this._viewPager.getCurrentItem() == this._pagerAdapter
						.getCount() - 1) ? R.id.action_finish
						: R.id.action_next, 2,
						(this._viewPager.getCurrentItem() == this._pagerAdapter
								.getCount() - 1) ? R.string.action_finish
								: R.string.action_next);
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM
				| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return true;
	}

	@Override
	protected void onPause() {
		super.onPause();

		// Save the model's state
		final Editor preferencesEditor = getSharedPreferences(Keys.VSM, MODE_PRIVATE)
				.edit();
		Log.i("VSMOnPause", this._model.toJSON().toString());
		preferencesEditor.putString(Keys.MODEL, this._model.toJSON()
				.toString());
		preferencesEditor.commit();
		Log.d("VSM", "Serializing and saving model");
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {

		final Intent parentActivityIntent = new Intent(this, MenuActivity.class);
		parentActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);

		switch (item.getItemId()) {

			case R.id.action_previous:
				// Go to the previous step in the wizard. If there is no previous
				// step,
				// setCurrentItem will do nothing.
				this._viewPager.setCurrentItem(this._viewPager.getCurrentItem() - 1);
				return true;

			case R.id.action_next:
				// Advance to the next step in the wizard. If there is no next step,
				// setCurrentItem
				// will do nothing.
				this._viewPager.setCurrentItem(this._viewPager.getCurrentItem() + 1);
				return true;

			case R.id.action_finish:

				startActivity(parentActivityIntent);
				finish();
				return true;

			case android.R.id.home:
				// This is called when the Home (Up) button is pressed
				// in the Action Bar.
				startActivity(parentActivityIntent);
				finish();
				return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private class SliderAdapter extends FragmentPagerAdapter {

		private final List<Fragment> _pages;

		public SliderAdapter(final FragmentManager fragmentManager) {
			super(fragmentManager);
			this._pages = new ArrayList<Fragment>();

			this._pages.add(Page1Fragment.newInstance(VitalStatsFormActivity.this._model));
			this._pages.add(Page2Fragment.newInstance(VitalStatsFormActivity.this._model));
			this._pages.add(Page3Fragment.newInstance(VitalStatsFormActivity.this._model));
			this._pages.add(Page4Fragment.newInstance(VitalStatsFormActivity.this._model));
			this._pages.add(Page5Fragment.newInstance(VitalStatsFormActivity.this._model));

		}

		@Override
		public Fragment getItem(final int page) {
			return this._pages.get(page);
		}

		@Override
		public int getCount() {
			return this._pages.size();
		}

	}

}
