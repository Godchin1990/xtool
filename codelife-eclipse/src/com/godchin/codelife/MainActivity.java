package com.godchin.codelife;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.godchin.codelife.receiver.WiFiReceivers;
import com.godchin.codelife.receiver.WiFiReceivers.IWiFiStatusInterface;
import com.godchin.codelife.testui.AnimRoundProcessDialog;
import com.godchin.codelife.view.TitleBar;
import com.godchin.codelife.view.TitleBar.TitleBarClickListener;

public class MainActivity extends Activity implements TitleBarClickListener,
		IWiFiStatusInterface {
	TitleBar titleBar;

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
	}

	@Override
	public void leftClick() {
		Toast.makeText(this, "left", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void rightClick() {
		Toast.makeText(this, "right", Toast.LENGTH_SHORT).show();

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
