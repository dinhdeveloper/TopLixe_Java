package com.java.music.api;

public class APIUntil {
    private static String baseURL = "http://115.73.214.162:7777/"; // https://mobishops.herokuapp.com/ http://vtnshop.herokuapp.com/

    public static APIService getServer() {
        return APIClient.getApiClientLSP(baseURL).create(APIService.class);
    }
}
