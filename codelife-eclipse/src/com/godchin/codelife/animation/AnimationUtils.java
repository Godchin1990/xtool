package com.godchin.codelife.animation;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.godchin.codelife.R;

@SuppressLint("NewApi")
public class AnimationUtils {
	public static final int ROTATE_REVERSE = -1;
	public static final int ROTATE_REVERSE_DOUBLE = -2;
	public static final int ROTATE_DEFAULT = 1;
	public static final int ROTATE_DOUBLE = 2;

	public static ValueAnimator rotateAnimation(View view, int rotate_mode) {
		ValueAnimator rotateAnimator = null;
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			rotateAnimator = ObjectAnimator.ofFloat(view, "rotation", 0,
					rotate_mode * 360);
			rotateAnimator.setInterpolator(new LinearInterpolator());
			rotateAnimator.setDuration(1000);
			rotateAnimator.setRepeatCount(Integer.MAX_VALUE);
			rotateAnimator.start();
		}
		return rotateAnimator;
	}

	public static ValueAnimator translateAnimation(View view, int startY,
			int endY) {
		ValueAnimator translateAnimator = null;
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			translateAnimator = ObjectAnimator.ofFloat(view, "translationY",
					startY, endY);
			translateAnimator.setDuration(800);
			translateAnimator.setRepeatCount(Integer.MAX_VALUE);
			translateAnimator.setRepeatMode(ValueAnimator.REVERSE);
			translateAnimator.start();
		}
		return translateAnimator;
	}

	public static ValueAnimator alphaAnimation(View view, int startA, int endA) {
		ValueAnimator alphaAnimator = null;
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			alphaAnimator = ObjectAnimator.ofFloat(view, "alpha", startA, endA);
			alphaAnimator.setDuration(10000);
			alphaAnimator.start();
		}
		return alphaAnimator;
	}

	public static ValueAnimator rotateZAnimation(View view, int rotate_mode,
			int startDegree) {
		ValueAnimator rotateAnimator = null;
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			rotateAnimator = ObjectAnimator.ofFloat(view, "rotationY",
					startDegree, rotate_mode * 90);
			rotateAnimator.setInterpolator(new LinearInterpolator());
			rotateAnimator.setDuration(500);
			// rotateAnimator.setRepeatCount(Integer.MAX_VALUE);
			rotateAnimator.start();
		}
		return rotateAnimator;
	}

	public static ObjectAnimator shake(View view) {
		int delta = view.getResources()
				.getDimensionPixelOffset(R.dimen.shake_x);

		PropertyValuesHolder pvhTranslateX = PropertyValuesHolder.ofKeyframe(
				View.TRANSLATION_X, Keyframe.ofFloat(0f, 0),
				Keyframe.ofFloat(.10f, -delta), Keyframe.ofFloat(.26f, delta),
				Keyframe.ofFloat(.42f, -delta), Keyframe.ofFloat(.58f, delta),
				Keyframe.ofFloat(.74f, -delta), Keyframe.ofFloat(.90f, delta),
				Keyframe.ofFloat(1f, 0f));

		return ObjectAnimator.ofPropertyValuesHolder(view, pvhTranslateX)
				.setDuration(1000);
	}

}
