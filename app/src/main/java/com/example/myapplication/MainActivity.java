package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.view.View;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.FusedLocationSource;


public class MainActivity extends AppCompatActivity
    implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE=1000;
    private FusedLocationSource locationSource;
    private LinearLayout linemenu;
    private Button mode_btn;

    private NaverMap myMap;
    Toast msg =null;
    private boolean clicked = false;
    private boolean menulist=false;



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
        locationSource =
                new FusedLocationSource(this,LOCATION_PERMISSION_REQUEST_CODE);


        linemenu = (LinearLayout)findViewById(R.id.linelayer);
        mode_btn = (Button)findViewById(R.id.mode);
        linemenu.setVisibility(View.INVISIBLE);
        mode_btn.setOnClickListener(new Button.OnClickListener(){
           @Override
           public void onClick(View v){
               if(menulist) {
                   menulist = !menulist;
                   linemenu.setVisibility(View.INVISIBLE);
               }
               else{
                   menulist = !menulist;
                   linemenu.setVisibility(View.VISIBLE);
               }
           }
        });

        mapFragment.getMapAsync(this);
    }


    public void btnEvent(String str){
        toastmsg(str);
        mode_btn.setText(str);
        linemenu.setVisibility(View.INVISIBLE);
        menulist= false;
    }


    public void mapchangeButt(View v){

        switch(v.getId())
        {
            case R.id.hybrid:
                myMap.setMapType(NaverMap.MapType.Hybrid);
                btnEvent("hybrid");
                break;
            case R.id.satelite:
                myMap.setMapType(NaverMap.MapType.Satellite);
                btnEvent("satellite");
                break;
            case R.id.terrain:
                myMap.setMapType(NaverMap.MapType.Terrain);
                btnEvent("terrain");
                break;
            case R.id.cadastral:
                clicked = !clicked;
                toastmsg("cadastral");
                Button btn = (Button)v;
                if(clicked)
                    btn.setText("Off");
                else
                    btn.setText("cadast");
                myMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_CADASTRAL,clicked);

                break;
        }
    }

    public void toastmsg(String str){
        msg.cancel();
        msg=Toast.makeText(this,str,Toast.LENGTH_SHORT);
        msg.show();
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.myMap = naverMap;
        msg=Toast.makeText(this,"hybrid",Toast.LENGTH_SHORT);
        msg.show();
        myMap.setMapType(NaverMap.MapType.Hybrid);
        LatLng coord1 = new LatLng(35.945378,126.682110);


        myMap = naverMap;
        myMap.setLocationSource(locationSource);
        myMap.setLocationTrackingMode(LocationTrackingMode.Follow);


        Marker marker1 = new Marker();
        Marker marker2 = new Marker();
        Marker marker3 = new Marker();

        marker1.setPosition(coord1);
        marker1.setMap(myMap);
        marker2.setPosition(new LatLng(35.967509,126.736971));
        marker2.setMap(myMap);
        marker3.setPosition(new LatLng(35.942719,126.726729));
        marker3.setMap(myMap);


        marker1.setTag("군산대학교");
        marker2.setTag("군산시청");
        marker3.setTag("마커3");

        InfoWindow infoWindow = new InfoWindow();

        infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(this) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                return (CharSequence)infoWindow.getMarker().getTag();
            }
        });





        naverMap.setOnMapClickListener((coord, point) -> {
            infoWindow.close();
        });

// 마커를 클릭하면:
        Overlay.OnClickListener listener = overlay -> {
            Marker marker = (Marker)overlay;

            if (marker.getInfoWindow() == null) {
                // 현재 마커에 정보 창이 열려있지 않을 경우 엶
                infoWindow.open(marker);
            } else {
                // 이미 현재 마커에 정보 창이 열려있을 경우 닫음
                infoWindow.close();
            }

            return true;
        };

        marker1.setOnClickListener(listener);
        marker2.setOnClickListener(listener);
        marker3.setOnClickListener(listener);



    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,  @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated()) { // 권한 거부됨
                myMap.setLocationTrackingMode(LocationTrackingMode.None);
            }
            return;
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
    }




}