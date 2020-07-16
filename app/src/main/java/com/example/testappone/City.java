package com.example.testappone;

public class City {

    private String mCityNameLocal;
    private String mCityNameEnglish;
    private String mCityID;
    private String mCityCountryName;
    private int mCityLocalTime;
    private double mCityGMT;
    private double mCityGMTOffset;
    private double mCityLat;
    private double mCityLon;

    public void SetCityNameLocal(String cityNameLocal){ mCityNameLocal = cityNameLocal; }
    public String GetCityNameLocal(){ return mCityNameLocal; }

    public void SetCityNameEnglish(String cityNameEnglish){ mCityNameEnglish = cityNameEnglish; }
    public String GetCityNameEnglish(){ return mCityNameEnglish; }

    public void SetCityID(String cityID){ mCityID = cityID; }
    public String GetCityID(){ return mCityID; }

    public void SetCityCountryName(String cityCountryName){ mCityCountryName = mCityCountryName; }
    public String GetCityCountryName(){ return mCityCountryName; }

    public void SetCityLocalTime(int cityLocalTime){ mCityLocalTime = cityLocalTime; }
    public int GetCityLocalTime(){ return mCityLocalTime; }

    public void SetCityGMT(double cityGMT){ mCityGMT = cityGMT; }
    public double GetCityGMT(){ return mCityGMT; }

    public void SetCityGMTOffset(double cityGMTOffset){ mCityGMTOffset = cityGMTOffset; }
    public double GetCityGMTOffset(){ return mCityGMTOffset; }

    public void SetCityLat(double cityLat){ mCityLat = cityLat; }
    public double GetCityLat(){ return mCityLat; }

    public void SetCityLon(double cityLon){ mCityLon = cityLon; }
    public double GetCityLon(){ return mCityLon; }


    public void InsertDummyCityInfo(){
        SetCityNameLocal("Dhaka");
        SetCityNameEnglish("DHAKA");
        SetCityID("location_id_dhaka");
        SetCityCountryName("USA");
        SetCityLocalTime(1234124);
        SetCityGMT(6.00);
        SetCityGMTOffset(0.0);
        SetCityLat(123.434234);
        SetCityLon(23.345233);
    }
}
