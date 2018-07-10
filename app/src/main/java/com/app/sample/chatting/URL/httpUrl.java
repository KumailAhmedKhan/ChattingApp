package com.app.sample.chatting.URL;

public class httpUrl {
    public static  final String ApiUrl="http://182.191.116.219:81/api/SLCApi";
    public static  final String HubUrl="http://182.191.116.219:81/chat";

    //public static final String ApiUrl="http://192.168.1.205:81/api/SLCApi";
    //public static final String HubUrl="http://192.168.1.205:81/chat";

    public httpUrl(){}

    public  String getURl() {
        return ApiUrl;
    }
    public static String getHubURl() {
        return HubUrl;
    }
}
