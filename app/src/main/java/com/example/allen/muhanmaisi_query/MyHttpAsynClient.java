package com.example.allen.muhanmaisi_query;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by allen on 15/10/29.
 */

public class MyHttpAsynClient {
    public static String url;
    public static AsyncHttpClient client = new AsyncHttpClient();

    public void login(String name, String pwd){
        try {
            String loginurl = url + "/login";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", name);
            jsonObject.put("pwd", pwd);
            StringEntity tmpstr = new StringEntity(jsonObject.toString());
            client.post(null, loginurl, tmpstr, "application/json", new JsonHttpResponseHandler());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
