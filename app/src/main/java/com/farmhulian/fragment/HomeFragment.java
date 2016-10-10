package com.farmhulian.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.farmhulian.ContrantsF;
import com.farmhulian.R;
import com.farmhulian.activities.EvaluateActivity;
import com.farmhulian.activities.GoodDetailsActivity;
import com.farmhulian.activities.KindActivity;
import com.farmhulian.activities.OilActivity;
import com.farmhulian.activities.ResturantActivity;
import com.farmhulian.activities.StoreDetailsActivity;
import com.farmhulian.activities.TravelActivity;
import com.farmhulian.adapters.ChoiceGridAdapter;
import com.farmhulian.adapters.HomeNearSeaAdapter;
import com.farmhulian.base.BaseFragment;
import com.farmhulian.utils.RefreshUtils;
import com.farmhulian.utils.ToastUtils;
import com.farmhulian.views.NoScrollGridView;
import com.farmhulian.views.NoScrollListView;
import com.farmhulian.views.Viewpager_RadioButton_View;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.zbar.lib.CaptureActivity;
import com.zbar.lib.Contrants;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页碎片
 *
 * @author 谭杰栖
 */
public class HomeFragment extends BaseFragment {
    private View curView;
    private PullToRefreshScrollView mPullToScroll; //刷新的
    private ImageView home_title_person; //左边人头
    private AllListener listener = new AllListener(); //所有事件的监听器
    private TextView tv_title_search; //搜索
    private LinearLayout ll_title;
    private ImageView iv_title_erwei; //二维码
    private Viewpager_RadioButton_View vr_banner;   //Banner条
    private LinearLayout ll_btns1, ll_btns2;
    //水产，鲜果，蔬菜，海鲜，旅游，牧场，野生，天然，餐厅，分类
    private TextView tv_oil, tv_fruit, tv_vegetable, tv_sea, tv_travel, tv_field, tv_wild, tv_localfood, tv_rest, tv_kind;
    private boolean isFirstRefresh = true;
    private Handler h;
    //热销推荐，即将上市，生态溯源，附近农场，海外农场，农场体验
    private RelativeLayout rl_hot_more, rl_jijiang_more, rl_suyuan_more, rl_near_more, rl_sea_more, rl_tiyan_more;
    private NoScrollGridView home_tuijian_gridView;  //热销推荐下面的GridView
    private NoScrollGridView home_suyuan_gridView;   //生态溯源
    private NoScrollGridView home_shangshi_gridView;   //即将上市
    private ChoiceGridAdapter tGridAdapter, sGridAdapter, shGridAdapter;
    private List<String> datas = new ArrayList<>();
    private NoScrollListView home_haiwai_listView, home_fujin_listView;
    private HomeNearSeaAdapter homeNearAdapter, homeSeaAdapter;  //附近农场和海外农场的适配器
    private List<String> nearOrSeaDatas = new ArrayList<>();  //附近农场和海外农场的数据


    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        curView = inflater.inflate(R.layout.fragment_main_home, null);
        return curView;
    }

    @Override
    protected void initFindView() {
        mPullToScroll = (PullToRefreshScrollView) curView.findViewById(R.id.mPullToScroll);
        home_title_person = (ImageView) curView.findViewById(R.id.home_title_person);
        tv_title_search = (TextView) curView.findViewById(R.id.tv_title_search);
        iv_title_erwei = (ImageView) curView.findViewById(R.id.iv_title_erwei);
        ll_btns1 = (LinearLayout) curView.findViewById(R.id.ll_btns1);
        ll_btns2 = (LinearLayout) curView.findViewById(R.id.ll_btns2);
        tv_oil = (TextView) curView.findViewById(R.id.tv_oil);
        tv_fruit = (TextView) curView.findViewById(R.id.tv_fruit);
        tv_vegetable = (TextView) curView.findViewById(R.id.tv_vegetable);
        tv_sea = (TextView) curView.findViewById(R.id.tv_sea);
        tv_travel = (TextView) curView.findViewById(R.id.tv_travel);
        tv_field = (TextView) curView.findViewById(R.id.tv_field);
        tv_wild = (TextView) curView.findViewById(R.id.tv_wild);
        tv_localfood = (TextView) curView.findViewById(R.id.tv_localfood);
        tv_rest = (TextView) curView.findViewById(R.id.tv_rest);
        tv_kind = (TextView) curView.findViewById(R.id.tv_kind);
        vr_banner = (Viewpager_RadioButton_View) curView.findViewById(R.id.vr_banner);
        ll_title = (LinearLayout) curView.findViewById(R.id.ll_title);

        //热销推荐，即将上市，生态溯源，附近农场，海外农场,农场体验 更多的点击事件
        rl_hot_more = (RelativeLayout) curView.findViewById(R.id.rl_hot_more);
        rl_jijiang_more = (RelativeLayout) curView.findViewById(R.id.rl_jijiang_more);
        rl_suyuan_more = (RelativeLayout) curView.findViewById(R.id.rl_suyuan_more);
        rl_near_more = (RelativeLayout) curView.findViewById(R.id.rl_near_more);
        rl_sea_more = (RelativeLayout) curView.findViewById(R.id.rl_sea_more);
        rl_tiyan_more = (RelativeLayout) curView.findViewById(R.id.rl_tiyan_more);


        //热销推荐，即将上市，生态溯源 下面的GridView内容
        home_tuijian_gridView = (NoScrollGridView) curView.findViewById(R.id.home_tuijian_gridView);
        home_suyuan_gridView = (NoScrollGridView) curView.findViewById(R.id.home_suyuan_gridView);
        home_shangshi_gridView = (NoScrollGridView) curView.findViewById(R.id.home_shangshi_gridView);


        tGridAdapter = new ChoiceGridAdapter(getContext(), datas);
        sGridAdapter = new ChoiceGridAdapter(getContext(), datas);
        shGridAdapter = new ChoiceGridAdapter(getContext(), datas);

        home_tuijian_gridView.setAdapter(tGridAdapter);
        home_suyuan_gridView.setAdapter(sGridAdapter);
        home_shangshi_gridView.setAdapter(shGridAdapter);

        for (int i = 0; i < 2; i++) {
            datas.add("数据" + i);
        }
        tGridAdapter.notifyDataSetChanged();
        sGridAdapter.notifyDataSetChanged();
        shGridAdapter.notifyDataSetChanged();

        //附近市场和海外市场
        home_haiwai_listView = (NoScrollListView) curView.findViewById(R.id.home_haiwai_listView);
        home_fujin_listView = (NoScrollListView) curView.findViewById(R.id.home_fujin_listView);

        homeNearAdapter = new HomeNearSeaAdapter(getActivity(), nearOrSeaDatas);
        homeSeaAdapter = new HomeNearSeaAdapter(getActivity(), nearOrSeaDatas);

        home_haiwai_listView.setAdapter(homeSeaAdapter);
        home_fujin_listView.setAdapter(homeNearAdapter);
        for (int i = 0; i < 3; i++) {
            nearOrSeaDatas.add("数据" + i);
        }
        homeNearAdapter.notifyDataSetChanged();
        homeSeaAdapter.notifyDataSetChanged();
    }

    @Override
    protected void achieveProgress() {
        //设置监听器
        setAllListener();
        //设置一些Pull的参数
        setPullToRefresh();
        //模拟设置广告条

        setBanner();
    }

    /**
     * 模拟设置广告条
     */
    private void setBanner() {
        String[] imgsUrl = {"http://p3.so.qhmsg.com/t016ec2cd3a0c2312bd.jpg", "http://p3.so.qhmsg.com/t01723278fa3d63c048.jpg", "http://p2.so.qhmsg.com/t01ba3a9f0dd885778c.jpg"};
        int[] imgs = {R.mipmap.banner1, R.mipmap.banner2, R.mipmap.banner3};
        //vr_banner.setViewPagerImages(getActivity(), imgs, imgs);
        vr_banner.setViewPagerImagesResource(getActivity(), imgs, imgsUrl);
    }

    @Override
    public void onResume() {
        super.onResume();

        ll_title.setFocusable(true);
        ll_title.setFocusableInTouchMode(true);
        ll_title.requestFocus();
    }

    /**
     * 设置所有监听器
     */
    private void setAllListener() {
        vr_banner.setOnItemClickListener(listener);
        mPullToScroll.setOnRefreshListener(listener);
        home_title_person.setOnClickListener(listener);
        tv_title_search.setOnClickListener(listener);
        iv_title_erwei.setOnClickListener(listener);
        for (int i = 0, len = ll_btns1.getChildCount(); i < len; i++) {
            ll_btns1.getChildAt(i).setOnClickListener(listener);
            ll_btns2.getChildAt(i).setOnClickListener(listener);
        }

        //热销推荐，即将上市，生态溯源，附近农场，海外农场,农场体验 更多的点击事件
        rl_hot_more.setOnClickListener(listener);
        rl_jijiang_more.setOnClickListener(listener);
        rl_suyuan_more.setOnClickListener(listener);
        rl_near_more.setOnClickListener(listener);
        rl_sea_more.setOnClickListener(listener);
        rl_tiyan_more.setOnClickListener(listener);

        //几个下面listview和GridView的监听器
        home_tuijian_gridView.setOnItemClickListener(listener);  //热销推荐
        home_shangshi_gridView.setOnItemClickListener(listener); //即将上市
        home_suyuan_gridView.setOnItemClickListener(listener); //生态溯源
        home_fujin_listView.setOnItemClickListener(listener);  //附近农场
        home_haiwai_listView.setOnItemClickListener(listener); //海外农场


    }

    /**
     * 配置一些PullToRfresh的参数
     */
    private void setPullToRefresh() {
        mPullToScroll.setMode(PullToRefreshBase.Mode.BOTH);
        //设置一下刷新显示的字
        RefreshUtils.setPullReContent(mPullToScroll, "开始刷新", "刷新成功：", "下拉刷新", "释放刷新");
        RefreshUtils.setPullMoreContent(mPullToScroll, "开始加载", "加载成功：", "上拉加载", "释放加载");

    }

    /**
     * 所有事件的监听事件
     */
    class AllListener implements PullToRefreshBase.OnRefreshListener<ScrollView>, View.OnClickListener, Viewpager_RadioButton_View.OnItemClickListener, AdapterView.OnItemClickListener {
        ///////////////////////PullToRefresh的监听器////////////////////////////////////////
        @Override
        public void onRefresh(PullToRefreshBase refreshView) {
            //这里来判断一下是上拉还是加载
            if (mPullToScroll.isShownHeader()) {  //刷新

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                mPullToScroll.onRefreshComplete();

            } else if (mPullToScroll.isShownFooter()) { //加载
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                mPullToScroll.onRefreshComplete();

            }

        }

        ///////////////////////////点击事件///////其中七个是同一个界面//////////////////////////////
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.home_title_person: //标题左边人头
                    ToastUtils.showShort(getActivity(), "点击了人头");
                    break;
                case R.id.tv_title_search:  //搜索
                    ToastUtils.showShort(getActivity(), "点击了搜索");
                    startActivity(new Intent(getActivity(), EvaluateActivity.class));
                    break;
                case R.id.iv_title_erwei: //二维码
                    startActivityForResult(new Intent(getActivity(), CaptureActivity.class), Contrants.HomeActErWei);
                    break;
                case R.id.tv_oil:  //生态粮油
                    intent = new Intent(getActivity(), OilActivity.class);
                    intent.putExtra(ContrantsF.HOME_SEVEN_MARK, "生态粮油");
                    startActivity(intent);
                    break;
                case R.id.tv_fruit:  //生态鲜果
                    intent = new Intent(getActivity(), OilActivity.class);
                    intent.putExtra(ContrantsF.HOME_SEVEN_MARK, "生态鲜果");
                    startActivity(intent);
                    break;
                case R.id.tv_vegetable:  //绿色蔬菜
                    intent = new Intent(getActivity(), OilActivity.class);
                    intent.putExtra(ContrantsF.HOME_SEVEN_MARK, "绿色蔬菜");
                    startActivity(intent);
                    break;
                case R.id.tv_sea:  //水产海鲜
                    intent = new Intent(getActivity(), OilActivity.class);
                    intent.putExtra(ContrantsF.HOME_SEVEN_MARK, "水产海鲜");
                    startActivity(intent);
                    break;
                case R.id.tv_field:  //牧场
                    intent = new Intent(getActivity(), OilActivity.class);
                    intent.putExtra(ContrantsF.HOME_SEVEN_MARK, "散养牧场");
                    startActivity(intent);
                    break;
                case R.id.tv_wild:  //天然野生
                    intent = new Intent(getActivity(), OilActivity.class);
                    intent.putExtra(ContrantsF.HOME_SEVEN_MARK, "天然野生");
                    startActivity(intent);
                    break;
                case R.id.tv_localfood:  //名优特产
                    intent = new Intent(getActivity(), OilActivity.class);
                    intent.putExtra(ContrantsF.HOME_SEVEN_MARK, "名优特产");
                    startActivity(intent);
                    break;
                case R.id.tv_travel:  //生态旅游
                    startActivity(new Intent(getActivity(), TravelActivity.class));
                    break;
                case R.id.tv_rest:  //生态餐厅
                    startActivity(new Intent(getActivity(), ResturantActivity.class));
                    break;
                case R.id.tv_kind:  //分类
                    startActivity(new Intent(getActivity(), KindActivity.class));
                    break;
                //////////几个更多/////
                case R.id.rl_hot_more:  //热销推荐
                    ToastUtils.showShort(getActivity(), "热销推荐");
                    break;
                case R.id.rl_jijiang_more: //即将上市
                    ToastUtils.showShort(getActivity(), "即将上市");
                    break;
                case R.id.rl_suyuan_more:  //生态溯源
                    ToastUtils.showShort(getActivity(), "生态溯源");
                    break;
                case R.id.rl_near_more:  //附近农场
                    ToastUtils.showShort(getActivity(), "附近农场");
                    break;
                case R.id.rl_sea_more:  //海外农场
                    ToastUtils.showShort(getActivity(), "海外农场");
                    break;
                case R.id.rl_tiyan_more: //农场体验
                    ToastUtils.showShort(getActivity(), "农场体验");
                    break;


                default:
                    break;
            }
        }

        //////////////////////广告条的点击事件/////////////////////////////////
        @Override
        public void onItemClick(int position, String webUrl) {
            ToastUtils.showShort(getActivity(), "webUrl=" + webUrl + ",position=" + position);
        }


        /**
         * item的点击事件，这里要区分一下
         */
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (home_tuijian_gridView == parent) {//热销推荐
                ToastUtils.showShort(getActivity(), "热销的" + position);
                startActivity(new Intent(getActivity(), GoodDetailsActivity.class));
            } else if (home_shangshi_gridView == parent) {//即将上市
                ToastUtils.showShort(getActivity(), "即将上市的" + position);
                startActivity(new Intent(getActivity(), GoodDetailsActivity.class));
            } else if (home_suyuan_gridView == parent) {//生态溯源
                ToastUtils.showShort(getActivity(), "生态溯源的" + position);
                startActivity(new Intent(getActivity(), GoodDetailsActivity.class));
            } else if (home_fujin_listView == parent) {//附近农场
                ToastUtils.showShort(getActivity(), "附近农场的" + position);
                startActivity(new Intent(getActivity(), StoreDetailsActivity.class));
            } else if (home_haiwai_listView == parent) { //海外农场
                ToastUtils.showShort(getActivity(), "海外农场的" + position);
                startActivity(new Intent(getActivity(), StoreDetailsActivity.class));
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ////////////////二维码返回/////////////
        if (requestCode == Contrants.captureActErWei && getActivity().RESULT_OK == resultCode) {
            if (data != null) {
                ToastUtils.showShort(getActivity(), data.getStringExtra("eResult"));
            }
        }
    }

    /**
     * 结束的时候把Hadler移除
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        /*if(vr_banner != null) {
            vr_banner.removeHandler();
        }*/
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (mPullToScroll != null) {
                mPullToScroll.scrollBy(0, 0);
            }
        }
    }
}
