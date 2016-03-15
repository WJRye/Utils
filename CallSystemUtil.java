package com.wj.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;

/**
 * @Title:
 * @Package
 * @Description: TODO(常用的一些调用系统功能工具)
 * @author wangjiang wangjiang7747@gmail.com
 * @date 2016-3-10 上午11:15:36
 * @version V1.0
 */
public final class CallSystemUtil {

	private CallSystemUtil() {
	}

	@SuppressLint("NewApi")
	private static void startActivity(Object context, Intent intent,
			int requestCode) {
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		if (context instanceof Fragment) {
			((Fragment) context).startActivityForResult(intent, requestCode);
		} else if (context instanceof Activity) {
			((Activity) context).startActivityForResult(intent, requestCode);
		}
	}

	/**
	 * 调用系统发送短信界面
	 * 
	 * @param context
	 *            上下文对象
	 * @param nubmer
	 *            电话号码，可以为空
	 * @param smsBody
	 *            短信内容
	 */
	public static void sendSMS(Object context, String nubmer, String smsBody) {
		Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"
				+ nubmer));
		intent.putExtra("sms_body", smsBody);
		intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
		startActivity(context, intent, -1);
	}

	/**
	 * 
	 * 直接拨打电话，需要权限android.permission.CALL_PHONE
	 * 
	 * @param context
	 *            上下文对象
	 * @param number
	 *            电话号码
	 */
	public static void call(Object context, String number) {
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
				+ number));
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(context, intent, -1);
	}

	/**
	 * 
	 * 跳入系统拨打电话界面
	 * 
	 * @param context
	 *            上下文对象
	 * @param number
	 *            电话号码
	 */
	public static void dial(Object context) {
		Intent intent = new Intent(Intent.ACTION_CALL_BUTTON);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(context, intent, -1);
	}

	/**
	 * 
	 * 添加联系人，返回结果后，通过ContentUris.parseId(data.getData())获取新添加的联系人的contactId
	 * 
	 * @param context
	 *            上下文对象
	 * @param data
	 *            添加联系人额外的数据：例如电话号码(ContactsContract.Intents.Insert.PHONE)，姓名(
	 *            ContactsContract.Intents.Insert.NAME)等等
	 * @param requestCode
	 *            请求码
	 */
	public static void insertContact(Object context, Intent data,
			int requestCode) {
		Intent intent = new Intent(data);
		intent.setAction(Intent.ACTION_INSERT);
		intent.setType("vnd.android.cursor.dir/raw_contact");// vnd.android.cursor.dir/person,vnd.android.cursor.dir/contact
		intent.putExtra("finishActivityOnSaveCompleted", true);
		startActivity(context, intent, requestCode);
	}

	/**
	 * 
	 * 如果联系人存在，则编辑联系人，否则添加联系人
	 * 
	 * @param context
	 *            上下文对象
	 * @param data
	 *            编辑或者添加联系人额外的数据：例如电话号码(ContactsContract.Intents.Insert.PHONE)，
	 *            姓名( ContactsContract.Intents.Insert.NAME)等等
	 * @param requestCode
	 *            请求码
	 */
	public static void editOrInsertContact(Object context, Intent data,
			int requestCode) {
		Intent intent = new Intent(data);
		intent.setAction(Intent.ACTION_INSERT_OR_EDIT);
		intent.setType("vnd.android.cursor.dir/raw_contact");// vnd.android.cursor.dir/person,vnd.android.cursor.dir/contact
		intent.putExtra("finishActivityOnSaveCompleted", true);
		startActivity(context, intent, requestCode);
	}

	/**
	 * 编辑联系人
	 * 
	 * @param context
	 *            上下文对象
	 * @param contactId
	 *            联系人Id
	 * @param requestCode
	 *            请求码
	 */
	public static void editContact(Object context, int contactId,
			int requestCode) {
		Intent intent = new Intent(Intent.ACTION_EDIT,
				Uri.parse("content://com.android.contacts/contacts/"
						+ contactId));
		intent.putExtra("finishActivityOnSaveCompleted", true);
		intent.addCategory("android.intent.category.DEFAULT");
		startActivity(context, intent, requestCode);
	}

	/**
	 * 从网站搜索内容
	 * 
	 * @param context
	 *            上下文对象
	 * @param content
	 *            搜索内容
	 */
	public static void webSearch(Object context, String content) {
		Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
		intent.putExtra(SearchManager.QUERY, content);
		startActivity(context, intent, -1);
	}

	/**
	 * 浏览网址(https://www.baidu.com)，显示地图，路径规划等等
	 * 
	 * @param context
	 *            上下文对象
	 * @param content
	 *            浏览内容
	 */
	public static void browse(Object context, String content) {
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(content));
		startActivity(context, intent, -1);
	}

	/**
	 * 发送邮件
	 * 
	 * @param context
	 *            上下文对象
	 * @param email
	 *            邮件
	 * @param text
	 *            邮件内容
	 */
	public static void sendEmail(Object context, String[] email, String text) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_EMAIL, email);
		intent.putExtra(Intent.EXTRA_TEXT, text);
		intent.setType("text/plain");
		startActivity(context, Intent.createChooser(intent, "选择邮箱"), -1);
	}

	/**
	 * 发送邮件
	 * 
	 * @param context
	 *            上下文对象
	 * @param email
	 *            邮件
	 * @param ccs
	 *            抄送
	 * @param text
	 *            文本
	 * @param subject
	 *            主题
	 * @param filePath
	 *            附件
	 */
	public static void sendEmail(Object context, String[] email, String[] ccs,
			String text, String subject, String filePath) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_EMAIL, email);
		intent.putExtra(Intent.EXTRA_TEXT, text);
		intent.putExtra(Intent.EXTRA_CC, ccs);
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		intent.putExtra(Intent.EXTRA_STREAM, filePath);
		intent.setType("*/*");
		startActivity(context, Intent.createChooser(intent, "选择邮箱"), -1);
	}

	/**
	 * 选择文件
	 * 
	 * @param context
	 *            上下文
	 * @param requestCode
	 *            请求码
	 */
	private static void pickFile(Object context, String mimeType, String title,
			int requestCode) {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType(mimeType);
		startActivity(context, Intent.createChooser(intent, title), requestCode);
	}

	/**
	 * 选择图片
	 * 
	 * @param context
	 *            上下文
	 * @param requestCode
	 *            请求码
	 */
	public static void pickImage(Object context, int requestCode) {
		pickFile(context, "image/*", "选择图片", requestCode);
	}

	/**
	 * 选择音频
	 * 
	 * @param context
	 *            上下文
	 * @param requestCode
	 *            请求码
	 */
	public static void pickAudio(Object context, int requestCode) {
		pickFile(context, "audio/*", "选择音频", requestCode);
	}

	/**
	 * 选择视频
	 * 
	 * @param context
	 *            上下文
	 * @param requestCode
	 *            请求码
	 */
	public static void pickVideo(Object context, int requestCode) {
		pickFile(context, "video/*", "选择视频", requestCode);
	}

	/**
	 * 删除应用
	 * 
	 * @param context
	 *            上下文
	 * @param packageName
	 *            包名
	 */
	public static void uninstallAPP(Object context, String packageName) {
		Uri uri = Uri.fromParts("package", packageName, null);
		Intent intent = new Intent(Intent.ACTION_DELETE, uri);
		startActivity(context, intent, -1);
	}

}
