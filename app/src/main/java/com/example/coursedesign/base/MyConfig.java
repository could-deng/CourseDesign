package com.example.coursedesign.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.coursedesign.CourseDesignAPP;
import com.example.coursedesign.common.Constants;
import com.example.coursedesign.controller.DesitinationInfoDB;
import com.example.coursedesign.controller.PicNewsInfoDB;

/**
 * Created by yuanqiang on 2016/3/29.
 */
public class MyConfig {
    private static MyConfig instance;

    private MemExchange memExchange;
    public static MyConfig getInstance() {
        if (instance == null) {
            instance = new MyConfig();
//            instance.checkBaseController();
        }
        return instance;
    }
    private DesitinationInfoDB getDesitinationDataBase(){
        return DesitinationInfoDB.getInstance();
    }

    public MemExchange getMemExchange() {
        return MemExchange.getInstance();
    }

    public void setMemExchange(MemExchange memExchange) {
        this.memExchange = memExchange;
    }

    public String getTag() {
        return Constants.TAG;
    }


    /**
     * 获取网络状态
     * @param context
     * @return
     */
    private int getNetworkType(Context context) {
        int network = Constants.NETWORK_TYPE_NONE;
        if (context == null)
            return network;
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] infos = connectivity.getAllNetworkInfo();
            if (infos != null) {
                for (NetworkInfo ni : infos) {
                    if (ni.getTypeName().equalsIgnoreCase("WIFI")
                            && ni.isConnected()) {
                        network = Constants.NETWORK_TYPE_WIFI;
                        break;
                    } else if (ni.getTypeName().equalsIgnoreCase("MOBILE")
                            && ni.isConnected()) {
                        network = Constants.NETWORK_TYPE_MOBILE;
                        break;
                    }
                }
            }
        }
        return network;
    }

    public int getNetworkType() {
        return getNetworkType(CourseDesignAPP.getContext());
    }

    public DesitinationInfoDB getDatabase() {
        return DesitinationInfoDB.getInstance();
    }
}
