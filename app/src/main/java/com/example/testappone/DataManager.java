package com.example.testappone;

public class DataManager {
    public static DataManager mObject = new DataManager();
    private City mCity = new City();

    int variable;

    public static DataManager GetObjcet(){
        return mObject;
    }

    /*public void setCity(City city){
        mCity = city;
    }*/
    public City GetCity(){
        return mCity;
    }
    public void insetDummyData(){
        mCity.InsertDummyCityInfo();
    }
}
