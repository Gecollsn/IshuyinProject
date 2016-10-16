package com.ishuyin.gecollsn;


import com.ishuyin.gecollsn.utils.SDCardUtil;

/**
 * Created by gecollsn on 2016/3/28 16:51.
 * <p/>
 * Company: zjp-company
 * Email: cs@zhijiepay.com
 * TEL: 0592-5101911
 * <p/>
 * Declare: 全局常量类
 */
public final class GBC/*该类为全局常量<Global Constant缩写>*/ {

    public static final class sp {
        public static final String FILE_MAIN = "UniteSharePreferMap";
    }

    public static final class file {
        public static final String SDCARD_COMMON_PATH = SDCardUtil.getSDCardPath() + "iShuYin/";
        public static final String DIRECT_CRASH_LOG = SDCARD_COMMON_PATH + "log/crash/";
        public static final String DIRECT_COMMON_LOG = SDCARD_COMMON_PATH + "log/common/";
        public static final String DIRECT_BITMAP_STORE = SDCARD_COMMON_PATH + "picture/";
        public static final String DIRECT_APK_STORE = SDCARD_COMMON_PATH + "download/";
    }

    public static final class packet {
        public static final String MAIN_APP = "com.zhijiepay.android";
        public static final String DESKTOP_APP = "com.zhijiepay.launcher";
    }

    public static final class handle {

    }

    public static final class layout {

    }

    public static final class code {

    }

    public static final class state {
        public static final int PRINTER_RESET = 0;
        public static final int PRINTER_FONT_NORMAL = 1;
        public static final int PRINTER_FONT_BIG = 2;
        public static final int PRINTER_ALIGN_CENTER = 3;
        public static final int PRINTER_ALIGN_LEFT = 4;
    }

    public static final class url {
        public static final String COMPANY_OFFICIAL_URL = "http://www.shouyinban.com";
        public static final String STORE_PREFIX_URL = "http://wx.zhijiefu.com/shopView/";
        public static final String QRCODE_PREFIX_URL = "http://wx.zhijiefu.com/#shop_view/";
        public static final String PICTURE_PREFIX_URL = "http://cdn01.zhijiepay.com/upload/fq_act/";
        public static final String LOGO_PREFIX_URL = "http://static.zhijiepay.com/upload/logo/";
        public static final String LUCKY_MONEY_PREFIX_URL = "http://wx.zhijiefu.com/redPacket/createLink/";
    }

    public static final class type {
        public static final int DEVICE_DEFAULT = 0;
        public static final int DEVICE_ST = 1;
        public static final int DEVICE_DJ = 2;
        public static final int DEVICE_DJ_AIO = 3;
        public static final int DEVICE_HDX = 4;
    }

    public static final class date {

    }
}
