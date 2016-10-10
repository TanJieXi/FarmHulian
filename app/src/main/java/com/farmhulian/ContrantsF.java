package com.farmhulian;

/**
 * 保存常量的地方
 *  @author 谭杰栖
 */
public class ContrantsF {
    /**
     * 用来判断是否是第一次安装程序，用来判断是跳转到引导页面的一个名字
     */
    public static final String isFirst = "isFirst";
    /**
     * 用来判断生态粮油，生态鲜果，绿色蔬菜，水产海鲜，散养牧场，天然野生，名优特产区别的一个名字,因为是同一个布局
     */
    public static final String HOME_SEVEN_MARK = "mark";
    /**
     * 用来判断Tablayout里面的是哪个界面  因为复用的
     */
    public static final String OVI_TABLAYOUT_WHICH = "tablayout_which";
    /**
     * 用来判断门店详情下面的RadioButton点击的是哪个
     */
    public static final String STORE_DETALS_WHICH_TABS = "STORE_DETALS_WHICH_TABS";
    /**
     * 用一个String来保存选择地址信息
     */
    public static final String CHOICE_WHICH_ADDRESS = "choice_which_address";

    /**
     * 用一个String来保存选择地址信息
     */
    public static final String CHOICE_WHICH_ADDRESS_NAME = "choice_which_address_name";
    /**
     * 用一个String来保存选择地址信息
     */
    public static final String CHOICE_WHICH_ADDRESS_PHONE = "choice_which_address_phone";


    /**
     * 用一个String来保存是地址界面还是物品信息点进去地址的信息，一个要返回一个纯展示
     */
    public static final String CHOICE_WHICH_NOW_PAY_ADDRESS_OR_ADDRESS_MANAGER = "choice_which_now_pay_address_or_address_manager";
    /**
     * 购买里面的选择地址
     */
    public static final String CHOICE_ADDRESS_PART = "购买里面的选择地址";
    /**
     * 碎片里面的地址管理
     */
    public static final String ADDRESS_MANAGER_PART = "碎片里面的地址管理";
    /**
     * 全部订单里面区别
     */
    public static final String ORDERS_WHICH_TAB_S = "orders_which_tabs_s";

    /**
     * Kind里面右边里面的rightMark的字，传过去，要判断切换到OilActivity里面的哪个tab导航
     */
    public static final String KIND_RIGHT_TEXT_PART = "kind_right_text_part";
    /**
     * 写一个用来跳转选择地址的
     */
    public static final int CHOICE_ADDRESS = 105;
    /**
     * 确定订单里面的地址
     */
    public static final int CHOICE_SURE_ADDRESS = 106;

    /**
     * 密码的最长长度
     */
    public static final int PASSWORD_MAX_LEN = 16;

    /**
     * 密码的最低长度
     */
    public static final int PASSWORD_MIN_LEN = 6;

    /**
     * ViewPager的预缓存个数
     */
    public static final int VIEWPAGER_LIMIT_NUM = 7;
    /**
     * 评价里面的服务态度，到货速度，商品满意度的一个类型区分
     */
    public static final int EVA_TYPE_FUWU = 1;
    public static final int EVA_TYPE_DAOHUO = 2;
    public static final int EVA_TYPE_MANYI = 3;




}
