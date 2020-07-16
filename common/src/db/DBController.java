package com.example.testappone;

public class DBController{
    public static DBController dbController = new DBController();
    public static DBController getInstance(){
        return dbController;
    }


    private void createDBIfNotExist(String dbName){

    }

}