package com.farmhulian.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Glide图片加载的简单用法
 */
public class SimpGlideUtils {
    /**
     * 加载图片
     * @param context
     * @param pictureUrl
     * @param imageView
     */
    public static void loadImage(Context context, String pictureUrl, ImageView imageView){
        Glide.with(context).load(pictureUrl).override(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL )
                .dontAnimate()
                .into(imageView);
    }

    /**
     * 设置加载图片带默认图片
     * @param context
     * @param pictureUrl
     * @param imageId       默认图片ID
     * @param imageView
     */
    public static void loadDefaultImage(Context context, String pictureUrl, int imageId, ImageView imageView){
        Glide.with(context).load(pictureUrl).override(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL )
                .placeholder(imageId)
                .dontAnimate()
                .into(imageView);
    }

    /**
     * 设置加载图片出错时候的默认图片
     * @param context
     * @param pictureUrl
     * @param imageId       图片出错时候的图片
     * @param imageView
     */
    public static void loadErrorImage(Context context, String pictureUrl, int imageId, ImageView imageView){
        Glide.with(context).load(pictureUrl)
                .error(imageId)
                .dontAnimate()
                .into(imageView);
    }

    /**
     * 加载缩略图
     * @param context
     * @param pictureUrl
     * @param imageView
     * @param size      单精度范围0-1之间（例：0.1f）
     */
    public static void loadAbbreviationsImage(Context context, String pictureUrl, ImageView imageView, float size){
        Glide.with(context).load(pictureUrl)
                .thumbnail(size)//用原型图的十分之一作为缩略图.
        .into(imageView);
    }

    /**
     * 加载缩略图，默认为原图形的十分之一
     * @param context
     * @param pictureUrl
     * @param imageView
     */
    public static void loadAbbreviationsImage(Context context, String pictureUrl, ImageView imageView){
        loadAbbreviationsImage(context,pictureUrl,imageView,0.1f);
    }

    /**
     * 加载圆形图片
     * @param context
     * @param pictureUrl
     * @param imageView
     */
    public static void loadCircularImage(Context context, String pictureUrl, ImageView imageView){
        Glide.with(context).load(pictureUrl).bitmapTransform(new CropCircleTransformation(context)).into(imageView);
    }

    /**
     * 对图片进行圆角处理
     * @param context
     * @param pictureUrl
     * @param imageView
     */

    public static void loadFilletImage(Context context, String pictureUrl, ImageView imageView){
        Glide.with(context).load(pictureUrl).override(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL )
                .bitmapTransform(new RoundedCornersTransformation(context,10,0, RoundedCornersTransformation.CornerType.TOP))//圆角处理
                .into(imageView);
    }

    /**
     * 灰度处理
     * @param context
     * @param pictureUrl
     * @param imageView
     */
    public static void loadGrayImage(Context context, String pictureUrl, ImageView imageView){
        Glide.with(context)
                .load(pictureUrl)
                .bitmapTransform(new GrayscaleTransformation(context))//灰度处理
                .into(imageView);
    }
}
