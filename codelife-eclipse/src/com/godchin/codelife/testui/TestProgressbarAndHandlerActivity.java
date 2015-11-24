package com.godchin.codelife.testui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

import com.godchin.codelife.R;
import com.godchin.codelife.view.RoundProgressBar;

public class TestProgressbarAndHandlerActivity extends Activity {
	private Button button = null;
	private ProgressBar progressbar = null;
	private RoundProgressBar round;
	private Handler updateprogressbarhanler = new Handler() {
		public void handleMessage(Message msg) {
			progressbar.setProgress(msg.arg1);
			updateprogressbarhanler.post(updateThread);
			round.setProgress(msg.arg1);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.testhandlerprogressbar);
		button = (Button) findViewById(R.id.btn_progress);
		progressbar = (ProgressBar) findViewById(R.id.progressbar);
		round = (RoundProgressBar) findViewById(R.id.round);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				progressbar.setVisibility(View.VISIBLE);
				updateprogressbarhanler.post(updateThread);
			}
		});

	}

	Runnable updateThread = new Runnable() {
		int i = 0;

		@Override
		public void run() {
			i = i + 1;
			Message msg = updateprogressbarhanler.obtainMessage();
			msg.arg1 = i;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			updateprogressbarhanler.sendMessage(msg);
			if (i == 100) {
				updateprogressbarhanler.removeCallbacks(updateThread);
			}
		}
	};

}
