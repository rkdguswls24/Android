package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;



public class MainActivity extends AppCompatActivity
    implements OnMapReadyCallback {
    NaverMap myMap;
    Toast msg =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }


        mapFragment.getMapAsync(this);
    }
    public void mapchangeButt(View v){

        switch(v.getId())
        {
            case R.id.hybrid:
                msg.cancel();
                myMap.setMapType(NaverMap.MapType.Hybrid);
                msg=Toast.makeText(this,"Hybrid",Toast.LENGTH_SHORT);
                msg.show();

                break;
            case R.id.satelite:
                msg.cancel();
                myMap.setMapType(NaverMap.MapType.Satellite);
                msg=Toast.makeText(this,"satelite",Toast.LENGTH_SHORT);
                msg.show();
                break;
            case R.id.terrain:
                msg.cancel();
                myMap.setMapType(NaverMap.MapType.Terrain);
                msg=Toast.makeText(this,"terrain",Toast.LENGTH_SHORT);
                msg.show();
                break;
        }
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.myMap = naverMap;
        myMap.setMapType(NaverMap.MapType.Hybrid);
        LatLng coord = new LatLng(37,125);
        msg = Toast.makeText(this,"latitude: "+coord.latitude+"longitude: "+coord.longitude,Toast.LENGTH_SHORT);
        msg.show();
    }

}