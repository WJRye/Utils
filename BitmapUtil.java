

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
 *         Bitmap�Ĺ���ת���࣬��Bitmap���д洢��ѹ����Բ�δ����
 * 
 */
public class BitmapUtil {

	private BitmapUtil() {
	}

	/**
	 * @param srcBitmap
	 *            ԭʼbitmap
	 * @return Բ��bitmap
	 * @throws OutOfMemoryError
	 *             �ڴ����
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap srcBitmap)
			throws OutOfMemoryError {
		int width = srcBitmap.getWidth();
		int height = srcBitmap.getHeight();

		int radius = 0;
		// �Զ̵�һ�ߵ�һ����ΪԲ�εİ뾶
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
		// �����
		canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
				| Paint.FILTER_BITMAP_FLAG));
		canvas.drawARGB(0, 0, 0, 0);
		// �����²�ͼ
		canvas.drawRoundRect(rectF, radius, radius, paint);
		// ����PorterDuffXfermodeģʽ��ȡ���Ƶ�ͼ�Ľ������֣���ʾ�ϲ�
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		// �����ϲ�ͼ
		canvas.drawBitmap(srcBitmap, rect, rect, paint);

		return desBitmap;
	}

	/**
	 * ���ʵ�ʵ�ͼƬС��Ŀ��Ŀ�ߣ���ô����ͼƬ���зŴ���
	 * 
	 * @param srcBitmap
	 *            ԭʼbitmap
	 * @param dstWidth
	 *            Ŀ����
	 * @param dstHeight
	 *            Ŀ��߶�
	 * @return Բ��bitmap
	 * @throws OutOfMemoryError
	 *             �ڴ����
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap srcBitmap, int dstWidth,
			int dstHeight) throws OutOfMemoryError {
		checkDstWidthAndDstHeight(dstWidth, dstHeight);

		int width = srcBitmap.getWidth();
		int height = srcBitmap.getHeight();
		// Ŀ���ߴ���ʵ��bitmap�Ŀ�ߣ���˶�ͼƬ���зŴ�
		if (width < dstWidth && height < dstHeight) {
			srcBitmap = Bitmap.createScaledBitmap(srcBitmap, dstWidth,
					dstHeight, true);
			return getRoundedCornerBitmap(srcBitmap);
		}
		int radius = 0;

		// ѡ����С��һ�ߵ�һ����ΪԲ�εİ뾶
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
		// �����
		canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
				| Paint.FILTER_BITMAP_FLAG));
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawRoundRect(rectF, radius, radius, paint);
		// ȡ������ƽ�������ʾ�ϲ�
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

		canvas.drawBitmap(srcBitmap, rect, rect, paint);

		return desBitmap;
	}

	/**
	 * @param srcBitmap
	 *            ԭʼͼƬ
	 * @param dstWidth
	 *            Ŀ����
	 * @param dstHeight
	 *            Ŀ��߶�
	 * @param circleRingR
	 *            �߿�Ŀ��
	 * @return ����Բ�α߿��Բ��bitmap
	 * @throws OutOfMemoryError
	 *             �ڴ����
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

		// �߿�İ뾶
		float circleRadius = 0f;
		// bitmap�İ뾶
		float bmRadius = 0f;
		if (w < h) {
			bmRadius = w / 2 - borderWidth;
			circleRadius = w / 2 - borderWidth / 2;
		} else {
			bmRadius = h / 2 - borderWidth;
			circleRadius = h / 2 - borderWidth / 2;
		}
		// ��ԭʼbitmapת��ΪԲ��bitmap
		Bitmap desBitmap = getRoundedCornerBitmap(srcBitmap, w, h);
		Bitmap drawBitmap = desBitmap.copy(Bitmap.Config.ARGB_4444, true);

		Canvas canvas = new Canvas(desBitmap);
		// �����
		canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
				| Paint.FILTER_BITMAP_FLAG));
		canvas.drawColor(Color.WHITE, Mode.CLEAR);

		// ��Բ��ͼƬ
		RectF oval = new RectF(desBitmap.getWidth() / 2 - bmRadius,
				desBitmap.getHeight() / 2 - bmRadius, desBitmap.getWidth() / 2
						+ bmRadius, desBitmap.getHeight() / 2 + bmRadius);
		Paint bmPaint = new Paint();
		bmPaint.setDither(true);
		bmPaint.setAntiAlias(true);
		bmPaint.setStyle(Paint.Style.FILL);
		bmPaint.setColor(Color.WHITE);
		canvas.drawBitmap(drawBitmap, null, oval, bmPaint);

		// ���߿�
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
	 * ���Ŀ���Ⱥ�Ŀ��߶��Ƿ�Ϊ0
	 * 
	 * @param dstWidth
	 *            Ŀ����
	 * @param dstHeight
	 *            Ŀ��߶�
	 */
	private static void checkDstWidthAndDstHeight(int dstWidth, int dstHeight) {
		if (dstWidth == 0 || dstHeight == 0)
			throw new IllegalArgumentException(
					"dstWidth and dstHeight can't be 0!");
	}

	/**
	 * @param context
	 *            �����Ķ���
	 * @param uri
	 *            ͼƬ��·��
	 * @param dstWidth
	 *            Ŀ����
	 * @param dstHeight
	 *            Ŀ��߶�
	 * @return ѹ�����bitmap
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
			// ͼƬ��ת�Ķ���
			int degree = readPictureDegree(uri);
			if (degree == 0 || degree == 180) {
				if (width > height && width > dstWidth) {
					inSampleSize = Math.round((float) width / (float) dstWidth);
				} else if (height > width && height > dstHeight) {
					inSampleSize = Math.round((float) height
							/ (float) dstHeight);
				}
			} else if (degree == 90 || degree == 270) {
				// ͼƬ����תʱ����͸ߵ�����
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
			// bitmap�����
			opts.inPurgeable = true;
			// bitmap��copy
			opts.inInputShareable = true;
			opts.inTargetDensity = context.getResources().getDisplayMetrics().densityDpi;
			opts.inScaled = true;
			opts.inTempStorage = new byte[16 * 1024];
			opts.inJustDecodeBounds = false;
			bitmap = BitmapFactory.decodeStream(new BufferedInputStream(
					new FileInputStream(uri)), null, opts);
			if (bitmap != null) {
				// ������ת��һ���Ƕȵ�ͼƬ��������Щ�����ĳ�����ƬĬ����ת��90�ȵ�
				Matrix matrix = new Matrix();
				matrix.postRotate(degree);
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
						bitmap.getHeight(), matrix, false);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			// ��ֹ�ڴ�������³��������ǿ���˳�
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * @param context
	 *            �����Ķ���
	 * @param bytes
	 *            ͼƬ��byte����
	 * @param dstWidth
	 *            Ŀ����
	 * @param dstHeight
	 *            Ŀ��߶�
	 * @return ѹ�����bitmap
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
			// bitmap�����
			opts.inPurgeable = true;
			// bitmap��copy
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
			// ��ֹ�ڴ�������³��������ǿ���˳�
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * @param path
	 *            ͼƬ·��
	 * @return ͼƬ��ת�Ķ���
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
	 *            Ҫ�����bitmap
	 * @param file
	 *            Ҫ������ļ�
	 * @param quality
	 *            0~100 ͼƬ����
	 * @return �����Ƿ�ɹ�
	 * @throws IOException
	 * @throws FileNotFoundException
	 *             ע�⣺����û����Ӷ��ļ��Ķ�дȨ��
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
	 * ��Assets�ļ��»�ȡͼƬ
	 * 
	 * @param context
	 *            �����Ķ���
	 * @param fileName
	 *            �ļ�����
	 * @return ���bitmap
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
