package com.maxdexter.liteweather.data;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public final class WeatherLoader {

    private final static String OPEN_WEATHER_MAP_API = "http://api.openweathermap.org/data/2.5/weather?q=%s&&apikey=%s&%s";
    private final static String OPEN_WEATHER_MAP_API_SEVEN_DAYS = "https://api.openweathermap.org/data/2.5/onecall?lat=%s&lon=%s&appid=%s&%s";
    private final static String MY_API_KEY = "073f40e104f2129961514beb51a721d2";
    private final static String RESPONSE = "cod"; //Имя поля с кодом состояния в данных json
    private final static int RESPONSE_SUCCESS = 200; //Код сервера о том что есть данные для такойлокации в поле cod
    private static String units = "units=metric";
    private static JSONObject jsonObject;
    private ParseData parseData = new ParseData();
    //Метод делает запрос на сервер и возвращает ответ в виде JSON или null,на вход принимает название городавведенного пользователем
    public  void getData(final String city){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(String.format(OPEN_WEATHER_MAP_API,city,MY_API_KEY,units)); //создаем новый url
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();// открываем соединение
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())); //Открываем потоковое чтение данных
                    String tempVariable;
                    StringBuilder rawData = new StringBuilder();
                    while ((tempVariable = reader.readLine()) != null) {
                        rawData.append(tempVariable).append("/n");
                    }
                    reader.close();
                    jsonObject = new JSONObject(rawData.toString());
                    if(jsonObject.getInt(RESPONSE) == RESPONSE_SUCCESS){
                        Log.d("SERVER MESSAGE", "Данные с сервера получены");
                        getCoordinates(jsonObject);
                    }else {
                        Log.d("SERVER MESSAGE", "Данные с сервера не получены");
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    //Получение координат по названию города
    @SuppressLint("DefaultLocale")
    private  void getCoordinates(JSONObject json) {
        final String cityName;
        try{
            JSONObject coord = json.getJSONObject("coord");
             double lat = coord.getDouble("lat");
             double lon = coord.getDouble("lon");
            cityName = json.getString("name").toUpperCase(Locale.getDefault());
            updateDailyWeatherData(lat,lon,cityName);
        }catch (Exception e){
            Log.d("Log", "One or more fields not found in the JSON data");
        }
    }

     private void updateDailyWeatherData(final double lat, final double lon, final String cityName) throws JSONException {
        final JSONObject j = getDataSevenDays(lat,lon);
        // Вызов методов напрямую может вызвать runtime error
        if (j == null) {
          Log.d("Log","updateDailyWeatherData j = null");
        } else {
            parseData.addWeather(j,cityName);
           
        }
    }

    public  JSONObject getDataSevenDays(double lat, double lon){
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
