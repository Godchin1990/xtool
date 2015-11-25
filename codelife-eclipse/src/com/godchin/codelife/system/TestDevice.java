package com.godchin.codelife.system;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.godchin.codelife.R;

public class TestDevice extends Activity {  
    DevicePolicyManager devicePolicyManager;  
    ComponentName componentName;  
  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.testdevice);  
        devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);  
        componentName = new ComponentName(TestDevice.this, Device.class);  
    }  
  
    // 激活程序  
    public void btnjihuo(View v) {  
        if(!devicePolicyManager.isAdminActive(componentName)) {  
            Intent intent = new Intent(  
                    DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);  
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,  
                    componentName);  
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "提示文字");  
            startActivityForResult(intent, 1);  
        }  
    }  
  
    // 移除程序 如果不移除程序 APP无法被卸载  
    public void btnxiezai(View v) {  
        devicePolicyManager.removeActiveAdmin(componentName);  
    }  
  
    // 设置解锁方式 不需要激活就可以运行  
    public void btnszmm(View v) {  
        Intent intent = new Intent(DevicePolicyManager.ACTION_SET_NEW_PASSWORD);  
        startActivity(intent);  
    }  
  
    // 设置解锁方式  
    public void btnmm(View v) {  
        // PASSWORD_QUALITY_ALPHABETIC  
        // 用户输入的密码必须要有字母（或者其他字符）。  
        // PASSWORD_QUALITY_ALPHANUMERIC  
        // 用户输入的密码必须要有字母和数字。  
        // PASSWORD_QUALITY_NUMERIC  
        // 用户输入的密码必须要有数字  
        // PASSWORD_QUALITY_SOMETHING  
        // 由设计人员决定的。  
        // PASSWORD_QUALITY_UNSPECIFIED  
        // 对密码没有要求。  
        Intent intent = new Intent(DevicePolicyManager.ACTION_SET_NEW_PASSWORD);  
        devicePolicyManager.setPasswordQuality(componentName,  
                DevicePolicyManager.PASSWORD_QUALITY_NUMERIC);  
        startActivity(intent);  
    }  
  
    // 立刻锁屏  
    public void btnlock(View v) {  
        devicePolicyManager.lockNow();  
    }  
  
    // 设置5秒后锁屏  
    public void btnlocktime(View v) {  
        devicePolicyManager.setMaximumTimeToLock(componentName, 5000);  
    }  
  
    // 恢复出厂设置  
    public void btnwipe(View v) {  
        devicePolicyManager.wipeData(0);  
    }  
  
    // 设置密码锁  
    public void btnl(View v) {  
        devicePolicyManager.resetPassword("asdf",  
                DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY);  
        devicePolicyManager.lockNow();  
  
    }  
}  