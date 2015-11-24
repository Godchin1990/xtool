package com.godchin.codelife.wifi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *description: PingNet
 * 
 *
 *@author xiaogong
 *created at 2015/11/23
 * 
 */
public class PingNet {

    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            return getNetWorkInfo(context);
        }
        return false;
    }


    public static boolean getNetWorkInfo(Context cont) {
        try {
            ConnectivityManager manager = (ConnectivityManager) cont
                    .getApplicationContext().getSystemService(
                            Context.CONNECTIVITY_SERVICE);
            if (manager == null) {
                return false;
            }
            NetworkInfo networkinfo = manager.getActiveNetworkInfo();
            if (networkinfo == null || !networkinfo.isAvailable()
                    || !networkinfo.isConnectedOrConnecting()) {
                return false;
            } else {// /如果有网络连接，再判断一下是否可以正常上网，
                if (ping()) {// //正常
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    private static final boolean ping() {

        String result = null;
        try {
            String ip = "www.baidu.com";// 除非百度挂了，否则用这个应该没问题~
            Process p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + ip);//ping3次
            // 读取ping的内容，可不加。
            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer stringBuffer = new StringBuffer();
            String content = "";
            while ((content = in.readLine()) != null) {
                stringBuffer.append(content);
            }
            Log.i("TTT", "result content : " + stringBuffer.toString());
            // PING的状态
            int status = p.waitFor();
            if (status == 0) {
                result = "successful~";
                return true;
            } else {
                result = "failed~ cannot reach the IP address";
            }

        } catch (IOException e) {
            result = "failed~ IOException";
        } catch (InterruptedException e) {
            result = "failed~ InterruptedException";
        } finally {
            Log.i("TTT", "result = " + result);

        }
        return false;

    }
}
