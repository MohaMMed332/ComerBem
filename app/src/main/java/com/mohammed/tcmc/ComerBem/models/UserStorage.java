package com.mohammed.tcmc.ComerBem.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashMap;
import java.util.Map;

public class UserStorage {

    public static void saveUserInfo(Context context, String name, String email, String imgurl, String phone) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("USER_NAME", name);
        editor.putString("USER_EMAIL", email);
        editor.putString("IMG_URL", imgurl);
        editor.putString("USER_PHONE", phone);


        editor.apply();
    }

    public static Map<String, String> getUserInfo(Context context) {
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
        Map<String, String> userInfos = new HashMap<String, String>();
        userInfos.put("USER_NAME", sh.getString("USER_NAME", null));
        userInfos.put("USER_EMAIL", sh.getString("USER_EMAIL", null));
        userInfos.put("IMG_URL", sh.getString("IMG_URL", null));
        // userInfos.put("USER_PHONE", sh.getString("USER_PHONE", null));
        userInfos.put("USER_PHONE", "1");





        return userInfos;
    }

}
