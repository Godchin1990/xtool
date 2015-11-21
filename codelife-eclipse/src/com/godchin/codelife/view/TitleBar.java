package com.godchin.codelife.view;

import com.godchin.codelife.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class TitleBar extends RelativeLayout {
	private TextView left_tv, right_tv, title_tv;
	private View title_view;
	private String title_text, left_text, right_text;
	private int title_text_color, left_text_color, right_text_color;
	private Drawable left_background, right_background;

	private TitleBarClickListener titleBarClickListener;

	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.title_bar);
		title_text = ta.getString(R.styleable.title_bar_title_text);
		left_text = ta.getString(R.styleable.title_bar_left_text);
		right_text = ta.getString(R.styleable.title_bar_right_text);

		title_text_color = ta.getColor(R.styleable.title_bar_title_text_color,
				0);
		left_text_color = ta.getColor(R.styleable.title_bar_left_text_color, 0);
		right_text_color = ta.getColor(R.styleable.title_bar_right_text_color,
				0);

		left_background = ta.getDrawable(R.styleable.title_bar_left_background);
		right_background = ta
				.getDrawable(R.styleable.title_bar_right_background);
		ta.recycle();

		title_view = inflate(context, R.layout.title_layout, null);
		left_tv = (TextView) title_view.findViewById(R.id.left_tv);
		right_tv = (TextView) title_view.findViewById(R.id.right_tv);
		title_tv = (TextView) title_view.findViewById(R.id.title_tv);

		left_tv.setText(left_text);
		left_tv.setTextColor(left_text_color);
		left_tv.setBackground(left_background);

		right_tv.setText(right_text);
		right_tv.setTextColor(right_text_color);
		right_tv.setBackground(right_background);

		title_tv.setText(title_text);
		title_tv.setTextColor(title_text_color);

		addView(title_view);

		left_tv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (titleBarClickListener != null) {
					titleBarClickListener.leftClick();
				}
			}
		});

		right_tv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (titleBarClickListener != null) {
					titleBarClickListener.rightClick();
				}
			}
		});

	}

	public void setTopBarClickListener(
			TitleBarClickListener titleBarClickListener) {
		this.titleBarClickListener = titleBarClickListener;
	}

	public String getTitle_text() {
		return title_text;
	}

	public void setTitle_text(String title_text) {
		this.title_text = title_text;
		title_tv.setText(title_text);
	}

	public String getLeft_text() {
		return left_text;
	}

	public void setLeft_text(String left_text) {
		this.left_text = left_text;
		left_tv.setText(left_text);
	}

	public String getRight_text() {
		return right_text;
	}

	public void setRight_text(String right_text) {
		this.right_text = right_text;
		right_tv.setText(right_text);

	}

	public int getTitle_text_color() {
		return title_text_color;
	}

	public void setTitle_text_color(int title_text_color) {
		this.title_text_color = title_text_color;
		title_tv.setTextColor(title_text_color);
	}

	public int getLeft_text_color() {
		return left_text_color;
	}

	public void setLeft_text_color(int left_text_color) {
		this.left_text_color = left_text_color;
		left_tv.setTextColor(left_text_color);
	}

	public int getRight_text_color() {
		return right_text_color;
	}

	public void setRight_text_color(int right_text_color) {
		this.right_text_color = right_text_color;
		right_tv.setTextColor(right_text_color);
	}

	public Drawable getLeft_background() {
		return left_background;
	}

	public void setLeft_background(Drawable left_background) {
		this.left_background = left_background;
		left_tv.setBackground(left_background);
	}

	public Drawable getRight_background() {
		return right_background;
	}

	public void setRight_background(Drawable right_background) {
		this.right_background = right_background;
		left_tv.setBackground(right_background);
	}

	public interface TitleBarClickListener {

		void leftClick();

		void rightClick();
	}
}
