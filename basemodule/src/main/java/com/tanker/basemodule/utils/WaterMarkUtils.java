package com.tanker.basemodule.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;

import com.tanker.basemodule.R;
import com.tanker.basemodule.common.TankerApp;
import com.tanker.basemodule.model.PictureInfo;


public class WaterMarkUtils {


    /**
     * 绘制文字到左下方
     *
     * @param bitmap        原图
     * @param text
     * @param size          文字大小
     * @param color         文字颜色
     * @param paddingLeft
     * @param paddingBottom
     * @return
     */
    public static Bitmap drawTextToLeftBottom(Bitmap bitmap, String text,
                                              int size, int color, int paddingLeft, int paddingBottom) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(DensityUtils.dip2px(size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(bitmap, text, paint, bounds,
                DensityUtils.dip2px(paddingLeft),
                bitmap.getHeight() - DensityUtils.dip2px(paddingBottom));
    }


    /**
     * 绘制文字到左下方
     *
     * @param bitmap 原图
     * @return
     */
    public static Bitmap drawWatermark(Bitmap bitmap, PictureInfo pictureInfo, String bookingNum) {
        String time = pictureInfo.getPicTime();
        String data = pictureInfo.getPicDate() + " " + pictureInfo.getPicWeek();
        String address = pictureInfo.getPicAddress();

        BitmapDrawable bitmapDrawable = (BitmapDrawable) TankerApp.getInstance().getResources().getDrawable(R.drawable.watermark_logo);
        Bitmap zoomImage = zoomImage(bitmapDrawable.getBitmap(), DensityUtils.dip2px(71), DensityUtils.dip2px(32));
        Bitmap waterMaskLeftBottom = WaterMarkUtils.createWaterMaskLeftBottom(bitmap, zoomImage, 11, 12);
        if (!TextUtils.isEmpty(address)) {
            bitmapDrawable = (BitmapDrawable) TankerApp.getInstance().getResources().getDrawable(R.drawable.watermark_address);
            zoomImage = zoomImage(bitmapDrawable.getBitmap(), DensityUtils.dip2px(8), DensityUtils.dip2px(10));
            waterMaskLeftBottom = WaterMarkUtils.createWaterMaskLeftBottom(waterMaskLeftBottom, zoomImage, 14, 64);
        }

        Paint paintTime = initPaint(time, 18);
        paintTime.setFakeBoldText(true);
        Paint paintData = initPaint(data, 10);
        Paint paintAddress = initPaint(address, 10);
        Paint paintBookingNum = initPaint(bookingNum, 10);

        Bitmap.Config bitmapConfig = bitmap.getConfig();
        if (bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888;
        }
        waterMaskLeftBottom = waterMaskLeftBottom.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(waterMaskLeftBottom);

        canvas.drawText(time, DensityUtils.dip2px(12), bitmap.getHeight() - DensityUtils.dip2px(80), paintTime);
        canvas.drawText(data, DensityUtils.dip2px(70), bitmap.getHeight() - DensityUtils.dip2px(80), paintAddress);
        canvas.drawText(address, DensityUtils.dip2px(24), bitmap.getHeight() - DensityUtils.dip2px(64), paintData);
        canvas.drawText(bookingNum, DensityUtils.dip2px(12), bitmap.getHeight() - DensityUtils.dip2px(48), paintBookingNum);
        return waterMaskLeftBottom;
    }

    private static Paint initPaint(String text, float textSize) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Rect bounds = new Rect();
        int textColor = TankerApp.getInstance().getResources().getColor(R.color.white);
        paint.setDither(true);
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        paint.getTextBounds(text, 0, text.length(), bounds);
        paint.setColor(textColor);
        paint.setTextSize(DensityUtils.sp2px(textSize));
//        paint.setTypeface(Typeface.createFromAsset(TankerApp.getInstance().getAssets(),""));
        return paint;
    }


    /**
     * 设置水印图片到左下角
     *
     * @param src
     * @param watermark
     * @param paddingLeft
     * @param paddingBottom
     * @return
     */
    public static Bitmap createWaterMaskLeftBottom(Bitmap src, Bitmap watermark,
                                                   int paddingLeft, int paddingBottom) {

        return createWaterMaskBitmap(src, watermark, DensityUtils.dip2px(paddingLeft),
                src.getHeight() - watermark.getHeight() - DensityUtils.dip2px(paddingBottom));
    }

    /**
     * 设置水印图片到中间
     *
     * @param src
     * @param watermark
     * @return
     */
    public static Bitmap createWaterMaskCenter(Bitmap src, Bitmap watermark) {
        return createWaterMaskBitmap(src, watermark,
                (src.getWidth() - watermark.getWidth()) / 2,
                (src.getHeight() - watermark.getHeight()) / 2);
    }

    /**
     * 绘制文字到中间
     *
     * @param bitmap
     * @param text
     * @param size
     * @param color
     * @return
     */
    public static Bitmap drawTextToCenter(Bitmap bitmap, String text,
                                          int size, int color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(DensityUtils.dip2px(size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(bitmap, text, paint, bounds,
                (bitmap.getWidth() - bounds.width()) / 2,
                (bitmap.getHeight() + bounds.height()) / 2);
    }


    //图片上绘制文字
    private static Bitmap drawTextToBitmap(Bitmap bitmap, String text,
                                           Paint paint, Rect bounds, int paddingLeft, int paddingTop) {
        android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();

        paint.setDither(true); // 获取跟清晰的图像采样
        paint.setFilterBitmap(true);// 过滤一些
        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);

        canvas.drawText(text, paddingLeft, paddingTop, paint);
        return bitmap;
    }

    //图片上绘制图片水印
    private static Bitmap createWaterMaskBitmap(Bitmap src, Bitmap watermark, int paddingLeft, int paddingTop) {
        if (src == null) {
            return null;
        }
        int width = src.getWidth();
        int height = src.getHeight();
        //创建一个bitmap
        Bitmap newb = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        //将该图片作为画布
        Canvas canvas = new Canvas(newb);
        //在画布 0，0坐标上开始绘制原始图片
        canvas.drawBitmap(src, 0, 0, null);
        //在画布上绘制水印图片
        canvas.drawBitmap(watermark, paddingLeft, paddingTop, null);
        // 保存
        canvas.save(Canvas.ALL_SAVE_FLAG);
        // 存储
        canvas.restore();
        if (watermark != null) {
            watermark.recycle();
        }
        return newb;
    }


    /***
     * 图片压缩
     *
     * @param bgimage
     *            ：源图片资源
     * @param newWidth
     *            ：缩放后宽度
     * @param newHeight
     *            ：缩放后高度
     * @return
     */
    private static Bitmap zoomImage(Bitmap bgimage, double newWidth, double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

}
