package com.farmhulian.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.badoo.mobile.util.WeakHandler;
import com.farmhulian.R;
import com.squareup.picasso.Picasso;


/**
 * 一个ViewPager和RadioButton的合成控件
 *  @author 谭杰栖
 */
public class Viewpager_RadioButton_View extends RelativeLayout {

    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private OnItemClickListener listener;  //自己写的一个回调点击事件的


    private String[] str, showWebUrl;   //一个viewPager需要绑定适配器的数据
    private int[] reStr;
    private ViewPagerAdapter adapter; //ViewPager的一个PgaerAdapter适配器
    private ViewPagerAdapterResource rAdapter;  //一个传本地文件的适配器
    private boolean isWebImagePath = false;  //判断是否是传的网址过来

    public Viewpager_RadioButton_View(Context context) {
        super(context);
        init(context, null, 0);
    }

    public Viewpager_RadioButton_View(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public Viewpager_RadioButton_View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    /**
     * 初始化函数 这里只需要初始化一个viewPager出来
     *
     * @param context      上下文
     * @param attrs
     * @param defStyleAttr
     */
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        viewPager = addViewPager(context);

        //把这个viewPager添加到这个自定义布局上面
        addView(viewPager);

        // 要进行联动，所以这里要给viewPager添加一个监听器
        ViewPagerAndRadioGroupListener listener = new ViewPagerAndRadioGroupListener();
        viewPager.addOnPageChangeListener(listener);

    }

    /**
     * 写一个类来实现viewPgaer和RatioButton的监听事件，
     * 作用是完成，RadioButton和ViewPager的联动
     */
    class ViewPagerAndRadioGroupListener extends ViewPager.SimpleOnPageChangeListener implements RadioGroup.OnCheckedChangeListener {

        /////////////////////////// RadioGroup的监听///////////////////////
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            for (int i = 0, len = radioGroup.getChildCount(); i < len; i++) {
                radioButton = (RadioButton) radioGroup.getChildAt(i);
                if (radioButton.isChecked()) {
                    //viewPager.setCurrentItem(i,true);
                }
            }
        }

        /////////////////ViewPager的监听事件//////////////////
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            //Log.i("info","========onPageSelected===============" + position);
            //这个方法是通过传网址第一种方式传过来的
            if (str != null && radioGroup.getChildCount() != 0) {
                radioButton = (RadioButton) radioGroup.getChildAt(position % str.length);
                radioButton.setChecked(true);
                //Toast.makeText(getContext(), position % str.length+"", Toast.LENGTH_SHORT).show();
            }
            //这个方法是通过传本地文件的id传过来的
            if (reStr != null && radioGroup.getChildCount() != 0) {
                radioButton = (RadioButton) radioGroup.getChildAt(position % reStr.length);
                radioButton.setChecked(true);
                //Toast.makeText(getContext(), position % str.length+"", Toast.LENGTH_SHORT).show();
            }
        }
    }


    /**
     * 初始化RadioButton
     *
     * @param context 上下文
     * @return 写好的一个radioGroup控件
     */
    private RadioGroup addRadioGroup(Context context) {
        //再来一个RadioGroup
        RadioGroup radioGroup = new RadioGroup(context);
        LayoutParams rgParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //设置位置，在viewPager的下中位置
        rgParams.addRule(ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        rgParams.addRule(CENTER_HORIZONTAL, RelativeLayout.TRUE);
        radioGroup.setLayoutParams(rgParams);

        //设置一下radioGroup的子控件排列顺序
        radioGroup.setOrientation(LinearLayout.HORIZONTAL);

        //在来三个RadioButton
        RadioButton rb = null;
        RadioGroup.LayoutParams rbParams;

        //这里就是来。添加radioButton
        if (str != null) {
            for (int i = 0; i < str.length; i++) {
                rb = new RadioButton(context);

                //添加一个自定义的背景
                rb.setButtonDrawable(R.drawable.selector_radiobutton_fill_empty);
                //间隔太近了
                rbParams = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                rbParams.setMargins(8, 5, 8, 8);
                rb.setLayoutParams(rbParams);

                radioGroup.addView(rb);
            }
        }

        if (reStr != null) {
            for (int i = 0; i < reStr.length; i++) {
                rb = new RadioButton(context);

                //添加一个自定义的背景
                rb.setButtonDrawable(R.drawable.selector_radiobutton_fill_empty);
                //间隔太近了
                rbParams = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                rbParams.setMargins(5, 5, 5, 5);
                rb.setLayoutParams(rbParams);

                radioGroup.addView(rb);
            }
        }
        return radioGroup;

    }

    /**
     * 初始化一个ViewPager
     *
     * @param context 上下文
     * @return 写好的一个ViewPager控件
     */
    @NonNull
    private ViewPager addViewPager(Context context) {
        //首先先来一个ViewPager控件
        ViewPager viewPager = new ViewPager(context);

        //设置一下位置
        LayoutParams vParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        viewPager.setLayoutParams(vParams);
        //加进布局

        return viewPager;
    }

    /**
     * 设置Banner条的图片
     *
     * @param context    一个上下文
     * @param imagePaths 需要展示的图片网址
     */
    public void setViewPagerImages(Context context, String[] imagePaths) {
        setViewPagerImages(context, imagePaths, null,null);
    }

    /**
     * 设置Banner条，注意这种方式的，必须设置点击事件，否则报错
     * @param context  上下文
     * @param imagePaths   图片网址
     * @param showWebUrls  跳转网页网址
     */
    public void setViewPagerImages(Context context, String[] imagePaths,String[] showWebUrls) {
        setViewPagerImages(context, imagePaths, showWebUrls, this.listener);
    }

    /**
     * 写一个方法传数据过来
     *
     * @param imagePaths  一个地址的byte数组
     * @param context     上下文
     * @param showWebUrls 需要展示的网址
     * @param fetchListener 一个结果的回调
     */
    public void setViewPagerImages(Context context, String[] imagePaths, String[] showWebUrls,OnItemClickListener fetchListener) {
        this.listener = fetchListener;
        isWebImagePath = true;
        if (showWebUrls != null) {
            showWebUrl = showWebUrls;
        }
        if (imagePaths != null) {
            str = imagePaths;
            //绑定适配器，必须在这里绑定viewPager的适配器，因为在这里数据才过来
            adapter = new ViewPagerAdapter(context, str);
            viewPager.setAdapter(adapter);


            //必须在这里来获得一个RadioGroup，因为RadioButton的数量，是根据传过来的数据字符数组的长度而决定的
            radioGroup = addRadioGroup(context);
            //把这个radioGroup放在自定义的这个RelativeLayout上面
            addView(radioGroup);

            if (radioGroup.getChildCount() != 0) {
                ((RadioButton) (radioGroup.getChildAt(0))).setChecked(true);
            }

            //同样的。要实现联动，就需要设置一个监听器
            ViewPagerAndRadioGroupListener listener = new ViewPagerAndRadioGroupListener();
            radioGroup.setOnCheckedChangeListener(listener);
            viewPager.setCurrentItem(imagePaths.length * 500 - 1);

            //发送
            handler.post(runnable);

        }
    }

    /**
     * 一个可以传本地文件的方法，但是不带跳转的地址
     *
     * @param context    this
     * @param imagePaths 一个本地的图片网址
     */
    public void setViewPagerImagesResource(Context context, int[] imagePaths) {
        setViewPagerImagesResource(context, imagePaths, null,null);
    }

    /**
     * 传本地图片过来，注意这种情况下，必须写点击事件
     * @param context  上下文
     * @param imagePaths  图片网址
     * @param showWebUrls  这是一个需要跳转的网址
     */
    public void setViewPagerImagesResource(Context context, int[] imagePaths,String[] showWebUrls) {
        setViewPagerImagesResource(context, imagePaths, showWebUrls,this.listener);
    }

    /**
     * 一个可以传本地文件的方法
     *
     * @param context     上下文
     * @param imagePaths  本地文件
     * @param showWebUrls 一个跳转网址
     * @param fetchListener 一个点击事件的回调接口
     */
    public void setViewPagerImagesResource(Context context, int[] imagePaths, String[] showWebUrls,OnItemClickListener fetchListener) {
        this.listener = fetchListener;
        isWebImagePath = false;
        if (showWebUrls != null) {
            showWebUrl = showWebUrls;
        }
        if (imagePaths != null) {
            reStr = imagePaths;
            //绑定适配器，必须在这里绑定viewPager的适配器，因为在这里数据才过来
            rAdapter = new ViewPagerAdapterResource(context, reStr);
            viewPager.setAdapter(rAdapter);


            //必须在这里来获得一个RadioGroup，因为RadioButton的数量，是根据传过来的数据字符数组的长度而决定的
            radioGroup = addRadioGroup(context);
            //把这个radioGroup放在自定义的这个RelativeLayout上面
            addView(radioGroup);

            if (radioGroup.getChildCount() != 0) {
                ((RadioButton) (radioGroup.getChildAt(0))).setChecked(true);
            }

            //同样的。要实现联动，就需要设置一个监听器
            ViewPagerAndRadioGroupListener listener = new ViewPagerAndRadioGroupListener();
            radioGroup.setOnCheckedChangeListener(listener);
            viewPager.setCurrentItem(imagePaths.length * 500 - 1);

            //发送
            handler.post(runnable);

        }
    }

    /**
     * 停止线程
     */
    public void removeHandler(){
        if(handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
            ((RadioButton) (radioGroup.getChildAt(viewPager.getCurrentItem() % str.length))).setChecked(false);
        }
    }


    /**
     * ViewPager的一个适配器
     */
    class ViewPagerAdapterResource extends PagerAdapter {
        private Context context;
        private int[] needStr;

        public ViewPagerAdapterResource(Context context, int[] needStr) {
            this.context = context;
            this.needStr = needStr;
        }

        @Override
        public int getCount() {
            return needStr.length * 1000;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView myImagerView = new ImageView(context);
            myImagerView.setScaleType(ImageView.ScaleType.FIT_XY);
            myImagerView.setTag(needStr[position % needStr.length]);
            //每张图片的点击事件
            myImagerView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (showWebUrl == null) {
                        Log.i("info", "===你没有传一个跳转网址语句过来====");
                    } else {
                        //Log.i("info", "===你必须添加点击事件，否则会报错====");
                        listener.onItemClick(position % needStr.length, showWebUrl[position % needStr.length]);
                    }

                }
            });

            if (needStr != null) {
                //这里设置一下图片
                Picasso.with(context).load(needStr[position % needStr.length]).config(Bitmap.Config.RGB_565).into(myImagerView);
            }
            container.addView(myImagerView);
            return myImagerView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ImageView) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }


    /**
     * ViewPager的一个适配器
     */
    class ViewPagerAdapter extends PagerAdapter {
        private Context context;
        private String[] needStr;

        public ViewPagerAdapter(Context context, String[] needStr) {
            this.context = context;
            this.needStr = needStr;
        }

        @Override
        public int getCount() {
            return needStr.length * 1000;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView myImagerView = new ImageView(context);
            myImagerView.setScaleType(ImageView.ScaleType.FIT_XY);
            myImagerView.setTag(needStr[position % needStr.length]);
            //每张图片的点击事件
            myImagerView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (showWebUrl == null) {
                         Log.i("info", "===你没有传一个跳转网址语句过来====");
                    } else {
                        //Toast.makeText(getContext(), "你需要添加点击事件", Toast.LENGTH_SHORT).show();
                        listener.onItemClick(position % needStr.length, showWebUrl[position % needStr.length]);
                    }

                }
            });

            if (needStr != null) {
                //这里设置一下图片
               Picasso.with(context).load(needStr[position % needStr.length]).config(Bitmap.Config.RGB_565).into(myImagerView);

            }
            container.addView(myImagerView);
            return myImagerView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ImageView) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    //自己滚动起来
    //Handler handler = new Handler();
    WeakHandler handler = new WeakHandler();
    int currentItemId;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (isWebImagePath) {
                //发送当前位置
                currentItemId = viewPager.getCurrentItem() + 1 % str.length;
            } else {
                currentItemId = viewPager.getCurrentItem() + 1 % reStr.length;
            }
            //设置一下
            viewPager.setCurrentItem(currentItemId);
            handler.postDelayed(runnable, 4000);

        }
    };

    //自己写一个点击事件的接口
    public interface OnItemClickListener {
        void onItemClick(int position, String webUrl);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
