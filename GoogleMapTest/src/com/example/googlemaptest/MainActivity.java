package com.example.googlemaptest;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
	
	private GoogleMap googlemap;
	private Activity coreactivity = this;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        try {
            // Loading map
            InitializeMap();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        googlemap.setMyLocationEnabled(true);
        googlemap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
			
			@Override
			public void onMapClick(LatLng point) {
				Toast.makeText(coreactivity, ""+point , Toast.LENGTH_SHORT).show();
			}
		});
        
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private void InitializeMap(){
    	if(googlemap == null){
    		
    		googlemap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
    		
    		// check if map is created successfully or not
            if (googlemap == null)
                Toast.makeText(getApplicationContext(),"Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
    	}
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	InitializeMap();
    }
}
