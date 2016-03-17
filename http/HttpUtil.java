package com.wj.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import android.webkit.CookieManager;

/**
 * @Title:
 * @Package
 * @Description: TODO(该类用于HTTP请求的工具类)
 * @author wangjiang wangjiang7747@gmail.com
 * @date 2016-3-15 下午4:45:46
 * @version V1.0
 */
public final class HttpUtil {

	/**
	 * 编码格式
	 */
	private static final String CHARSET = "UTF-8";
	/**
	 * GET请求方式
	 */
	private static final String GET = "GET";
	/**
	 * POST请求方式
	 */
	private static final String POST = "POST";

	private HttpUtil() {
	}

	/**
	 * 将输入流转换为字符串
	 * 
	 * @param inputStream
	 *            输入流
	 * @return 字符串
	 * @throws IOException
	 *             读取错误
	 */
	private static final String read(InputStream inputStream)
			throws IOException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[inputStream.available()];
		int len = 0;
		while ((len = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inputStream.close();
		return new String(outStream.toByteArray(), CHARSET);
	}

	/**
	 * 发送GET请求
	 * 
	 * @param path
	 *            请求地址
	 * @return 返回的结果
	 * @throws IOException
	 *             请求或者读取出错
	 */
	public static final String doGet(String path) throws IOException {
		String result = null;
		HttpURLConnection conn = (HttpURLConnection) new URL(path)
				.openConnection();
		conn.setConnectTimeout(5000);
		conn.setReadTimeout(10000);
		conn.setRequestMethod(GET);
		conn.setDoInput(true);
		conn.setDoOutput(false);
		conn.setRequestProperty("Cookie", getCookie(path));
		conn.setRequestProperty("Accept-Charset", CHARSET);
		conn.setRequestProperty("Connection:", "close");
		if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			result = read(conn.getInputStream());
		}
		conn.disconnect();
		return result;
	}

	/**
	 * 发送POST请求
	 * 
	 * @param path
	 *            访问地址
	 * @param params
	 *            参数 key-value
	 * @return 返回结果
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static final String doPost(String path, Map<String, String> params)
			throws MalformedURLException, IOException {
		return doPost(path, params, null);
	}

	/**
	 * 发送POST请求
	 * 
	 * @param path
	 *            访问地址
	 * @param params
	 *            参数 key-value
	 * @param paramsMulti
	 *            参数 key-values
	 * @return 返回结果
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static final String doPost(String path, Map<String, String> params,
			Map<String, List<String>> paramsMulti)
			throws MalformedURLException, IOException {
		StringBuilder data = new StringBuilder();
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				if (entry.getValue() != null && entry.getValue().length() != 0) {
					data.append(entry.getKey()).append("=");
					data.append(URLEncoder.encode(entry.getValue(), CHARSET));
					data.append("&");
				}
			}
		}
		if (paramsMulti != null && !paramsMulti.isEmpty()) {
			for (Map.Entry<String, List<String>> entry : paramsMulti.entrySet()) {
				List<String> values = entry.getValue();
				for (String value : values) {
					data.append(entry.getKey()).append("=");
					data.append(URLEncoder.encode(value, CHARSET));
					data.append("&");
				}
			}
		}
		if (data.length() >= 1)
			data.deleteCharAt(data.length() - 1);

		return doPost(path, data.toString().getBytes());
	}

	private static final String doPost(String path, byte[] entity)
			throws MalformedURLException, IOException {
		HttpURLConnection conn = (HttpURLConnection) new URL(path)
				.openConnection();
		conn.setReadTimeout(10000);
		conn.setConnectTimeout(5000);
		conn.setRequestMethod(POST);
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length",
				Integer.toString(entity.length));
		conn.setRequestProperty("Cookie", getCookie(path));
		OutputStream outStream = conn.getOutputStream();
		outStream.write(entity);
		outStream.close();

		String result = null;
		if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			result = read(conn.getInputStream());
			setCookie(conn, path);
		}
		conn.disconnect();
		return result;
	}

	/**
	 * 模仿表单的形式上传文件
	 * 
	 * @param path
	 *            访问地址
	 * @param params
	 *            参数
	 * @param file
	 *            文件
	 * @return 返回结果
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static final String uploadFile(String path,
			Map<String, String> params, File file)
			throws MalformedURLException, IOException {
		String result = null;
		if (file != null && file.isFile()) {
			final String boundary = "-----------------------------7dec138207ee";
			final String twoHyphens = "--";
			final String lineFeed = "\r\n";
			HttpURLConnection conn = (HttpURLConnection) new URL(path)
					.openConnection();
			conn.setReadTimeout(20000);
			conn.setConnectTimeout(5000);
			conn.setRequestMethod(POST);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestProperty("Accept",
					"text/html, application/xhtml+xml, */*");
			conn.setRequestProperty("Charset", CHARSET);
			conn.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			// conn.setRequestProperty("Content-Length", "");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.setRequestProperty("Cookie", getCookie(path));

			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

			if (params != null && !params.isEmpty()) {
				for (Map.Entry<String, String> entry : params.entrySet()) {
					if (entry.getValue() != null
							&& entry.getValue().length() != 0) {
						dos.writeBytes(twoHyphens + boundary + lineFeed);
						dos.writeBytes("Content-Disposition: form-data; name=\""
								+ entry.getKey() + "\"");
						dos.writeBytes(lineFeed);
						dos.writeBytes(lineFeed);
						dos.writeBytes(entry.getValue());
						dos.writeBytes(lineFeed);
					}
				}
			}

			dos.writeBytes(twoHyphens + boundary + lineFeed);
			dos.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\""
					+ file.getName() + "\"");
			dos.writeBytes(lineFeed);
			dos.writeBytes("Content-Type: "
					+ URLConnection.getFileNameMap().getContentTypeFor(
							file.getName()));
			dos.writeBytes(lineFeed);
			dos.writeBytes(lineFeed);
			dos.writeBytes(read(new FileInputStream(file)));
			dos.writeBytes(lineFeed);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + lineFeed);
			dos.flush();
			dos.close();

			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				result = read(conn.getInputStream());
				setCookie(conn, path);
			}
			conn.disconnect();
		}
		return result;
	}

	/**
	 * 获得存储的Cookie
	 * 
	 * @param path
	 *            地址
	 * @return cookie
	 */
	private static String getCookie(String path) {
		return CookieManager.getInstance().getCookie(path);
	}

	/**
	 * 存储返回的Cookie
	 * 
	 * @param conn
	 *            HttpURLConnection
	 * @param path
	 *            地址
	 */
	private static void setCookie(HttpURLConnection conn, String path) {
		String cookieValue = null;
		String key = null;
		for (int i = 1; (key = conn.getHeaderFieldKey(i)) != null; i++) {
			if (key.equals("Set-Cookie")) {
				cookieValue = conn.getHeaderField(i);
			}
		}
		if (cookieValue != null) {
			// 删掉以前的cookie
			CookieManager cookieManager = CookieManager.getInstance();
			cookieManager.removeAllCookie();
			// 添加新的cookie
			cookieManager.setCookie(path, cookieValue);
		}
	}
}