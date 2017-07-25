

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.widget.ImageView;

import com.baseproject.utils.Logger;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.youku.phone.cmscomponent.R;

/**
 * Created by wangjiang on 17/5/19.
 * <p>
 * 图片操作的工具类，希望所有关于图片的操作都写在这里面
 */

public final class ImageUtil {

    private static final String TAG = "ImageUtil";
    private ImageLoader mImageLoader;

    private ImageUtil() {
        mImageLoader = ImageLoader.getInstance();
    }

    private DisplayImageOptions getDisplayImageOptions(int imageDefaultRes) {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(imageDefaultRes) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(imageDefaultRes)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(imageDefaultRes)  //设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FlexibleRoundedBitmapDisplayer())
                .build();//构建完成
    }


    private static class ImageUtilHolder {

        private static ImageUtil INSTANCE = null;

        private static ImageUtil getInstance() {
            if (INSTANCE == null) {
                INSTANCE = new ImageUtil();
            }
            return INSTANCE;
        }
    }

    public static ImageUtil getInstance() {
        return ImageUtilHolder.getInstance();
    }


    /**
     * String imageUri = "http://site.com/image.png";      // from Web
     * String imageUri = "file:///mnt/sdcard/image.png";   // from SD card
     * String imageUri = "content://media/external/audio/albumart/13"; // from content provider
     * String imageUri = "assets://image.png";             // from assets
     * String imageUri = "drawable://" + R.drawable.image; // from drawables (only images, non-9patch)
     * <p>
     * /**
     *
     * @see #display(String, ImageView, int, ImageLoadingListener, ImageCorner)
     */
    public final void display(String uri, ImageView imageAware) {
        display(uri, imageAware, 0, null, null);
    }

    /**
     * @param uri             图片路径
     * @param imageAware      {@link ImageView ImageView}
     * @param imageDefaultRes drawable 资源
     * @param listener        ImageLoadingListener
     * @param imageCorner     {@link com.youku.phone.homecms.utils.ImageUtil.ImageCorner ImageCorner}
     */
    public final void display(String uri, ImageView imageAware, @DrawableRes int imageDefaultRes, ImageLoadingListener listener, ImageCorner imageCorner) {
        imageAware.setTag(R.id.image_corner, imageCorner);
        mImageLoader.displayImage(uri, imageAware, getDisplayImageOptions(imageDefaultRes), listener);
    }

    /**
     * @see #display(String, ImageView, int, ImageLoadingListener, ImageCorner)
     */
    public final void display(@DrawableRes int drawableId, ImageView imageAware) {
        display(drawableId, imageAware, 0, null);
    }

    /**
     * @param drawableId      图片资源id
     * @param imageAware      {@link ImageView ImageView}
     * @param imageDefaultRes 默认图片资源id
     * @param imageCorner     ImageCorner
     * @see #display(String, ImageView, int, ImageLoadingListener, ImageCorner)
     */
    public final void display(@DrawableRes int drawableId, ImageView imageAware, int imageDefaultRes, ImageCorner imageCorner) {
        if (drawableId != 0) {
            display("drawable://" + drawableId, imageAware, imageDefaultRes, null, imageCorner);
        } else {
            imageAware.setImageResource(0);
        }
    }


    private class FlexibleRoundedBitmapDisplayer implements BitmapDisplayer {

        @Override
        public final void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
            if (!(imageAware instanceof ImageViewAware)) {
                throw new IllegalArgumentException("ImageAware should wrap ImageView. ImageViewAware is expected.");
            }
            ImageView imageView = (ImageView) imageAware.getWrappedView();
            ImageCorner imageCorner = (ImageCorner) imageView.getTag(R.id.image_corner);
            Logger.d(TAG, "display-->imageCorner=" + (imageCorner == null));
            if (imageCorner != null) {
                if (imageCorner.isAll) {
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setImageDrawable(getRoundedBitmapDrawable(imageView.getResources(), bitmap, imageCorner.radius));
                } else {
                    try {
                        imageView.setImageBitmap(getRoundedCornerBitmap(bitmap, imageCorner, imageView.getWidth(), imageView.getHeight()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    } catch (OutOfMemoryError e) {
                        e.printStackTrace();
                    }
                }
            } else {
                imageView.setImageBitmap(bitmap);
            }
        }

    }

    /**
     * 获得圆角图片
     *
     * @param resources Resources
     * @param bitmap    Bitmap
     * @param radius    圆角大小
     * @return Drawable
     */
    public static Drawable getRoundedBitmapDrawable(Resources resources, Bitmap bitmap, int radius) {
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, bitmap);
        roundedBitmapDrawable.setCornerRadius(radius);
        roundedBitmapDrawable.setAntiAlias(true);
        return roundedBitmapDrawable;
    }


    /**
     * 获得圆角图片
     *
     * @param srcBitmap   源bitmap
     * @param imageCorner {@link com.youku.phone.homecms.utils.ImageUtil.ImageCorner ImageCorner}
     * @param dstWidth    目标宽度，通常为ImageView的宽度
     * @param dstHeight   目标高度，通常为ImageView的高度
     * @return 处理后的圆角图片
     * @throws IllegalArgumentException 异常，需要捕捉
     * @throws OutOfMemoryError         内存溢出，需要捕捉
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap srcBitmap, ImageCorner imageCorner, int dstWidth, int dstHeight)
            throws Exception, OutOfMemoryError {

        int radius = imageCorner.radius;

        if (srcBitmap == null || radius == 0) return srcBitmap;

        int width = srcBitmap.getWidth();
        int height = srcBitmap.getHeight();

        if (dstWidth != 0 && dstHeight != 0) {
            int x = 0;
            int y = 0;
            if (width < dstWidth) {
                if (height < dstHeight) {
                    srcBitmap = Bitmap.createScaledBitmap(srcBitmap, dstWidth, dstHeight, false);
                } else {
                    y = (dstHeight - height) / 2;
                    srcBitmap = Bitmap.createBitmap(srcBitmap, x, y, width, height - y);
                }
            } else {
                if (height < dstHeight) {
                    x = (width - dstWidth) / 2;
                    srcBitmap = Bitmap.createBitmap(srcBitmap, x, y, width - x, height);
                } else {
                    srcBitmap = Bitmap.createScaledBitmap(srcBitmap, dstWidth, dstHeight, false);
                }
            }

            width = dstWidth;
            height = dstHeight;
        }

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint.setColor(Color.WHITE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.FILL);

        Rect rect = new Rect(0, 0, width, height);
        RectF rectF = new RectF(rect);
        Bitmap desBitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(desBitmap);
        // 抗锯齿
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
                | Paint.FILTER_BITMAP_FLAG));
        canvas.drawARGB(0, 255, 255, 255);
        // 绘制下层图
        canvas.drawRoundRect(rectF, radius, radius, paint);


        if (!imageCorner.topLeft) {
            //绘制左上角不圆角
            RectF topLeft = new RectF(0, 0, 2 * radius, 2 * radius);
            canvas.drawRect(topLeft, paint);
        }

        if (!imageCorner.topRight) {
            //绘制右上角不圆角
            RectF topRight = new RectF(width - 2 * radius, 0, width, 2 * radius);
            canvas.drawRect(topRight, paint);
        }
        if (!imageCorner.bottomLeft) {
            //绘制左下角不圆角
            RectF bottomLeft = new RectF(0, height - 2 * radius, 2 * radius, height);
            canvas.drawRect(bottomLeft, paint);
        }
        if (!imageCorner.bottomRight) {
            //绘制右上角不圆角
            RectF bottomRight = new RectF(width - 2 * radius, height - 2 * radius, width, height);
            canvas.drawRect(bottomRight, paint);

        }
        // 设置PorterDuffXfermode模式，取绘制的图的交集部分，显示上层
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // 绘制上层图
        canvas.drawBitmap(srcBitmap, rect, rect, paint);

        paint.setColor(Color.TRANSPARENT);
        paint.setXfermode(null);

        return desBitmap;
    }


    public static class ImageCorner {
        /**
         * 圆角半径
         */
        private int radius;
        /**
         * 左上角是否显示圆角
         */
        private boolean topLeft;
        /**
         * 右上角是否显示圆角
         */
        private boolean topRight;
        /**
         * 左下角是否显示圆角
         */
        private boolean bottomLeft;
        /**
         * 右下角是否显示圆角
         */
        private boolean bottomRight;

        /**
         * 是不是四个角都是要圆角
         */
        private boolean isAll = false;

        /**
         * 默认圆角大小
         *
         * @see #ImageCorner(int, boolean, boolean, boolean, boolean)
         */
        public ImageCorner(Context context, boolean topLeft, boolean topRight, boolean bottomLeft, boolean bottomRight) {
            this(context.getResources().getDimensionPixelSize(R.dimen.home_waist_corner_size), topLeft, topRight, bottomLeft, bottomRight);
        }


        /**
         * 默认圆角大小
         *
         * @see #ImageCorner(int, boolean, boolean, boolean, boolean)
         */
        public ImageCorner(Context context) {
            this(context.getResources().getDimensionPixelSize(R.dimen.home_waist_corner_size), true, true, true, true);
        }


        /**
         * @param radius      圆角半径
         * @param topLeft     左上角是否显示圆角，true显示圆角，false不显示圆角
         * @param topRight    右上角是否显示圆角，true显示圆角，false不显示圆角
         * @param bottomLeft  左下角是否显示圆角，true显示圆角，false不显示圆角
         * @param bottomRight 右下角是否显示圆角，true显示圆角，false不显示圆角
         */
        public ImageCorner(int radius, boolean topLeft, boolean topRight, boolean bottomLeft, boolean bottomRight) {
            this.radius = radius;
            this.topLeft = topLeft;
            this.topRight = topRight;
            this.bottomLeft = bottomLeft;
            this.bottomRight = bottomRight;
            if (topLeft && topRight & bottomLeft & bottomRight) {
                isAll = true;
            }
        }
    }

}
