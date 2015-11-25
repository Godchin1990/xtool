package com.godchin.codelife.system;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

//跟App相关的辅助类
public class AppUtils {
	/**
	 * 获取包名
	 * 
	 * @param ctx
	 * @return
	 */
	public static String getPackageName(Context ctx) {
		try {
			PackageInfo info = ctx.getPackageManager().getPackageInfo(
					ctx.getPackageName(), 0);
			return info.packageName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取应用程序名称
	 */
	public static String getAppName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			int labelRes = packageInfo.applicationInfo.labelRes;
			return context.getResources().getString(labelRes);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * [获取应用程序版本名称信息]
	 * 
	 * @param context
	 * @return 当前应用的版本名称
	 */
	public static String getVersionName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			return packageInfo.versionName;

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取设备信息
	 * 
	 * @param context
	 * @return
	 */
	public static Map<String, String> getDeviceInfo(Context context) {
		Map<String, String> map = new HashMap<String, String>();
		android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		String device_id = tm.getDeviceId();
		String msisdn = tm.getLine1Number(); // 手机号码
		String iccid = tm.getSimSerialNumber(); // sim卡号ICCID
		String imsi = tm.getSubscriberId(); // imsi

		android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);

		int i = wifi.getConnectionInfo().getIpAddress();
		String ip = ((i & 0xff) + "." + (i >> 8 & 0xff) + "."
				+ (i >> 16 & 0xff) + "." + (i >> 24 & 0xff));
		String mac = wifi.getConnectionInfo().getMacAddress();

		if (TextUtils.isEmpty(device_id)) {
			device_id = mac;
		}

		if (TextUtils.isEmpty(device_id)) {
			device_id = android.provider.Settings.Secure.getString(
					context.getContentResolver(),
					android.provider.Settings.Secure.ANDROID_ID);
		}

		map.put("ip", doNullStr(ip));
		map.put("mac", doNullStr(mac));
		map.put("device_id", doNullStr(device_id));
		map.put("msisdn", doNullStr(msisdn));
		map.put("iccid", doNullStr(iccid));
		map.put("imsi", doNullStr(imsi));

		return map;
	}

	/**
	 * 判断当前应用程序是否处于后台，通过getRunningTasks的方式
	 * 
	 * @return true 在后台; false 在前台
	 */
	public static boolean isApplicationBroughtToBackgroundByTask(
			String packageName, Context context) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasks = activityManager.getRunningTasks(1);
		if (!tasks.isEmpty()) {
			ComponentName topActivity = tasks.get(0).topActivity;
			if (!topActivity.getPackageName().equals(packageName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取Manifest中的meta-data值
	 * 
	 * @param context
	 * @param metaKey
	 * @return
	 */
	public static String getMetaValue(Context context, String metaKey) {
		Bundle metaData = null;
		String values = null;
		if (context == null || metaKey == null) {
			return null;
		}
		try {
			ApplicationInfo ai = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			if (null != ai) {
				metaData = ai.metaData;
			}
			if (null != metaData) {
				values = metaData.getString(metaKey);
			}
		} catch (NameNotFoundException e) {

		}
		return values;
	}

	/**
	 * 判断字符串是否有空
	 * 
	 * @param str
	 * @return
	 */
	public static String doNullStr(String str) {
		return TextUtils.isEmpty(str) ? "" : str;
	}

	public static String getLanguage(Context context) {
		Locale locale = context.getResources().getConfiguration().locale;
		String language = locale.getLanguage();
		if (TextUtils.isEmpty(language) || language.length() < 2) {
			language = "en";
		} else {
			language = language.substring(language.length() - 2);
		}
		return language.toLowerCase();
	}
	
	
	 /**
     * 获取机器唯一ID
     *
     * @return 唯一UUID
     */
    public static String getUid(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String tmDevice, androidId;
        tmDevice = tm.getDeviceId();
        androidId = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), (long) tmDevice.hashCode());
        return deviceUuid.toString();
    }
    
    void sim(Context context){
    	StringBuffer siminfo = new StringBuffer();
    	
    	TelephonyManager tm = (TelephonyManager) context  
                .getSystemService(Context.TELEPHONY_SERVICE);  
        // SIM卡提供商代码 46000 46002移动  46001中国联通  46003电信  
        String SimOperator = tm.getSimOperator();  
        // Log.e("SIM卡提供商代码 ", SimOperator);  
        siminfo.append("SIM卡提供商代码   " + SimOperator + "\n");  
  
        // SIM卡提供商名称  
        String SimOperatorName = tm.getSimOperatorName();  
        // Log.e("SIM卡提供商名称", SimOperatorName);  
        siminfo.append("SIM卡提供商名称  " + SimOperatorName + "\n");  
  
        // SIM卡国别  
        String SimCountryIso = tm.getSimCountryIso();  
        // Log.e("SIM卡国别", SimCountryIso);  
        siminfo.append("SIM卡国别 " + SimCountryIso + "\n");  
  
        // 返回设备唯一ID  
        String deviceid = tm.getDeviceId();  
        // Log.e("返回设备唯一ID", deviceid);GSM手机的IMEI和CDMA手机的MEID  
        siminfo.append("返回设备唯一ID " + deviceid + "\n");  
  
        // 获得电话号码  
        String tel = tm.getLine1Number();  
        // Log.e("获得电话号码", tel);  
        siminfo.append("获得电话号码 " + tel + "\n");  
  
        // SIM卡序列号  
        String imei = tm.getSimSerialNumber();  
        // Log.e("SIM卡序列号", imei);  
        siminfo.append("SIM卡序列号 " + imei + "\n");  
  
        // 获取客户ID,在GSM中是imsi号  
        String imsi = tm.getSubscriberId();  
        // Log.e("获取客户ID,在GSM中是imsi号 ", imsi);  
        siminfo.append("获取客户ID,在GSM中是imsi号 " + imsi + "\n");  
  
        // SIM卡状态  
        int simState = tm.getSimState();  
  
        // SIM卡状态  
        switch(simState) {  
            case TelephonyManager.SIM_STATE_READY:  
                // 良好  
                // Log.e("", "良好");  
                siminfo.append("SIM卡状态  良好    \n");  
                break;  
            case TelephonyManager.SIM_STATE_ABSENT:  
                // 无SIM卡  
                // Log.e("", "无SIM卡");  
                siminfo.append("SIM卡状态  无SIM卡    \n");  
                break;  
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:  
                // SIM卡网络被锁定，需要Network PIN解锁  
                // Log.e("", "SIM卡网络被锁定，需要Network PIN解锁");  
                siminfo.append("SIM卡状态  SIM卡网络被锁定，需要Network PIN解锁好    \n");  
                break;  
            case TelephonyManager.SIM_STATE_PIN_REQUIRED:  
                // SIM卡PIN被锁定，需要User PIN解锁  
                // Log.e("", "SIM卡PIN被锁定，需要User PIN解锁");  
                siminfo.append("SIM卡状态  SIM卡PIN被锁定，需要User PIN解锁    \n");  
                break;  
            case TelephonyManager.SIM_STATE_PUK_REQUIRED:  
                // SIM卡PUK被锁定，需要User PUK解锁  
                // Log.e("", "SIM卡PUK被锁定，需要User PUK解锁");  
                siminfo.append("SIM卡状态  SIM卡PUK被锁定，需要User PUK解锁    \n");  
                break;  
            case TelephonyManager.SIM_STATE_UNKNOWN:  
                // SIM卡未知  
                // Log.e("", "SIM卡未知");  
                siminfo.append("SIM卡状态  SIM卡未知    \n");  
                break;  
        }  
    }

}
