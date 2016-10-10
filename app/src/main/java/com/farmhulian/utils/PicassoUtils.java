package com.farmhulian.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * 一个简单的Picasso工具类
 */
public class PicassoUtils {
    /**
     * 加载网络图片
     *
     * @param context   上下文
     * @param imgUrl    图片网址
     * @param imageView 控件
     */
    public static void loadImg(Context context, String imgUrl, ImageView imageView) {
        Picasso.with(context).load(imgUrl).into(imageView);
    }

    public static void loadImgRGB(Context context, String imgUrl, ImageView imageView) {
        Picasso.with(context).load(imgUrl).config(Bitmap.Config.RGB_565).into(imageView);
    }


    /**
     * 加载网络图片，如果错误，显示另外一张
     *
     * @param context    上下文
     * @param imgUrl     图片网址
     * @param errorImgId 下载失败的图片
     * @param imageView  控件
     */
    public static void loadImgWithError(Context context, String imgUrl, int errorImgId, ImageView imageView) {
        Picasso.with(context).load(imgUrl).error(errorImgId).into(imageView);
    }

    public static void loadImgWithErrorRGB(Context context, String imgUrl, int errorImgId, ImageView imageView) {
        Picasso.with(context).load(imgUrl).error(errorImgId).config(Bitmap.Config.RGB_565).into(imageView);
    }

    /**
     * 加载带有默认图片网络图片
     *
     * @param context   上下文
     * @param imgUrl    图片网址
     * @param plImgId   默认图片
     * @param imageView 控件
     */
    public static void loadImgWithPl(Context context, String imgUrl, int plImgId, ImageView imageView) {
        Picasso.with(context).load(imgUrl).placeholder(plImgId).into(imageView);
    }

    public static void loadImgWithPlRGB(Context context, String imgUrl, int plImgId, ImageView imageView) {
        Picasso.with(context).load(imgUrl).placeholder(plImgId).config(Bitmap.Config.RGB_565).into(imageView);
    }

    /**
     * 加载带有默认图片网络图片,并且包含加载错误的图片
     *
     * @param context    上下文
     * @param imgUrl     图片网址
     * @param plImgId    默认图片
     * @param errorImgId 加载错误的图片
     * @param imageView  控件
     */
    public static void loadImgWithPl(Context context, String imgUrl, int plImgId, int errorImgId, ImageView imageView) {
        Picasso.with(context).load(imgUrl).placeholder(plImgId).error(errorImgId).into(imageView);
    }

    public static void loadImgWithPlRGB(Context context, String imgUrl, int plImgId, int errorImgId, ImageView imageView) {
        Picasso.with(context).load(imgUrl).placeholder(plImgId).config(Bitmap.Config.RGB_565).error(errorImgId).into(imageView);
    }

    /**
     * 指定大小加载图片
     *
     * @param mContext   上下文
     * @param path       图片路径
     * @param width      宽
     * @param height     高
     * @param mImageView 控件
     */
    public static void loadImageViewSize(Context mContext, String path, int width, int height, ImageView mImageView) {
        Picasso.with(mContext).load(path).resize(width, height).centerCrop().into(mImageView);
    }


    /**
     * 加载圆形图片
     *
     * @param context
     * @param pictureUrl
     * @param imageView
     */
    public static void loadCircleImage(Context context, String pictureUrl, ImageView imageView) {
        Picasso.with(context).load(pictureUrl).transform(new CircleImageTransformation()).config(Bitmap.Config.RGB_565).into(imageView);
    }


    public static class CircleImageTransformation implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);//定义一个渲染器
            paint.setShader(shader);//设置渲染器
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);//绘制图形

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }

}
