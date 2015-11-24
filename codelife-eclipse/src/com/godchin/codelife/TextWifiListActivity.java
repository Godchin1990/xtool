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
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.godchin.codelife.rx.wifi.ConnectivityStatus;
import com.godchin.codelife.rx.wifi.ReactiveNetwork;

public class TextWifiListActivity extends Activity {

	private static final String TAG = "ReactiveNetwork";
	private TextView tvConnectivityStatus;
	private ListView lvAccessPoints;
	private ReactiveNetwork reactiveNetwork;
	private Subscription wifiSubscription;
	private Subscription connectivitySubscription;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.textrxandroidtctivity);
		tvConnectivityStatus = (TextView) findViewById(R.id.connectivity_status);
		lvAccessPoints = (ListView) findViewById(R.id.access_points);

		reactiveNetwork = new ReactiveNetwork();
		// reactiveNetwork.enableInternetCheck();
		connectivitySubscription = reactiveNetwork.observeConnectivity(this)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Action1<ConnectivityStatus>() {
					@Override
					public void call(ConnectivityStatus connectivityStatus) {
						Log.d(TAG, connectivityStatus.toString());
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

	@Override
	protected void onResume() {
		super.onResume();
	}

	private void displayAccessPoints(List<ScanResult> scanResults) {
		List<String> ssids = new ArrayList<String>();

		for (ScanResult scanResult : scanResults) {
			ssids.add(scanResult.SSID);
		}

		int itemLayoutId = android.R.layout.simple_list_item_1;
		lvAccessPoints.setAdapter(new ArrayAdapter<String>(this, itemLayoutId,
				ssids));
	}

	@Override
	protected void onPause() {
		super.onPause();
		connectivitySubscription.unsubscribe();
		wifiSubscription.unsubscribe();
	}
}
