package com.example.testappone;

import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {


    public void ParseJson(@NotNull JSONObject jsonObject) {
        try {
            Log.d("uttam: newcall ","ParseJson success: ");
        String cityName = jsonObject.getString("city_en");
        String countryName = jsonObject.getString("country_en");
        String cityLat = jsonObject.getString("lat");
        String cityLon = jsonObject.getString("lon");
        String cityId = jsonObject.getString("code");

        JSONObject dayInfo = jsonObject.getJSONObject("day0");
        String month = dayInfo.getString("mon");
        City city = DataManager.GetObjcet().GetCity();
        city.SetCityID(cityId);
        city.SetCityNameEnglish(cityName);
        //city.SetCityLat(cityL);
        //city.SetCityLon(cityLon);

        Log.d("uttam: paser, City Info : ",cityName+"\n"+countryName+"\n"+cityLat+"\n" +cityLon+"\n"+cityId+month);
        }catch(JSONException e){
            e.printStackTrace();
        }

    }
}
