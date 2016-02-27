

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;

/**
 * @author wangjiang
 * 
 *         Bitmap的工具转换类，对Bitmap进行存储、压缩、圆形处理等
 * 
 */
public class BitmapUtil {

	private BitmapUtil() {
	}

	/**
	 * @param srcBitmap
	 *            原始bitmap
	 * @return 圆形bitmap
	 * @throws OutOfMemoryError
	 *             内存溢出
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap srcBitmap)
			throws OutOfMemoryError {
		int width = srcBitmap.getWidth();
		int height = srcBitmap.getHeight();

		int radius = 0;
		// 以短的一边的一半作为圆形的半径
		if (width <= height) {
			radius = width / 2;
		} else {
			radius = height / 2;
		}

		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setColor(Color.WHITE);

		Rect rect = new Rect(0, 0, 2 * radius, 2 * radius);
		RectF rectF = new RectF(rect);
		Bitmap desBitmap = Bitmap.createBitmap(2 * radius, 2 * radius,
				Bitmap.Config.ARGB_4444);
		Canvas canvas = new Canvas(desBitmap);
		// 抗锯齿
		canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
				| Paint.FILTER_BITMAP_FLAG));
		canvas.drawARGB(0, 0, 0, 0);
		// 绘制下层图
		canvas.drawRoundRect(rectF, radius, radius, paint);
		// 设置PorterDuffXfermode模式，取绘制的图的交集部分，显示上层
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		// 绘制上层图
		canvas.drawBitmap(srcBitmap, rect, rect, paint);

		return desBitmap;
	}

	/**
	 * 如果实际的图片小于目标的宽高，那么将对图片进行放大处理
	 * 
	 * @param srcBitmap
	 *            原始bitmap
	 * @param dstWidth
	 *            目标宽度
	 * @param dstHeight
	 *            目标高度
	 * @return 圆形bitmap
	 * @throws OutOfMemoryError
	 *             内存溢出
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap srcBitmap, int dstWidth,
			int dstHeight) throws OutOfMemoryError {
		checkDstWidthAndDstHeight(dstWidth, dstHeight);

		int width = srcBitmap.getWidth();
		int height = srcBitmap.getHeight();
		// 目标宽高大于实际bitmap的宽高，因此对图片进行放大
		if (width < dstWidth && height < dstHeight) {
			srcBitmap = Bitmap.createScaledBitmap(srcBitmap, dstWidth,
					dstHeight, true);
			return getRoundedCornerBitmap(srcBitmap);
		}
		int radius = 0;

		// 选择最小的一边的一半作为圆形的半径
		if (width <= height) {
			if (dstWidth <= dstHeight) {
				if (width >= dstWidth) {
					radius = dstWidth / 2;
				} else {
					radius = width / 2;
				}
			} else {
				if (width >= dstHeight) {
					radius = dstHeight / 2;
				} else {
					radius = width / 2;
				}
			}
		} else {
			if (dstWidth <= dstHeight) {
				if (height >= dstWidth) {
					radius = dstWidth / 2;
				} else {
					radius = height / 2;
				}
			} else {
				if (height >= dstHeight) {
					radius = dstHeight / 2;
				} else {
					radius = height / 2;
				}
			}
		}

		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setColor(Color.WHITE);

		Rect rect = new Rect(0, 0, 2 * radius, 2 * radius);
		RectF rectF = new RectF(rect);
		Bitmap desBitmap = Bitmap.createBitmap(2 * radius, 2 * radius,
				Bitmap.Config.ARGB_4444);
		Canvas canvas = new Canvas(desBitmap);
		// 抗锯齿
		canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
				| Paint.FILTER_BITMAP_FLAG));
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawRoundRect(rectF, radius, radius, paint);
		// 取两层绘制交集，显示上层
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

		canvas.drawBitmap(srcBitmap, rect, rect, paint);

		return desBitmap;
	}

	/**
	 * @param srcBitmap
	 *            原始图片
	 * @param dstWidth
	 *            目标宽度
	 * @param dstHeight
	 *            目标高度
	 * @param circleRingR
	 *            边框的宽度
	 * @return 带有圆形边框的圆形bitmap
	 * @throws OutOfMemoryError
	 *             内存溢出
	 */
	public static Bitmap getBitmapWithBorder(Bitmap srcBitmap, int dstWidth,
			int dstHeight, float borderWidth, int borderColor)
			throws OutOfMemoryError {
		checkDstWidthAndDstHeight(dstWidth, dstHeight);

		int w = 0;
		int h = 0;
		if (srcBitmap.getWidth() <= dstWidth
				|| srcBitmap.getHeight() <= dstHeight) {
			w = srcBitmap.getWidth();
			h = srcBitmap.getHeight();
		} else {
			w = dstWidth;
			h = dstHeight;
		}

		// 边框的半径
		float circleRadius = 0f;
		// bitmap的半径
		float bmRadius = 0f;
		if (w < h) {
			bmRadius = w / 2 - borderWidth;
			circleRadius = w / 2 - borderWidth / 2;
		} else {
			bmRadius = h / 2 - borderWidth;
			circleRadius = h / 2 - borderWidth / 2;
		}
		// 将原始bitmap转换为圆形bitmap
		Bitmap desBitmap = getRoundedCornerBitmap(srcBitmap, w, h);
		Bitmap drawBitmap = desBitmap.copy(Bitmap.Config.ARGB_4444, true);

		Canvas canvas = new Canvas(desBitmap);
		// 抗锯齿
		canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
				| Paint.FILTER_BITMAP_FLAG));
		canvas.drawColor(Color.WHITE, Mode.CLEAR);

		// 画圆形图片
		RectF oval = new RectF(desBitmap.getWidth() / 2 - bmRadius,
				desBitmap.getHeight() / 2 - bmRadius, desBitmap.getWidth() / 2
						+ bmRadius, desBitmap.getHeight() / 2 + bmRadius);
		Paint bmPaint = new Paint();
		bmPaint.setDither(true);
		bmPaint.setAntiAlias(true);
		bmPaint.setStyle(Paint.Style.FILL);
		bmPaint.setColor(Color.WHITE);
		canvas.drawBitmap(drawBitmap, null, oval, bmPaint);

		// 画边框
		Paint circlePaint = new Paint();
		circlePaint.setDither(true);
		circlePaint.setAntiAlias(true);
		circlePaint.setStyle(Paint.Style.STROKE);
		circlePaint.setStrokeWidth(borderWidth);
		circlePaint.setColor(borderColor);
		circlePaint.setAlpha(153);
		canvas.drawCircle(desBitmap.getWidth() / 2, desBitmap.getHeight() / 2,
				circleRadius, circlePaint);
		drawBitmap.recycle();
		drawBitmap = null;

		return desBitmap;
	}

	/**
	 * 检查目标宽度和目标高度是否为0
	 * 
	 * @param dstWidth
	 *            目标宽度
	 * @param dstHeight
	 *            目标高度
	 */
	private static void checkDstWidthAndDstHeight(int dstWidth, int dstHeight) {
		if (dstWidth == 0 || dstHeight == 0)
			throw new IllegalArgumentException(
					"dstWidth and dstHeight can't be 0!");
	}

	/**
	 * @param context
	 *            上下文对象
	 * @param uri
	 *            图片的路径
	 * @param dstWidth
	 *            目标宽度
	 * @param dstHeight
	 *            目标高度
	 * @return 压缩后的bitmap
	 */
	public static final Bitmap compress(Context context, String uri,
			int dstWidth, int dstHeight) {
		checkDstWidthAndDstHeight(dstWidth, dstHeight);

		Bitmap bitmap = null;
		try {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new BufferedInputStream(
					new FileInputStream(uri)), null, opts);
			int height = opts.outHeight;
			int width = opts.outWidth;
			int inSampleSize = 1;
			// 图片旋转的度数
			int degree = readPictureDegree(uri);
			if (degree == 0 || degree == 180) {
				if (width > height && width > dstWidth) {
					inSampleSize = Math.round((float) width / (float) dstWidth);
				} else if (height > width && height > dstHeight) {
					inSampleSize = Math.round((float) height
							/ (float) dstHeight);
				}
			} else if (degree == 90 || degree == 270) {
				// 图片有旋转时，宽和高调换了
				if (width > height && width > dstHeight) {
					inSampleSize = Math
							.round((float) width / (float) dstHeight);
				} else if (height > width && height > dstWidth) {
					inSampleSize = Math
							.round((float) height / (float) dstWidth);
				}
			}

			if (inSampleSize <= 1)
				inSampleSize = 1;
			opts.inSampleSize = inSampleSize;
			opts.inPreferredConfig = Bitmap.Config.ARGB_4444;
			// bitmap可清除
			opts.inPurgeable = true;
			// bitmap可copy
			opts.inInputShareable = true;
			opts.inTargetDensity = context.getResources().getDisplayMetrics().densityDpi;
			opts.inScaled = true;
			opts.inTempStorage = new byte[16 * 1024];
			opts.inJustDecodeBounds = false;
			bitmap = BitmapFactory.decodeStream(new BufferedInputStream(
					new FileInputStream(uri)), null, opts);
			if (bitmap != null) {
				// 处理旋转了一定角度的图片，比如有些机型拍出的照片默认旋转了90度的
				Matrix matrix = new Matrix();
				matrix.postRotate(degree);
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
						bitmap.getHeight(), matrix, false);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			// 防止内存溢出导致程序崩溃而强制退出
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * @param context
	 *            上下文对象
	 * @param bytes
	 *            图片的byte数据
	 * @param dstWidth
	 *            目标宽度
	 * @param dstHeight
	 *            目标高度
	 * @return 压缩后的bitmap
	 */
	public static final Bitmap compress(Context context, byte[] bytes,
			int dstWidth, int dstHeight) {
		checkDstWidthAndDstHeight(dstWidth, dstHeight);

		Bitmap bitmap = null;
		try {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
			int height = opts.outHeight;
			int width = opts.outWidth;

			int inSampleSize = 1;

			if (width > height && width > dstWidth) {
				inSampleSize = Math.round((float) width / (float) dstWidth);
			} else if (height > width && height > dstHeight) {
				inSampleSize = Math.round((float) height / (float) dstHeight);
			}

			if (inSampleSize <= 1)
				inSampleSize = 1;
			opts.inSampleSize = inSampleSize;
			opts.inPreferredConfig = Bitmap.Config.ARGB_4444;
			// bitmap可清除
			opts.inPurgeable = true;
			// bitmap可copy
			opts.inInputShareable = true;
			opts.inTargetDensity = context.getResources().getDisplayMetrics().densityDpi;
			opts.inScaled = true;
			opts.inTempStorage = new byte[16 * 1024];
			opts.inJustDecodeBounds = false;
			bitmap = BitmapFactory
					.decodeByteArray(bytes, 0, bytes.length, opts);
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			// 防止内存溢出导致程序崩溃而强制退出
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * @param path
	 *            图片路径
	 * @return 图片旋转的度数
	 */
	private static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	 /**
	 * @param bitmap
	 *            要保存的bitmap
	 * @param file
	 *            要保存的文件
	 * @param quality
	 *            0~100 图片质量
	 * @return 保存是否成功
	 * @throws IOException
	 * @throws FileNotFoundException
	 *             注意：可能没有添加对文件的读写权限
	 */
	public static boolean saveBitmap(Bitmap bitmap, File file, int quality)
			throws IOException, FileNotFoundException {
		boolean result = false;

		if (bitmap == null)
			throw new NullPointerException("bitmap can't be null!");

		OutputStream outputStream = new FileOutputStream(file);
		result = bitmap.compress(Bitmap.CompressFormat.JPEG, quality,
				outputStream);
		outputStream.flush();
		outputStream.close();

		return result;
	}

	/**
	 * 从Assets文件下获取图片
	 * 
	 * @param context
	 *            上下文对象
	 * @param fileName
	 *            文件名字
	 * @return 结果bitmap
	 */
	public static Bitmap getBitmapFromAssets(Context context, String fileName) {
		AssetManager assetManager = context.getAssets();
		InputStream inputStream = null;
		Bitmap bitmap = null;
		try {
			inputStream = assetManager.open(fileName);
			bitmap = BitmapFactory.decodeStream(inputStream);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;

	}

}
