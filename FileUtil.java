package com.wj.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

/**
 * @Title:
 * @Package
 * @Description: TODO(文件操作工具类，注：需要文件读写权限)
 * @author wangjiang wangjiang7747@gmail.com
 * @date 2016-3-7 下午6:41:11
 * @version V1.0
 */
public final class FileUtil {

	/**
	 * 存储过滤的文件，需要创建对象
	 */
	private List<File> mFilterFiles = new LinkedList<File>();

	/**
	 * @param path
	 *            文件路径，例如：/cache；如果path为"/",则文件存在于手机根目录下
	 * @param fileName
	 *            1.txt
	 * @return 文件
	 */
	public static File createFile(String path, String fileName) {
		return new File(getRootFile(path), fileName);
	}

	/**
	 * 获得根路径文件
	 * 
	 * @param path
	 *            文件路径，例如：/cache
	 * @return 根路径文件
	 */
	public static File getRootFile(String path) {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			throw new UnsupportedOperationException(
					"The SDCard does not exist.");
		}

		File file = null;
		if (path == null || path.equals("/")) {
			file = Environment.getExternalStorageDirectory();
		} else {
			file = new File(Environment.getExternalStorageDirectory().getPath()
					+ path);
		}
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

	/**
	 * 将文本写入SD卡，如果path为"/"，会将文件存储于根路径
	 * 
	 * @param path
	 *            文件路径 例如：/cache
	 * @param fileName
	 *            文件名字
	 * @param content
	 *            文本内容
	 * @param append
	 *            是否追加
	 * @throws Exception
	 *             文件不存在，读取文件出错
	 */
	public static void writeTextToSDCard(String path, String fileName,
			String content, boolean append) throws Exception {
		File desFile = createFile(path, fileName);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(desFile, append)));
		writer.write(content);
		writer.flush();
		writer.close();
	}

	/**
	 * 
	 * 从SDCard中读取文件，如果path为"/"，将会读取根路径下的文件
	 * 
	 * @param path
	 *            文件路径 例如：/cache
	 * @param fileName
	 *            文件名字
	 * @return 文本内容
	 * @throws Exception
	 *             文件不存在，读取文件出错
	 */
	public static String readTextFromSDCard(String path, String fileName)
			throws Exception {
		InputStream inputStream = new FileInputStream(
				createFile(path, fileName));
		byte[] buffer = new byte[inputStream.available()];
		int len = 0;
		while ((len = inputStream.read(buffer)) != -1) {
			inputStream.read(buffer);
		}
		inputStream.close();
		return new String(buffer);
	}

	/**
	 * 删除当前目录和子目录下的所有文件
	 * 
	 * @param directory
	 *            文件夹
	 */
	public static void remove(File directory) {
		if (directory != null && directory.exists() && directory.isDirectory()) {
			File[] files = directory.listFiles();
			for (File item : files) {
				if (item.isDirectory()) {
					remove(new File(item.getPath()));
				} else {
					item.delete();
				}
			}
		}
	}

	/**
	 * 
	 * 根据后缀名过滤文件，仅限当前目录下的文件，不包含子目录下的文件
	 * 
	 * @param path
	 *            文件路径
	 * @param suffix
	 *            文件后缀名，例如：.txt,.jpg等等
	 * @return 当前目录下（不包含子目录）符合后缀名的文件
	 */
	public static File[] filterFiles(String path, final String suffix) {
		File directory = getRootFile(path);
		if (directory == null)
			return null;

		FileFilter filter = new FileFilter() {

			@SuppressLint("DefaultLocale")
			@Override
			public boolean accept(File pathname) {
				String name = pathname.getName().toLowerCase();
				if (name.endsWith(suffix.toLowerCase())) {
					return true;
				}
				return false;
			}
		};

		return directory.listFiles(filter);
	}

	/**
	 * 根据后缀名过滤文件，当前目录下的文件，以及子目录下的文件，由于文件可能较多，请在子线程中执行
	 * 
	 * @param directory
	 *            根文件
	 * @param suffix
	 *            文件后缀名，例如：.txt,.jpg等等
	 * @return 当前目录下符合后缀名的所有文件
	 */
	@SuppressLint("DefaultLocale")
	public List<File> filterAllFiles(File directory, String suffix) {
		if (directory != null && directory.exists()) {
			File[] files = directory.listFiles();
			if (files != null) {
				for (File item : files) {
					if (item.isDirectory()) {
						filterAllFiles(new File(item.getPath()), suffix);
					} else {
						if (item.getName().toLowerCase()
								.endsWith(suffix.toLowerCase())) {
							mFilterFiles.add(item);
						}
					}
				}
			}
		}

		return mFilterFiles;
	}

	/**
	 * 复制文件
	 * 
	 * @param srcFile
	 *            复制源文件
	 * @param desFile
	 *            复制目标文件
	 * @throws IOException
	 */
	public static void copyTo(File srcFile, File desFile) throws IOException {

		BufferedOutputStream outputStream = new BufferedOutputStream(
				new FileOutputStream(desFile));
		BufferedInputStream inputStream = new BufferedInputStream(
				new FileInputStream(srcFile));
		byte[] buffer = new byte[inputStream.available()];
		int len = 0;
		while ((len = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer);
		}
		inputStream.close();
		outputStream.flush();
		outputStream.close();

	}

	/**
	 * 把数据写入指定文件
	 * 
	 * @param path
	 *            文件路径 例如：/cache
	 * @param fileName
	 *            文件名字
	 * @param data
	 *            要写入的数据
	 * @throws IOException
	 */
	public static void writeDataToSDCard(String path, String fileName,
			byte[] data) throws IOException {
		writeDataToSDCard(createFile(path, fileName), data);
	}

	/**
	 * 
	 * 把数据写入指定文件
	 * 
	 * @param file
	 *            指定文件
	 * @param data
	 *            要写入的数据
	 * @throws IOException
	 */
	public static void writeDataToSDCard(File file, byte[] data)
			throws IOException {
		BufferedOutputStream out = new BufferedOutputStream(
				new FileOutputStream(file));
		out.write(data);
		out.flush();
		out.close();
	}

	/**
	 * 把数据写入指定文件
	 * 
	 * @param path
	 *            文件路径 例如：/cache
	 * @param fileName
	 *            文件名字
	 * @param inputStream
	 *            输入流
	 * @throws IOException
	 */
	public static void writeDataToSDCard(String path, String fileName,
			InputStream inputStream) throws IOException {
		writeDataToSDCard(createFile(path, fileName), inputStream);
	}

	/**
	 * 
	 * 把数据写入指定文件
	 * 
	 * @param file
	 *            指定文件
	 * @param inputStream
	 *            输入流
	 * @throws IOException
	 */
	public static void writeDataToSDCard(File file, InputStream inputStream)
			throws IOException {

		BufferedOutputStream out = new BufferedOutputStream(
				new FileOutputStream(file));
		BufferedInputStream in = new BufferedInputStream(inputStream);
		byte[] data = new byte[in.available()];
		int len = 0;
		while ((len = in.read(data)) != -1) {
			out.write(data);
		}
		in.close();
		out.flush();
		out.close();
	}

	/**
	 * 将Assets中的文件复制到SDCard中
	 * 
	 * @param context
	 *            上下文对象
	 * @param assetFileName
	 *            Assets文件夹下的文件名字
	 * @param desFile
	 *            在SDCard中的文件
	 * @throws IOException
	 *             文件不存在或者读取文件出错
	 */
	public static void copyAssetsToSDCard(Context context,
			String assetFileName, File desFile) throws IOException {
		AssetManager am = context.getAssets();
		InputStream inputStream = am.open(assetFileName);
		writeDataToSDCard(desFile, inputStream);
	}

}
