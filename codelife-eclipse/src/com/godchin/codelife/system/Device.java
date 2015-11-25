package com.godchin.codelife.system;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;

/** 
 * @version 2012-9-27 上午09:25:21 
 **/  
public class Device extends DeviceAdminReceiver {  
    @Override  
    public void onEnabled(Context context, Intent intent) {  
        // 设备管理：可用  
    }  
  
    @Override  
    public void onDisabled(Context context, Intent intent) {  
  
        // 设备管理：不可用  
    }  
  
    @Override  
    public CharSequence onDisableRequested(Context context, Intent intent) {  
        return "这是一个可选的消息，警告有关禁止用户的请求";  
    }  
  
    @Override  
    public void onPasswordChanged(Context context, Intent intent) {  
        // 设备管理：密码己经改变  
    }  
  
    @Override  
    public void onPasswordFailed(Context context, Intent intent) {  
        // 设备管理：改变密码失败  
    }  
  
    @Override  
    public void onPasswordSucceeded(Context context, Intent intent) {  
        // 设备管理：改变密码成功  
    }  
}  