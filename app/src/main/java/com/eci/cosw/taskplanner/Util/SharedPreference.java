package com.eci.cosw.taskplanner.Util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {

    private final SharedPreferences sharedPreferences;
    private Context context;

    public SharedPreference(Context context, String file) {
        this.context = context;
        this.sharedPreferences =
                context.getSharedPreferences(file, Context.MODE_PRIVATE);
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void save(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public Boolean contains(String key) {
        return sharedPreferences.contains(key);
    }

    public String getValue(String key) {
        return sharedPreferences.getString(key,null);
    }

}
