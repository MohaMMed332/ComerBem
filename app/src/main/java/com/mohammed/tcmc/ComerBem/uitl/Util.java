package com.mohammed.tcmc.ComerBem.uitl;

import android.content.Context;
import android.net.ConnectivityManager;

public class Util {

    public static boolean isConected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isAvailable();

    }
}
