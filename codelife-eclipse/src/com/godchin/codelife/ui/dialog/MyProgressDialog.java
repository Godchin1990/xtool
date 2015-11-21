package com.godchin.codelife.ui.dialog;

import java.util.Timer;
import java.util.TimerTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

/*
 *   可以设定超时的时间以及超时后的行为，使用时可以通过静态Create的方式*   创建一个MyProgressDialog实例对象
 */
public class MyProgressDialog extends ProgressDialog {

	public static final String TAG = "ProgressDialog";
	private long mTimeOut = 0; // 默认timeOut为0即无限大
	private OnTimeOutListener mTimeOutListener = null; // timeOut后的处理器
	private Timer mTimer = null;// 定时器
	/*
	 * 超时后才调用该Handler
	 */
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (mTimeOutListener != null) {
				mTimeOutListener.onTimeOut(MyProgressDialog.this);
				dismiss(); // 释放掉ProgressDialog
			}
		}
	};

	public MyProgressDialog(Context context) {
		super(context);
	}

	/**
	 * 设置timeOut长度，和处理器
	 * 
	 * @param t
	 *            timeout时间长度
	 * @param timeOutListener
	 *            超时后的处理器
	 */
	public void setTimeOut(long t, OnTimeOutListener timeOutListener) {
		mTimeOut = t;
		if (timeOutListener != null) {
			this.mTimeOutListener = timeOutListener;
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		if (mTimeOut > 0) {
			mTimer = new Timer();
			TimerTask timerTask = new TimerTask() {
				@Override
				public void run() {
					Message msg = mHandler.obtainMessage();
					mHandler.sendMessage(msg);
				}
			};
			mTimer.schedule(timerTask, mTimeOut); // 设定mTimeout时间后调用TimerTask任务
		}

	}

	/**
	 * 通过静态Create的方式创建一个实例对象
	 * 
	 * @param context
	 * @param time
	 *            timeout时间长度
	 * @param listener
	 *            timeOutListener 超时后的处理器
	 * @return MyProgressDialog 对象
	 */
	public static MyProgressDialog createProgressDialog(Context context,
			long time, OnTimeOutListener listener) {
		MyProgressDialog progressDialog = new MyProgressDialog(context);
		if (time != 0) {
			progressDialog.setTimeOut(time, listener);
		}
		return progressDialog;
	}

	/**
	 * 
	 * 处理超时的的接口
	 * 
	 */
	public interface OnTimeOutListener {

		/**
		 * 当progressDialog超时时调用此方法
		 */
		abstract public void onTimeOut(ProgressDialog dialog);
	}
}