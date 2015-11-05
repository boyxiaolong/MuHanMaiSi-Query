package com.example.allen.muhanmaisi_query;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
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
            final msg.login login = new msg().new login();
            login.set_name_("allen");
            login.set_pwd_("123456");
            StringEntity tmpstr = new StringEntity(login.tojson());
            client.post(null, loginurl, tmpstr, "application/json", new JsonHttpResponseHandler(){
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    msg.actor_info actor_info_ = new msg().new actor_info();
                    actor_info_.fromjson(response);
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
