

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

/**
 * @author wangjiang
 * 
 *         存储测试的文本和图片
 */
public class DebugFile extends Debug {

	/**
	 * 是否开启Debug模式
	 */
	public static boolean IS_PRIVATE_DEBUG = true;

	/**
	 * 存储的文件路径
	 */
	private static final String ROOT_FILEPATH = "/mnt/sdcard/debug";

	public enum FileType {
		TEXT("text", ".txt"), IMAGE("image", ".jpg");
		// 文件的名字
		private String filename;
		// 文件的格式
		private String format;

		FileType(String filename, String format) {
			this.filename = filename;
			this.format = format;
		}

		public String getFilename() {
			return filename;
		}

		public String getFormat() {
			return format;
		}
	}

	/**
	 * 将文本写入SD卡
	 * 
	 * @param foldername
	 *            文件夹的名字
	 * @param filename
	 *            文件名字
	 * @param content
	 *            文件内容
	 * @param append
	 *            是否追加
	 */
	public static void writeTextToSDCard(String foldername, String filename,
			String content, boolean append) {

		if (!isDebug())
			return;

		FileType fileType = FileType.TEXT;
		File desFile = getRootFile(ROOT_FILEPATH + File.separator + foldername,
				fileType);
		if (desFile != null) {
			writeText(new File(desFile, filename + fileType.getFormat()),
					content, append);
		}
	}

	/**
	 * 将文本写入SD卡
	 * 
	 * @param filename
	 *            文件名字
	 * @param content
	 *            文件内容
	 * @param append
	 *            是否追加
	 */
	public static void writeTextToSDCard(String filename, String content,
			boolean append) {

		if (!isDebug())
			return;

		FileType fileType = FileType.TEXT;
		File desFile = new File(getRootFile(ROOT_FILEPATH, fileType), filename
				+ fileType.getFormat());
		if (desFile != null)
			writeText(desFile, content, append);
	}

	/**
	 * 写入文本
	 * 
	 * @param desFile
	 *            文本文件
	 * @param content
	 *            文件内容
	 * @param append
	 *            是否追加
	 */
	private static void writeText(File desFile, String content, boolean append) {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(desFile, append)));
			writer.write(content);
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将图片存入SD卡
	 * 
	 * @param context
	 *            上下文对象
	 * @param foldername
	 *            文件夹名字
	 * @param filename
	 *            文件名字
	 * @param bitmap
	 *            图片
	 */
	public static void writeImageToSDCard(Context context, String foldername,
			String filename, Bitmap bitmap) {
		if (!isDebug())
			return;
		if (bitmap == null)
			throw new NullPointerException("The bitmap is null!");
		FileType fileType = FileType.IMAGE;
		File desFile = new File(getRootFile(ROOT_FILEPATH + File.separator
				+ foldername, fileType), filename + fileType.getFormat());
		if (desFile != null)
			writeImage(context, bitmap, desFile);
	}

	/**
	 * 将图片存入SD卡
	 * 
	 * @param context
	 *            上下文对象
	 * @param filename
	 *            文件夹名字
	 * @param bitmap
	 *            图片
	 */
	public static void writeImageToSDCard(Context context, String filename,
			Bitmap bitmap) {
		if (!isDebug())
			return;
		if (bitmap == null)
			throw new NullPointerException("The bitmap is null!");
		FileType fileType = FileType.IMAGE;
		File desFile = new File(getRootFile(ROOT_FILEPATH, fileType), filename
				+ fileType.getFormat());
		if (desFile != null)
			writeImage(context, bitmap, desFile);
	}

	/**
	 * 写入图片
	 * 
	 * @param context
	 *            上下文对象
	 * @param bitmap
	 *            图片
	 * @param desFile
	 *            图片文件
	 */
	private static void writeImage(Context context, Bitmap bitmap, File desFile) {

		boolean result = false;
		try {
			OutputStream outputStream = new FileOutputStream(desFile);
			result = bitmap.compress(Bitmap.CompressFormat.JPEG, 100,
					outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (result) {
			// 显示在相册
			Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			intent.setData(Uri.fromFile(new File(ROOT_FILEPATH)));
			context.sendBroadcast(intent);
		}

	}

	/**
	 * 获得存储的根路径文件
	 * 
	 * @param dirPath
	 *            根路径
	 * @param fileType
	 *            文件类型
	 * @return 根路径文件
	 */
	private static File getRootFile(String dirPath, FileType fileType) {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			throw new UnsupportedOperationException(
					"The SDCard does not exist.");
		}
		File file = new File(dirPath, fileType.getFilename());

		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

	/**
	 * @return 是否开启Debug模式
	 */
	private static boolean isDebug() {
		return IS_PUBLIC_DEBUG && IS_PRIVATE_DEBUG;
	}
}
