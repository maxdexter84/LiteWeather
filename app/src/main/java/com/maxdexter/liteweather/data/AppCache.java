package com.maxdexter.liteweather.data;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Вспомогательный класс для хранения выбранного города
 *
 * @author Aleksandr Anikin
 */
public final class AppCache {

    private static final String CITY_KEY = "city";
    private static final String DEFAULT_TOWN = "Moscow";
    private SharedPreferences userPreferences; //Специальный класс для длительного хранения данных

    //Конструктор класса
    public AppCache(Activity activity) {
        userPreferences = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    // Возвращаем город по умолчанию, если SharedPreferences пустые
    String getSavedCity() {
        return userPreferences.getString(CITY_KEY, DEFAULT_TOWN);
    }

    public void saveCity(String city) {
        userPreferences.edit().putString(CITY_KEY, city).apply();
    }
}
