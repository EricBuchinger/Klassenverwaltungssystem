package at.htl.organicer.rest;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import at.htl.organicer.activities.StartupActivity;

/**
 * Created by phili on 29.11.2017.
 */

public class RestHelperAlternative {
    private static final String URL = "https://mese.webuntis.com/WebUntis/jsonrpc.do";
    private static final String URL_SCHOOLNAME = URL + "?school=htbla linz leonding";
    private static final String APIKEY = "DACE2CO6HRCEKDOM";

    public static String authUser(String username, String password) {

        //FIXME: Hotfix, suboptimal
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        JSONObject userdata = new JSONObject();

        try {
            userdata.put("user", username);
            userdata.put("password", password);
            userdata.put("client", "Organicer");


            JSONObject standardParams = new JSONObject();
            standardParams.put("id", "ID");
            standardParams.put("method", "authenticate");
            standardParams.put("params", userdata);
            standardParams.put("jsonrpc", "2.0");


            HttpURLConnection httpURLConnection;

            httpURLConnection = (HttpURLConnection) new URL(URL_SCHOOLNAME).openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.connect();


            //Write
            OutputStream os = httpURLConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(standardParams.toString());
            writer.close();
            os.close();


            //Read
            BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));


            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();
            String result = sb.toString();


            JSONObject object = new JSONObject(result);

            object = object.getJSONObject("result");

            return object.getString("sessionId");
        }catch (JSONException | IOException ex){
            return null;
        }
    }

    private static JSONArray getData(SessionDataHelper dataHelper, String methodName) throws JSONException, IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(URL_SCHOOLNAME).openConnection(); //TODO: Request must not be resetted.
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.setRequestProperty("Cookie", "JSESSIONID=" + dataHelper.getSessionId());
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.connect();


        JSONObject standardParams = new JSONObject();
        standardParams.put("id", "ID");
        standardParams.put("method", methodName);
        standardParams.put("params", null);
        standardParams.put("jsonrpc", "2.0");

        //Write
        OutputStream os = httpURLConnection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(standardParams.toString());
        writer.close();
        os.close();

        //Read
        BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8"));

        String line = null;
        StringBuilder sb = new StringBuilder();

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        br.close();
        String result = sb.toString();

        JSONObject asJsonObject = new JSONObject(result);
        JSONArray jsonArray = asJsonObject.getJSONArray("result");

        return jsonArray;
    }

    public static SessionDataHelper getDataFromWebuntis(SessionDataHelper dataHelper) throws IOException, JSONException {
        dataHelper.parseData(getData(dataHelper, "getTimegridUnits"), "timegrid");

        //TODO: do this in background
        dataHelper.parseData(getData(dataHelper, "getTeachers"), "teacher");
        dataHelper.parseData(getData(dataHelper, "getSubjects"), "subject");
        dataHelper.parseData(getData(dataHelper, "getStudents"), "student");

        /*
        JSONArray classes = getData(dataHelper, "getKlassen");
        JSONArray rooms = getData(dataHelper, "getRooms");
        JSONArray departments = getData(dataHelper, "getDepartments");
        JSONArray holidays = getData(dataHelper, "getHolidays");
        JSONArray timegrid = getData(dataHelper, "getTimegridUnits");
        //JSONArray currentSchoolYear = getData(dataHelper, "getCurrentSchoolyear"); TODO: returns a JsonObject, not a JsonArray
        JSONArray schoolyears = getData(dataHelper, "getSchoolyears");
        */



        String dummy = "";
        //TODO: Timetable (getTimetable) params: id, type

        return dataHelper;
    }
}
