package com.example.allen.muhanmaisi_query;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by allen on 15/10/29.
 */

public class MyHttpAsynClient {
    public static AsyncHttpClient client = new AsyncHttpClient();
    public static String url;

    public void login(){
        try {
            AppMessage.Login login = AppMessage.Login.newBuilder()
                    .setName("allen")
                    .setPassword(".m").build();


            String loginurl = url + "login";

            String[] sendData = new String[]{login.toString()};

            client.post(loginurl, new BinaryHttpResponseHandler(sendData) {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    int a = 1;
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    int a = 2;
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
