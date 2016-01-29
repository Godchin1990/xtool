package com.godchin.codelife;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.godchin.codelife.camera.TestcameraActivity;
import com.godchin.codelife.receiver.WiFiReceivers;
import com.godchin.codelife.receiver.WiFiReceivers.IWiFiStatusInterface;
import com.godchin.codelife.system.TestDevice;
import com.godchin.codelife.testui.AnimRoundProcessDialog;
import com.godchin.codelife.view.TitleBar;
import com.godchin.codelife.view.TitleBar.TitleBarClickListener;

public class MainActivity extends Activity implements TitleBarClickListener,
		IWiFiStatusInterface {
	TitleBar titleBar;
	private PopupWindow popupWindow;
	private String title[] = { "1", "2", "3", "4", "5" };
	private LinearLayout layout;
	private ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		setListener();
		WiFiReceivers.addWiFiStatusInterface(this);
	}

	private void initView() {
		titleBar = (TitleBar) findViewById(R.id.title_bar);
		titleBar.setTitle_text("codelife");
		titleBar.setRight_text("save");
	}

	private void setListener() {
		titleBar.setTopBarClickListener(this);
		findViewById(R.id.testwifiexist).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(MainActivity.this,
								TextWifiListActivity.class);
						startActivity(intent);
					}
				});

		findViewById(R.id.animroundprocessdialog).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(MainActivity.this,
								AnimRoundProcessDialog.class);
						startActivity(intent);
					}
				});
		
		findViewById(R.id.testdevice).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(MainActivity.this,
								TestDevice.class);
						startActivity(intent);
					}
				});
		findViewById(R.id.testswback).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(MainActivity.this,
								TestcameraActivity.class);
						startActivity(intent);
					}
				});
	}

	@Override
	public void leftClick() {
		Toast.makeText(this, "left", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void rightClick() {
		Toast.makeText(this, "right", Toast.LENGTH_SHORT).show();
		
		showPopupWindow(titleBar.right_tv);

	}

	@Override
	public void onWiFiStateStatusChange() {
		Toast.makeText(this, "onWiFiStateStatusChange", Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public void onWiFiRSSIStatusChange() {
		Toast.makeText(this, "onWiFiRSSIStatusChange", Toast.LENGTH_SHORT)
				.show();

	}

	@Override
	public void onWiFiConnectStatusChange() {
		Toast.makeText(this, "onWiFiConnectStatusChange", Toast.LENGTH_SHORT)
				.show();
	}
	
	public void showPopupWindow(View parent) {
		//加载布局
		layout = (LinearLayout) LayoutInflater.from(MainActivity.this).inflate(
				R.layout.dialog, null);
		//找到布局的控件
		listView = (ListView) layout.findViewById(R.id.lv_dialog);
		//设置适配器
		listView.setAdapter(new ArrayAdapter<String>(MainActivity.this,
				R.layout.text, R.id.tv_text, title));
		// 实例化popupWindow
		popupWindow = new PopupWindow(layout, 300,500);
		//控制键盘是否可以获得焦点
		popupWindow.setFocusable(true);
		//设置popupWindow弹出窗体的背景
		popupWindow.setBackgroundDrawable(new BitmapDrawable(null,""));
		WindowManager manager=(WindowManager) getSystemService(Context.WINDOW_SERVICE);
		@SuppressWarnings("deprecation")
		//获取xoff
		int xpos=manager.getDefaultDisplay().getWidth()/2-popupWindow.getWidth()/2;
		//xoff,yoff基于anchor的左下角进行偏移。
		popupWindow.showAsDropDown(parent,xpos, 0);
		//监听
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//关闭popupWindow
				popupWindow.dismiss();
				popupWindow = null;
			}
		});
	}
}
