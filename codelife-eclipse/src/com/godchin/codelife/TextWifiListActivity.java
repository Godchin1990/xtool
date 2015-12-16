package com.godchin.codelife;

/*
 * Copyright (C) 2015 Piotr Wittchen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.godchin.codelife.log.LogUtil;
import com.godchin.codelife.rx.wifi.ConnectivityStatus;
import com.godchin.codelife.rx.wifi.ReactiveNetwork;
import com.godchin.codelife.testui.OnNetworkChangeListener;
import com.godchin.codelife.testui.WifiConnDialog;
import com.godchin.codelife.testui.WifiStatusDialog;
import com.godchin.codelife.view.button.SlipButton;
import com.godchin.codelife.view.button.SlipButton.OnChangedListener;
import com.godchin.codelife.view.tool.SwipeBackActivity;
import com.godchin.codelife.wifi.WifiAdmin;
import com.joanzapata.android.listview.BaseAdapterHelper;
import com.joanzapata.android.listview.QuickAdapter;

public class TextWifiListActivity extends Activity {

	private static final String TAG = "ReactiveNetwork";
	private TextView tvConnectivityStatus;
	private ListView lvAccessPoints;
	private SlipButton wifiswitch;
	private ReactiveNetwork reactiveNetwork;
	private Subscription wifiSubscription;
	private Subscription connectivitySubscription;
	// Wifi管理类
	private WifiAdmin mWifiAdmin;
	// 扫描结果列表
	private List<ScanResult> list = new ArrayList<ScanResult>();

	// 取得WifiManager对象
	private WifiManager mWifiManager;
	private WifiInfo connInfo;
	private ConnectivityManager cm;

	private QuickAdapter<ScanResult> mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.textrxandroidtctivity);
		initdata();
		initview();
		initlisener();
	}

	private void initdata() {
		mWifiAdmin = new WifiAdmin(TextWifiListActivity.this);
		mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		connInfo = mWifiManager.getConnectionInfo();
		cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

	}

	private void initlisener() {
		// // 开关
		int wifiState = mWifiAdmin.checkState();
		if (wifiState == WifiManager.WIFI_STATE_DISABLED
				|| wifiState == WifiManager.WIFI_STATE_DISABLING
				|| wifiState == WifiManager.WIFI_STATE_UNKNOWN) {
			wifiswitch.setCheck(false);
		} else {
			wifiswitch.setCheck(true);
		}

		wifiswitch.SetOnChangedListener(new OnChangedListener() {
			@Override
			public void OnChanged(boolean CheckState) {
				if (CheckState) {
					LogUtil.i("======== open wifi ========");
					// 打开Wifi
					mWifiAdmin.openWifi();
				} else {
					LogUtil.i("======== close wifi ========");
					// 关闭Wifi
					mWifiAdmin.closeWifi();
				}
			}
		});

		lvAccessPoints
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView adapterView, View view,
							int position, long arg3) {
						ScanResult scanResult = list.get(position);
						String desc = "";
						String descOri = scanResult.capabilities;
						if (descOri.toUpperCase().contains("WPA-PSK")) {
							desc = "WPA";
						}
						if (descOri.toUpperCase().contains("WPA2-PSK")) {
							desc = "WPA2";
						}
						if (descOri.toUpperCase().contains("WPA-PSK")
								&& descOri.toUpperCase().contains("WPA2-PSK")) {
							desc = "WPA/WPA2";
						}
						if (desc.equals("")) {
							isConnectSelf(scanResult);
							return;
						}
						isConnect(scanResult);
					}
				});
		// ==========================================
		// lvAccessPoints
		// .setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
		// {
		//
		// @Override
		// public boolean onItemLongClick(AdapterView<?> arg0,
		// View arg1, int position, long arg3) {
		//
		// WifiSeeConfig wifiSeeConfig = new WifiSeeConfig(
		// TextWifiListActivity.this);
		// ArrayList<String[]> get_list = wifiSeeConfig.get_list();
		// for (String[] strings : get_list) {
		// if (strings[0] == list.get(position).SSID) {
		// Toast.makeText(getApplicationContext(),
		// strings[1], Toast.LENGTH_SHORT).show();
		// return true;
		// }
		// }
		//
		// return true;
		// }
		// });
		// -------------------------------------------------
		// lvAccessPoints
		// .setOnItemLongClickListener(new OnItemLongClickListener() {
		//
		// @Override
		// public boolean onItemLongClick(AdapterView<?> arg0,
		// View arg1, final int arg2, long arg3) {
		// new AlertDialog.Builder(ListOnLongClickActivity.this)
		// .setTitle("对Item进行操作")
		// .setItems(R.array.arrcontent,
		// new DialogInterface.OnClickListener() {
		// public void onClick(
		// DialogInterface dialog,
		// int which) {
		// String[] PK =new String []{"11","22"} ;
		// Toast.makeText(
		// ListOnLongClickActivity.this,
		// PK[which],
		// Toast.LENGTH_LONG)
		// .show();
		// if (PK[which].equals("删除")) {
		//
		// }
		// }
		// })
		// .setNegativeButton("取消",
		// new DialogInterface.OnClickListener() {
		// public void onClick(
		// DialogInterface dialog,
		// int which) {
		//
		//
		// }
		// }).show();
		// return true;
		// }
		// });
		// ======================================

		lvAccessPoints
				.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

					public void onCreateContextMenu(ContextMenu menu, View v,
							ContextMenuInfo menuInfo) {

						menu.add(0, 0, 0, "购买");
						menu.add(0, 1, 0, "收藏");
						menu.add(0, 2, 0, "对比");

					}
				});
	}

	// 长按菜单响应函数
	public boolean onContextItemSelected(MenuItem item) {

		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();

		int MID = (int) info.id;// 这里的info.id对应的就是数据库中_id的值

		switch (item.getItemId()) {
		case 0:
			// 添加操作
			Toast.makeText(TextWifiListActivity.this, "添加", Toast.LENGTH_SHORT)
					.show();
			break;

		case 1:
			// 删除操作

			break;

		case 2:
			// 删除ALL操作
			break;

		default:
			break;
		}

		return super.onContextItemSelected(item);

	}

	private void isConnectSelf(ScanResult scanResult) {
		if (mWifiAdmin.isConnect(scanResult)) {

			// 已连接，显示连接状态对话框
			WifiStatusDialog mStatusDialog = new WifiStatusDialog(this,
					R.style.PopDialog, scanResult, mOnNetworkChangeListener);
			mStatusDialog.show();

		} else {
			boolean iswifi = mWifiAdmin.connectSpecificAP(scanResult);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (iswifi) {
				Toast.makeText(this, "连接成功！", 0).show();
			} else {
				Toast.makeText(this, "连接失败！", 0).show();
			}
		}
	}

	private void isConnect(ScanResult scanResult) {
		if (mWifiAdmin.isConnect(scanResult)) {
			// 已连接，显示连接状态对话框
			WifiStatusDialog mStatusDialog = new WifiStatusDialog(this,
					R.style.PopDialog, scanResult, mOnNetworkChangeListener);
			mStatusDialog.show();
		} else {
			// 未连接显示连接输入对话框
			WifiConnDialog mDialog = new WifiConnDialog(this,
					R.style.PopDialog, scanResult, mOnNetworkChangeListener);
			mDialog.show();
		}
	}

	private OnNetworkChangeListener mOnNetworkChangeListener = new OnNetworkChangeListener() {

		@Override
		public void onNetWorkDisConnect() {
		}

		@Override
		public void onNetWorkConnect() {

		}
	};

	private void initview() {
		wifiswitch = (SlipButton) findViewById(R.id.wifiswitch);
		tvConnectivityStatus = (TextView) findViewById(R.id.connectivity_status);
		lvAccessPoints = (ListView) findViewById(R.id.access_points);
	}

	@Override
	protected void onResume() {
		super.onResume();
		reactiveNetwork = new ReactiveNetwork();
		// reactiveNetwork.enableInternetCheck();
		connectivitySubscription = reactiveNetwork.observeConnectivity(this)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Action1<ConnectivityStatus>() {
					@Override
					public void call(ConnectivityStatus connectivityStatus) {

						tvConnectivityStatus.setText(connectivityStatus
								.toString());

					}
				});

		wifiSubscription = reactiveNetwork.observeWifiAccessPoints(this)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Action1<List<ScanResult>>() {
					@Override
					public void call(List<ScanResult> scanResults) {
						displayAccessPoints(scanResults);
					}
				});

	}

	private void displayAccessPoints(List<ScanResult> scanResults) {
		// List<String> ssids = new ArrayList<String>();
		//
		// for (ScanResult scanResult : scanResults) {
		// ssids.add(scanResult.SSID);
		// }
		//
		// int itemLayoutId = android.R.layout.simple_list_item_1;
		// lvAccessPoints.setAdapter(new ArrayAdapter<String>(this,
		// itemLayoutId,
		// ssids));
		list = scanResults;
		mAdapter = new QuickAdapter<ScanResult>(this, R.layout.wifi_info_item,
				list) {

			@Override
			protected void convert(BaseAdapterHelper helper, ScanResult item) {

				helper.setText(R.id.tv_wifi_name, item.SSID);
				// Wifi 描述
				String desc = "";
				String descOri = item.capabilities;
				if (descOri.toUpperCase().contains("WPA-PSK")) {
					desc = "WPA";
				}
				if (descOri.toUpperCase().contains("WPA2-PSK")) {
					desc = "WPA2";
				}
				if (descOri.toUpperCase().contains("WPA-PSK")
						&& descOri.toUpperCase().contains("WPA2-PSK")) {
					desc = "WPA/WPA2";
				}

				if (TextUtils.isEmpty(desc)) {
					desc = "未受保护的网络";
				} else {
					desc = "通过 " + desc + " 进行保护";
				}

				// 是否连接，如果刚刚断开连接，connInfo.SSID==null
				connInfo = mWifiManager.getConnectionInfo();
				State wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
						.getState();
				if (wifi == State.CONNECTED) {
					WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
					String g1 = wifiInfo.getSSID();

					String g2 = "\"" + item.SSID + "\"";

					if (g2.endsWith(g1)) {
						desc = "已连接";
					}
				}

				helper.setText(R.id.tv_wifi_desc, desc);

				// 网络信号强度
				int level = item.level;
				int imgId = R.drawable.wifi05;
				if (Math.abs(level) > 100) {
					imgId = R.drawable.wifi05;
				} else if (Math.abs(level) > 80) {
					imgId = R.drawable.wifi04;
				} else if (Math.abs(level) > 70) {
					imgId = R.drawable.wifi04;
				} else if (Math.abs(level) > 60) {
					imgId = R.drawable.wifi03;
				} else if (Math.abs(level) > 50) {
					imgId = R.drawable.wifi02;
				} else {
					imgId = R.drawable.wifi01;
				}

				helper.setImageResource(R.id.iv_wifi_level, imgId);
				helper.setText(R.id.tv_signal,
						WifiManager.calculateSignalLevel(item.level, 100) + "%");
			}
		};

		lvAccessPoints.setAdapter(mAdapter);

	}

	@Override
	protected void onPause() {
		super.onPause();
		connectivitySubscription.unsubscribe();
		wifiSubscription.unsubscribe();
	}

}
