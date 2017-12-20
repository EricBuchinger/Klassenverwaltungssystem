package at.htl.organicer.rest;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by phili on 23.11.2017.
 */

public class RestHelper {
    private static final String URL = "https://mese.webuntis.com/WebUntis/jsonrpc.do";
    public static boolean authenticateUser(String username, String password, Context context) throws JSONException {
        JSONObject asfd = new JSONObject();
        JSONObject userdata = new JSONObject();
        userdata.put("user", username);
        userdata.put("password", password);
        userdata.put("client", "Organicer");


        asfd.put("id", "ID");
        asfd.put("method", "authenticate");
        asfd.put("params", userdata);
        asfd.put("jsonrpc", "2.0");





        JSONArray parameter = new JSONArray();
        parameter.put(asfd);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, URL + "?school=htbla linz leonding", parameter, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String jakob = "";
                for(int i = 0; i < response.length(); i++){
                    try {
                        JSONObject actual = (JSONObject) response.get(i);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        requestQueue.add(request);
        requestQueue.start();

        return true;
    }
    public static boolean logout(Context context) throws JSONException {
        //{"id":"ID","method":"logout","jsonrpc":"2.0"}
        JSONObject logoutObject = new JSONObject();
        logoutObject.put("id", "ID");
        logoutObject.put("method", "logout");
        logoutObject.put("params", new JSONObject());
        logoutObject.put("jsonrpc", "2.0");

        JSONArray array = new JSONArray(); array.put(logoutObject);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, URL + "?school=htbla linz leonding", array, new Response.Listener<JSONArray>(){

            @Override
            public void onResponse(JSONArray response) {
                //logout
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        requestQueue.add(request);
        requestQueue.start();

        return true;
    }

    public static boolean downloadWebuntisData(){
        //TODO
        return true;
    }


    public static boolean getTeachers(Context context) throws  JSONException {
        //{"id":"ID","method":"getTeachers","params":{},"jsonrpc":"2.0"}

        JSONObject asfd = new JSONObject();
        asfd.put("id", "ID");
        asfd.put("method", "getTeachers"); //TODO always set Methodname here
        asfd.put("params", "");
        asfd.put("jsonrpc", "2.0");

        JSONArray param = new JSONArray();
        param.put(asfd);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, URL, param, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i < response.length(); i++){
                    try {
                        JSONObject actual = (JSONObject) response.get(i); //FIXME ACTUAL TEACHER
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        requestQueue.add(request);
        requestQueue.start();

        return true;
    }
}
