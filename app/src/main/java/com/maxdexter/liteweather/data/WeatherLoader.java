package com.maxdexter.liteweather.data;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public final class WeatherLoader {

    private final static String OPEN_WEATHER_MAP_API = "http://api.openweathermap.org/data/2.5/weather?q=%s&&apikey=%s&%s";
    private final static String OPEN_WEATHER_MAP_API_SEVEN_DAYS = "https://api.openweathermap.org/data/2.5/onecall?lat=%s&lon=%s&appid=%s&%s";
    private final static String MY_API_KEY = "073f40e104f2129961514beb51a721d2";
    private final static String RESPONSE = "cod"; //Имя поля с кодом состояния в данных json
    private final static int RESPONSE_SUCCESS = 200; //Код сервера о том что есть данные для такойлокации в поле cod
    private static String units = "units=metric";


    //Метод делает запрос на сервер и возвращает ответ в виде JSON или null,на вход принимает название городавведенного пользователем
    public static JSONObject getData(String city){
        try {
            URL url = new URL(String.format(OPEN_WEATHER_MAP_API,city,MY_API_KEY,units)); //создаем новый url
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();// открываем соединение

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())); //Открываем потоковое чтение данных
            StringBuilder rawData = new StringBuilder();
            String tempVariable;
            while ((tempVariable = reader.readLine()) != null){
                rawData.append(tempVariable).append("/n");
            }
            reader.close();
            JSONObject jsonObject = new JSONObject(rawData.toString());
            if(jsonObject.getInt(RESPONSE) == RESPONSE_SUCCESS){
                Log.d("SERVER MESSAGE", "Данные с сервера получены");
                return jsonObject;
            }else {
                Log.d("SERVER MESSAGE", "Данные с сервера не получены");
                return null;
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;

        }

    }
    public static JSONObject getDataSevenDays(double lat, double lon){
        String la = ""+lat;
        String lo = ""+lon;
        try {

            URL url = new URL(String.format(OPEN_WEATHER_MAP_API_SEVEN_DAYS,la,lo,MY_API_KEY,units)); //создаем новый url
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();// открываем соединение

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())); //Открываем потоковое чтение данных
            StringBuilder rawData = new StringBuilder();
            String tempVariable;
            while ((tempVariable = reader.readLine()) != null){
                rawData.append(tempVariable).append("/n");
            }
            reader.close();
            JSONObject jsonObjectSevenDays = new JSONObject(rawData.toString());
            if(jsonObjectSevenDays != null){
                Log.d("SERVER MESSAGE", "Данные с сервера получены");
                return jsonObjectSevenDays;
            }else {
                Log.d("SERVER MESSAGE", "Данные с сервера е получены");
                return null;
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;

        }

    }


}
