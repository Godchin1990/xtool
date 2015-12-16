package com.godchin.codelife.system;


import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcelable;

import com.godchin.codelife.MainActivity;
import com.godchin.codelife.R;

public class CreateShortCutUtils {
    //创建及删除一键清理桌面快捷方式的intent
    //参数分别为，调用者activity，快捷方式的图标id，快捷方式启动的intent，快捷方式的名称，是否为长按桌面进来的（或者是直接点击进来的）
    public static void createShortCut(Activity activity, int imageId, Intent intent, String title, boolean isLongClick) {
        if (isLongClick) {
            Intent addShortCut;
            //判断是否需要添加快捷方式
            if (activity.getIntent() != null) {
                if (activity.getIntent().getAction() != null) {
                    if (activity.getIntent().getAction().equals(Intent.ACTION_CREATE_SHORTCUT)) {
                        addShortCut = new Intent();
                        //快捷方式的名称
                        addShortCut.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
                        //显示的图片
                        Parcelable icon = Intent.ShortcutIconResource.fromContext(activity, imageId);
                        addShortCut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
                        //快捷方式激活的activity，需要执行的intent，自己定义
                        addShortCut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
                        //OK，生成
                        activity.setResult(activity.RESULT_OK, addShortCut);
                    } else {
                        //取消
                        activity.setResult(activity.RESULT_CANCELED);
                    }
                } else {
                    createShortCut(activity, imageId, intent, title);
                }
            }
        } else {
            createShortCut(activity, imageId, intent, title);
        }
    }

    public static void createShortCut(Activity activity, int imageId, Intent intent, String title) {
        //创建快捷方式的Intent
        Intent shortcutintent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        //不允许重复创建
        shortcutintent.putExtra("duplicate", false);
        //需要现实的名称
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
        //快捷图片
        Parcelable icon = Intent.ShortcutIconResource.fromContext(activity.getApplicationContext(), imageId);
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        //点击快捷图片，运行的程序主入口
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
        //发送广播。OK
        activity.sendBroadcast(shortcutintent);
    }

    //创建程序主快捷方式
    public static void createMainShortCut(Activity activity, boolean isLongClick) {
        createShortCut(activity, R.drawable.ic_launcher, new Intent(activity.getApplicationContext(), MainActivity.class), activity.getString(R.string.app_name), isLongClick);
    }

    //创建桌面系统清理快捷方式
    public static void createSystemClearShortCut(Activity activity, boolean isLongClick) {
        Intent intent = new Intent();
        ComponentName cn = new ComponentName("com.yiba.www.activity",
                "com.yiba.www.activity.BackgroundSystemClearActivity");
        intent.setComponent(cn);
        intent.setAction("com.yiba.www.activity.BackgroundSystemClear");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        createShortCut(activity, R.drawable.ic_launcher, intent, "launchSystemClear", isLongClick);
    }

    //删除桌面系统清理快捷方式
    public static void deleteSystemClearShortCut(Activity activity) {
        Intent intent = new Intent();
        ComponentName cn = new ComponentName("com.yiba.www.activity",
                "com.yiba.www.activity.BackgroundSystemClearActivity");
        intent.setComponent(cn);
        intent.setAction("com.yiba.www.activity.BackgroundSystemClear");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
        //快捷方式的名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, "launchSystemClear");

        //这里的intent要和创建时的intent设置一致
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
        activity.sendBroadcast(shortcut);
    }

    /**
     * 删除快捷方式
     */
    public static void deleteShortCut(Activity activity, String shortcutName,Intent intent) {
        Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
        //快捷方式的名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);
        //这里的intent要和创建时的intent设置一致
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
        activity.sendBroadcast(shortcut);
    }

    //检测是否存在快捷方式
    private boolean hasShortcut(Activity activity,String shortCutName) {
        boolean isInstallShortcut = false;
        final ContentResolver cr = activity.getContentResolver();
        //2.2版本的是launcher2，不然无效，网上有的是launcher,我试验了2.2不能用
        final Uri CONTENT_URI = Uri.parse("content://com.android.launcher2.settings/favorites?notify=true");//保持默认
        Cursor c = cr.query(CONTENT_URI, new String[]{"title", "iconResource"}, "title=?", //保持默认
                //getString(R.string.app_name)是获取string配置文件中的程序名字，这里用一个String的字符串也可以
                new String[]{shortCutName.trim()}, null);
        if (c != null && c.getCount() > 0) {
            isInstallShortcut = true;
        }
        return isInstallShortcut;
    }
}