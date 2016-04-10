package com.wj.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

public class StateListUtil {

	public static Drawable getStatePressedDrawable(Context context,
			int pressedDrawableId, int normalDrawableId) {
		return getStateDrawable(context, android.R.attr.state_focused,
				pressedDrawableId, normalDrawableId);
	}

	public static Drawable getStateFocusedDrawable(Context context,
			int focusedDrawableId, int normalDrawableId) {
		return getStateDrawable(context, android.R.attr.state_focused,
				focusedDrawableId, normalDrawableId);
	}

	private static Drawable getStateDrawable(Context context, int state,
			int stateDrawableId, int normalDrawableId) {
		StateListDrawable drawable = new StateListDrawable();
		drawable.addState(new int[] { state, android.R.attr.state_enabled },
				context.getResources().getDrawable(stateDrawableId));
		drawable.addState(new int[] { android.R.attr.state_enabled }, context
				.getResources().getDrawable(normalDrawableId));
		return drawable;
	}

	public static Drawable getStatePressedDrawable(int pressedColor,
			int normalColor) {
		return getStateDrawableByColor(android.R.attr.state_pressed,
				pressedColor, normalColor);
	}

	public static Drawable getStateFocusedDrawable(int foucsedColor,
			int normalColor) {
		return getStateDrawableByColor(android.R.attr.state_focused,
				foucsedColor, normalColor);
	}

	private static Drawable getStateDrawableByColor(int state, int stateColor,
			int normalColor) {
		StateListDrawable drawable = new StateListDrawable();
		drawable.addState(new int[] { state, android.R.attr.state_enabled },
				new ColorDrawable(stateColor));
		drawable.addState(new int[] { android.R.attr.state_enabled },
				new ColorDrawable(normalColor));
		return drawable;
	}

	public static ColorStateList getColorStatePressed(int pressedColor,
			int normalColor) {
		return getColorStateList(android.R.attr.state_pressed,
				pressedColor, normalColor);
	}

	public static ColorStateList getColorStateFocused(int focusedColor,
			int normalColor) {
		return getColorStateList(android.R.attr.state_focused,
				focusedColor, normalColor);
	}

	private static ColorStateList getColorStateList(int state,
			int stateColor, int normal) {
		int[][] states = { { state, android.R.attr.state_enabled },
				{ android.R.attr.state_enabled } };
		int[] colors = { stateColor, normal };
		return new ColorStateList(states, colors);
	}
}
