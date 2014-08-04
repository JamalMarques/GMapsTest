package com.example.googlemaptest;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

//import android.support.v4.widget.SearchViewCompatIcs.MySearchView;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements /*OnMarkerClickListener,*/ OnInfoWindowClickListener{
	
	private GoogleMap googlemap;
	private Activity coreactivity = this;
	
	private Marker markOrigin,markDestiny;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        try {
            // Loading map
            InitializeMap();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        googlemap.setMyLocationEnabled(true);
        googlemap.getUiSettings().setMyLocationButtonEnabled(false);
        googlemap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
			
			@Override
			public void onMapClick(LatLng point) {
				Toast.makeText(coreactivity, ""+point , Toast.LENGTH_SHORT).show();
			}
		});
        
        googlemap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
			
			@Override
			public void onMapLongClick(LatLng point) {
				
				if( markOrigin == null ){
					final MarkerOptions marker = new MarkerOptions().position(new LatLng(point.latitude, point.longitude))
															  .title("Origin")
															  .snippet("Origin Location")
															  .draggable(true)
															  .alpha((float) 0.7)
															  .icon(BitmapDescriptorFactory.fromResource(R.drawable.markerorigen));
					CharSequence options[] = new CharSequence[]{"Cancel","Origin"};
					AlertDialog.Builder builder = new Builder(coreactivity);
					builder.setTitle("Do you want set it as Origin?");
					builder.setItems(options, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if(which == 1){
								markOrigin = googlemap.addMarker(marker);
							}
						}
					});				
					builder.show();
				}else{  //origin exist!
					if(markDestiny == null ){
							final MarkerOptions marker = new MarkerOptions().position(new LatLng(point.latitude, point.longitude))
									  .title("Destiny")
									  .snippet("Destiny Location")
									  .draggable(true)
									  .alpha((float) 0.7)
									  .icon(BitmapDescriptorFactory.fromResource(R.drawable.markerdestino));
							CharSequence options[] = new CharSequence[]{"Cancel","Destiny"};
							AlertDialog.Builder builder = new Builder(coreactivity);
							builder.setTitle("Do you want set it as Destiny?");
							builder.setItems(options, new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								if(which == 1){
									markDestiny = googlemap.addMarker(marker);
								}
							}
							});		
							builder.show();
						}
				}
			}
		});
        
        //googlemap.setOnMarkerClickListener(this);
        googlemap.setOnInfoWindowClickListener(this);
        
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
    		googlemap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(-38.010481, -57.553307) , 13.0f) );  //Default ubication
    		
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


	/*@Override
	public boolean onMarkerClick(Marker marker) {
		
		
		return false; //for continue executing
	}*/


	@Override
	public void onInfoWindowClick(Marker marker) {
		
		if( markOrigin != null ){
			if( marker.getId().equals(markOrigin.getId()) ){ //reselect the origin point!
				Toast.makeText(this, "Marker Origin", Toast.LENGTH_SHORT).show();
				CharSequence options[] = new CharSequence[]{"Cancel","Reselect"};
				AlertDialog.Builder builder = new Builder(coreactivity);
				builder.setTitle("Do you want reselect the origin marker?");
				builder.setItems(options, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(which == 1){
						markOrigin.remove();
						markOrigin = null;
					}
				}
				});		
				builder.show();
			}
		}
		
		if( markDestiny != null){
			if( marker.getId().equals(markDestiny.getId()) ){ //reselect the destiny point!
				CharSequence options[] = new CharSequence[]{"Cancel","Reselect"};
				AlertDialog.Builder builder = new Builder(coreactivity);
				builder.setTitle("Do you want reselect the destiny marker?");
				builder.setItems(options, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(which == 1){
						markDestiny.remove();
						markDestiny = null;
					}
				}
				});		
				builder.show();
			}
		}
		
	}
	
	/*private void centerMapOnMyLocation() {

		LatLng myLocation;
	    googlemap.setMyLocationEnabled(true);

	    Location location = googlemap.getMyLocation();

	    if (location != null) {
	         myLocation = new LatLng(location.getLatitude(),location.getLongitude());
	    }
	    googlemap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation,
	            Constants.MAP_ZOOM));
	}*/
}
