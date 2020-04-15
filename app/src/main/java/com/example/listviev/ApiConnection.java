package com.example.listviev;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ApiConnection {

    void connect() throws IOException {

        URL url = new URL("http://api.gios.gov.pl/pjp-api/rest/station/findAll");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Accept", "application/json");
        if (connection.getResponseCode() == 200) {
            InputStreamReader is = new InputStreamReader(connection.getInputStream());
            Gson gson = new Gson();
            stations = new ArrayList<>();
            Type ListType = new TypeToken<ArrayList<MainPojo>>(){}.getType();
            stations= gson.fromJson(is,ListType);
            connection.disconnect();
        }}

        SensorsPojo getSensor(int id) throws IOException {
            try {
                URL urll = new URL("http://api.gios.gov.pl/pjp-api/rest/station/sensors/" + id);
                HttpURLConnection conn = (HttpURLConnection) urll.openConnection();
                conn.setRequestProperty("Accept", "application/json");
                if (conn.getResponseCode() == 200) {
                  InputStreamReader iss = new InputStreamReader(conn.getInputStream());

                 Gson gsonn = new Gson();

                    sensor = new ArrayList<>();
                    Type ListType = new TypeToken<ArrayList<SensorsPojo>>(){}.getType();
                    sensor = gsonn.fromJson(iss, ListType);
                    conn.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sensor.get(0);
    }
    DataPojo getData(int id) throws IOException {

        try {
            URL urll = new URL("http://api.gios.gov.pl/pjp-api/rest/data/getData/" + id);
            HttpURLConnection conn = (HttpURLConnection) urll.openConnection();
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() == 200) {
                InputStreamReader iss = new InputStreamReader(conn.getInputStream());
                Gson gsonn = new Gson();
                dataPojoList = gsonn.fromJson(iss, DataPojo.class);
                conn.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataPojoList;
        }
    public List<SensorsPojo> sensor;
    public List<MainPojo> stations;
    public DataPojo dataPojoList;
}
