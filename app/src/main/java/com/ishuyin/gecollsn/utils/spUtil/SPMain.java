package com.ishuyin.gecollsn.utils.spUtil;


/**
 * Created by gecollsn on 2016/5/17 15:35.
 * <p/>
 * Company: zjp-company
 * Email: cs@zhijiepay.com
 * TEL: 0592-5101911
 * <p/>
 * Declare: SPMain
 */
public class SPMain extends SPFather {
    public static final String FILE = "SpMainFile";
    public static final String KEY_VIRTUAL_DATA = "BOOK_DATA";

    /*-----------------------             请叫我割割          ----------------------------------------*/
    public static boolean isVirtualWritten() {
        return SPUtil.get(FILE, KEY_VIRTUAL_DATA, false);
    }


    /** 防止变态的AS自动缩进 */
/*-----------------------------------------------------------------------------------------------*/
/*-----------------------                                ----------------------------------------*/
/*-----------------------       割出一片新天地！            ----------------------------------------*/
/*-----------------------                                ----------------------------------------*/
/*-----------------------                                ----------------------------------------*/
/*-----------------------                                ----------------------------------------*/
/*-----------------------                                ----------------------------------------*/
/*-----------------------------------------------------------------------------------------------*/
    public static void setVirtualWritten(boolean isWritten) {
        SPUtil.put(FILE, KEY_VIRTUAL_DATA, isWritten);
    }
}
