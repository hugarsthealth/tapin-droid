package se761.bestgroup.vsm;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class MainActivity extends ListActivity {


	private ArrayAdapter<String> adapter;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		setListAdapter(adapter);
		
		registerForContextMenu(getListView());
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    

    public boolean onActionAdd(MenuItem item){
    	Intent i = new Intent(this, NewItemActivity.class);
    	startActivityForResult(i, 1);
    	return false;
    }
    
    public boolean onActionRefresh(MenuItem item){
    	
    	return false;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	String firstName = data.getStringExtra("firstName");
    	String lastName = data.getStringExtra("lastName");
    	adapter.add(firstName + " " + lastName);
    	Toast.makeText(this, "First name: " + firstName + ", Last name: " + lastName, Toast.LENGTH_LONG).show();
    	
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
    		ContextMenuInfo menuInfo) {

    	super.onCreateContextMenu(menu, v, menuInfo);
    	
    	Toast.makeText(this, "SNAG", Toast.LENGTH_SHORT).show();
    	
    }
    
}
