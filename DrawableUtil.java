package com.wj.base.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.content.res.AppCompatResources;

/**
 * Author：王江 on 2017/6/15 21:15
 * Description:Drawable相关的工具类
 */

public final class DrawableUtil {

    private DrawableUtil() {
    }

    /**
     * @param pressedDrawable 按住时的Drawable
     * @param normalDrawable  普通情况下的Drawable
     * @return StateListDrawable
     */
    public static StateListDrawable getStateDrawable(Drawable pressedDrawable, Drawable normalDrawable) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressedDrawable);
        drawable.addState(new int[]{android.R.attr.state_enabled}, normalDrawable);
        return drawable;
    }

    /**
     * 同一个Drawable，不同的状态
     *
     * @param context      上下文对象
     * @param resId        图片资源id
     * @param pressedColor 按住时的颜色
     * @param normalColor  普通情况下的颜色
     * @return StateListDrawable
     */
    public static StateListDrawable getStateDrawable(Context context, @DrawableRes int resId, @ColorRes int pressedColor, @ColorRes int normalColor) {
        Drawable pressedDrawable = AppCompatResources.getDrawable(context, resId).mutate();
        Drawable normalDrawable = AppCompatResources.getDrawable(context, resId).mutate();
        pressedDrawable.setColorFilter(ContextCompat.getColor(context, pressedColor), PorterDuff.Mode.SRC_IN);
        normalDrawable.setColorFilter(ContextCompat.getColor(context, normalColor), PorterDuff.Mode.SRC_IN);
        return getStateDrawable(pressedDrawable, normalDrawable);
    }

    /**
     * 获得着色后的图片
     *
     * @param context 上下文对象
     * @param resId   图片资源
     * @param colorId 颜色资源
     * @return 着色后的Drawable
     */
    public static Drawable getTintDrawable(Context context, @DrawableRes int resId, @ColorRes int colorId) {
        Drawable drawable = AppCompatResources.getDrawable(context, resId);
        ColorStateList colorStateList = ContextCompat.getColorStateList(context, colorId);
        return getTintDrawable(drawable, colorStateList);
    }


    /**
     * 给图片着色
     *
     * @param drawable       要着色的Drawable
     * @param colorStateList 颜色状态列表
     * @return 着色后的Drawable
     */
    public static Drawable getTintDrawable(Drawable drawable, ColorStateList colorStateList) {
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colorStateList);
        return wrappedDrawable;
    }

    /**
     * 给图片着色
     *
     * @param srcDrawable 要着色的Drawable
     * @param color       着色颜色
     * @return 着色后的Drawable
     */
    public static Drawable getColorDrawable(Drawable srcDrawable, int color) {
        srcDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        return srcDrawable;
    }
}
