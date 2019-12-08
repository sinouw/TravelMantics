package ke.com.yass.travelmantics;

import  androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Localisation extends AppCompatActivity implements OnMapReadyCallback {
  GoogleMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localisation);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync((OnMapReadyCallback) this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        LatLng Nabeul= new LatLng(36.436919, 10.679200);
        map.addMarker(new MarkerOptions().position(Nabeul).title("Restaurant El Mrezga"));

        map.moveCamera(CameraUpdateFactory.newLatLng(Nabeul));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(Nabeul,10));

    }
}
