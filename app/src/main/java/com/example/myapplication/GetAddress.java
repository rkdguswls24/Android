package com.example.myapplication;

import android.icu.text.IDNA;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.gson.JsonParser;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

public class GetAddress extends AsyncTask<LatLng,Void,String> {
    int value;
    LatLng point;


    protected void onPreExecute(){
    }
    protected String doInBackground(LatLng ... latLngs){
        StringBuilder result = new StringBuilder();
        try{

            String strCoord = String.valueOf(latLngs[0].longitude) + "," + String.valueOf(latLngs[0].latitude);
            StringBuilder sb = new StringBuilder();
            StringBuilder urlBuilder = new StringBuilder("https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?request=coordsToaddr&coords=" +strCoord+ "&sourcecrs=epsg:4326&output=json&orders=addr"); /* URL */

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn;


            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID","mpv15evkbg");
            conn.setRequestProperty("X-NCP-APIGW-API-KEY","qNqbokQCIyejnMYnM05EukdcfoJmmUK9Y3woZm3v");
            InputStream is = conn.getInputStream();
            StringBuilder builder = new StringBuilder(); //문자열을 담기 위한 객체
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8")); //문자열 셋 세팅
            String line;

            while ((line = reader.readLine()) != null) {

                builder.append(line+ "\n");
            }
            Log.d("STATE",builder.toString());
            JSONArray jarray = new JSONObject(builder.toString()).getJSONArray("results");
            Log.d("STATE",jarray.get(0).toString());
            for(int i = 0 ; i<jarray.length(); i++){
                JSONObject jObject = jarray.getJSONObject(i);
                /*String area1 = jObject.optString("area1");
                String area2 = jObject.optString("area2");
                String area3 = jObject.optString("area3");
                JSONObject jobj= new JSONObject(area1);
                JSONObject jobj2= new JSONObject(area2);
                JSONObject jobj3= new JSONObject(area3);
                result.append(jobj.getString("name"));
                result.append(jobj2.getString("name"));
                result.append(jobj3.getString("name"));*/
                result.append(jObject.getString("name"));
            }

            Log.d("STATE",result.toString());


           /* JsonParser jsonParser = new JsonParser();
            JSONObject jObj = new JSONObject(builder.toString());
            JSONArray jArray = (JSONArray) jObj.get("results");
            Log.d("STATE",jArray.get(1).toString());
            JSONObject jObj1 = new JSONObject(jArray.get(1).toString());
            line = jObj1.getString("name");
            Log.d("STATE",jArray.get(1).toString());*/
            return result.toString();
        }catch(MalformedURLException | ProtocolException exception) {
            exception.printStackTrace();
        }catch(IOException io){
            io.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }
    protected void onProgressUpdate(Integer ... value){

    }
    protected void onPostExecute(String result){
        Marker mark1  = new Marker();

    }
    protected void onCancelled(){

    }
}
