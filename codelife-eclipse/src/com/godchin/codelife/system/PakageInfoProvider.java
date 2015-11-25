package com.godchin.codelife.system;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

public class PakageInfoProvider {
    private static final String tag = "GetappinfoActivity";
    private Context context;
    private List<PackageInfo> appInfos;
    public PakageInfoProvider(Context context) {
        super();
        this.context = context;
    }



    public boolean filterApp(PackageInfo info) {
        if ((info.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
        // 代表的是系统的应用,但是被用户升级了. 用户应用
            return true;
        } else if ((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
        // 代表的用户的应用
            return true;
        }
        return false;
    }


    /**
     * 查询手机内非系统应用
     * @param context
     * @return
     */
    public static List<PackageInfo> getAllApps(Context context) {
        List<PackageInfo> apps = new ArrayList<PackageInfo>();
        PackageManager pManager = context.getPackageManager();
        //获取手机内所有应用
        List<PackageInfo> paklist = pManager.getInstalledPackages(0);
        for (int i = 0; i < paklist.size(); i++) {
            PackageInfo pak = (PackageInfo) paklist.get(i);
            //判断是否为非系统预装的应用程序
            if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) <= 0) {
                // customs applications
                apps.add(pak);
            }
        }
        return apps;
    }
}