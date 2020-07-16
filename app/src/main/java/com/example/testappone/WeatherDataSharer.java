package com.example.testappone;

import android.os.Bundle;

public class WeatherDataSharer {

    public Bundle MakeWeatherBundle(Bundle bundle){
        bundle = AddCityInfo(bundle);
        bundle = AddCityCurrentInfo(bundle);
        bundle = AddCityHourlyInfo(bundle);
        bundle = AddCityDailyInfo(bundle);
        bundle = AddCityLifeIndexInfo(bundle);
        bundle = AddSettingInfo(bundle);
        return bundle;
    }

    public Bundle AddCityInfo(Bundle bundle){
        City city=DataManager.GetObjcet().GetCity();
        bundle.putString("cityID",city.GetCityID());
        bundle.putString("cityName",city.GetCityNameEnglish());
        bundle.putString("cityCountryName",city.GetCityCountryName());
        bundle.putString("cityLat","100.09");
        bundle.putString("cityLon","98.0923");
        bundle.putString("cityWeather","Rainy");
        bundle.putString("cityCurrentTime","10:10");
        return bundle;
    }
    public Bundle AddCityCurrentInfo(Bundle bundle){
        return bundle;
    }
    public Bundle AddCityHourlyInfo(Bundle bundle){
        return bundle;
    }
    public Bundle AddCityDailyInfo(Bundle bundle){
        return bundle;
    }
    public Bundle AddCityLifeIndexInfo(Bundle bundle){
        return bundle;
    }

    public Bundle AddSettingInfo(Bundle bundle){
        return bundle;
    }
}
