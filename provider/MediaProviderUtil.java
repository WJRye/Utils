package com.example.utils.usercase.provider;

import java.io.File;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Video;

/**
 * @Title:
 * @Package
 * @Description: TODO(该类主要用于获取媒体库中图片，音频，视频的地址的信息)
 * @author wangjiang wangjiang7747@gmail.com
 * @date 2016-3-9 下午4:50:07
 * @version V1.0
 */
public final class MediaProviderUtil {

	public static final Uri IMAGE = Images.Media.EXTERNAL_CONTENT_URI;
	public static final Uri VIDEO = Video.Media.EXTERNAL_CONTENT_URI;
	public static final Uri AUDIO = Audio.Media.EXTERNAL_CONTENT_URI;

	private MediaProviderUtil() {
	}

	/**
	 * 获取系统所有图片，视屏，音频的路径，排序为时间倒叙
	 * 
	 * @param context
	 *            上下文对象
	 * @return 图片，视屏，音频的路径
	 */
	public static ArrayList<String> getMediaUris(Context context, Uri uri) {
		ArrayList<String> uirs = new ArrayList<String>();
		ContentResolver cr = context.getContentResolver();
		Cursor cursor = cr.query(uri, new String[] {
				MediaStore.MediaColumns.DATA,
				MediaStore.MediaColumns.DATE_ADDED }, null, null,
				"date_added desc");
		if (cursor != null) {
			while (cursor.moveToNext()) {
				uirs.add(cursor.getString(cursor
						.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)));
			}
			cursor.close();
			cursor = null;
		}

		return uirs;
	}

	/**
	 * 读取视屏的第一帧，需要API 10以上
	 * 
	 * @param path
	 *            视频路径
	 * @return 第一帧图片
	 * @throws Exception
	 *             读取错误
	 */
	@SuppressLint("NewApi")
	public static Bitmap getVideoFirstFrame(String path) throws Exception {
		return getVideoFrameAtTime(path, 0L);
	}

	/**
	 * 读取视频某一帧的图片，需要API 10以上
	 * 
	 * @param path
	 *            视频路径
	 * @param timeUs
	 *            视频的时间
	 * @return 位于视频多少时间的图片
	 * @throws Exception
	 *             读取错误
	 */
	@SuppressLint("NewApi")
	public static Bitmap getVideoFrameAtTime(String path, long timeUs)
			throws Exception {
		Bitmap bitmap = null;
		MediaMetadataRetriever retriever = new MediaMetadataRetriever();
		retriever.setDataSource(path);
		bitmap = retriever.getFrameAtTime(timeUs);
		retriever.release();
		return bitmap;
	}

	/**
	 * 读取音频中的图片，通常用于读取mp3的专辑封面，需要API 10以上
	 * 
	 * @param path
	 *            音频路径
	 * @return 音频中的图片
	 * @throws Exception
	 *             读取错误
	 */
	@SuppressLint("NewApi")
	public static Bitmap getAudioAlbumImage(String path) throws Exception {
		Bitmap bitmap = null;
		MediaMetadataRetriever retriever = new MediaMetadataRetriever();
		retriever.setDataSource(path);
		byte[] data = retriever.getEmbeddedPicture();
		bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
		retriever.release();
		return bitmap;
	}

	/**
	 * 将文件扫入媒体库，或者使用MediaScannerConnection类
	 * 
	 * @param context
	 *            上下文对象
	 * @param path
	 *            文件路径
	 */
	public static void scan(Context context, String path) {
		Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		intent.setData(Uri.fromFile(new File(path)));
		context.sendBroadcast(intent);
	}

}
