package com.godchin.codelife;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.godchin.codelife.receiver.WiFiReceivers;
import com.godchin.codelife.receiver.WiFiReceivers.IWiFiStatusInterface;
import com.godchin.codelife.ui.wifi.SeeExistwifiActivity;
import com.godchin.codelife.view.RoundProgressBar;
import com.godchin.codelife.view.TitleBar;
import com.godchin.codelife.view.TitleBar.TitleBarClickListener;

public class MainActivity extends Activity implements TitleBarClickListener,
		IWiFiStatusInterface {
	TitleBar titleBar;
	RoundProgressBar round;

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
		round = (RoundProgressBar) findViewById(R.id.round);
		// 可以用代码设置
		// titleBar.setTitle_text("aaa");
	}

	private void setListener() {
		titleBar.setTopBarClickListener(this);
		findViewById(R.id.testwifiexist).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this,SeeExistwifiActivity.class);
			    startActivity(intent);
			}
		});
	}

	@Override
	public void leftClick() {
		Toast.makeText(this, "点击左边", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void rightClick() {
		Toast.makeText(this, "点击右边", Toast.LENGTH_SHORT).show();

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
}
