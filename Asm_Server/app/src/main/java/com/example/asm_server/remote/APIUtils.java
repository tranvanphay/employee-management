package com.example.asm_server.remote;

public class APIUtils {

    private APIUtils(){
    };
    public  static  final  String API_URL="http://192.168.43.21:8080";
    public static  UserService getUserService(){
        return  RetrofitClient.getClient(API_URL).create(UserService.class);

    }

}
