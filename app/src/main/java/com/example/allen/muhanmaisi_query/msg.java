package com.example.allen.muhanmaisi_query;
import org.json.JSONObject;
public class msg {
    public class login {
        public login (){
        }
        String get_name_(){
            return name_;
        }
        void set_name_(String name){
            name_ = name;
        }
        String get_pwd_(){
            return pwd_;
        }
        void set_pwd_(String pwd){
            pwd_ = pwd;
        }
        String name_;
        String pwd_;
        String tojson() {
            String res = "";
            try{
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name",name_);
                jsonObject.put("pwd",pwd_);
                res = jsonObject.toString();
            }
            catch (Exception e){
            }
            return res;
        }
        void fromjson(JSONObject json) {
            try {
                name_= json.getString("name");
                pwd_= json.getString("pwd");
            }
            catch(Exception e) {
            }
        }
    }
    public class actor_info {
        public actor_info (){
            readnum_ = 0;
        }
        String get_id_(){
            return id_;
        }
        void set_id_(String id){
            id_ = id;
        }
        int get_readnum_(){
            return readnum_;
        }
        void set_readnum_(int readnum){
            readnum_ = readnum;
        }
        String id_;
        int readnum_;
        String tojson() {
            String res = "";
            try{
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id",id_);
                jsonObject.put("readnum",readnum_);
                res = jsonObject.toString();
            }
            catch (Exception e){
            }
            return res;
        }
        void fromjson(JSONObject json) {
            try {
                id_= json.getString("id");
                readnum_= json.getInt("readnum");
            }
            catch(Exception e) {
            }
        }
    }
}