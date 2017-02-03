package com.ishuyin.gecollsn.accueilBlock.fragments;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ishuyin.gecollsn.R;
import com.ishuyin.gecollsn.db.BookInfo;
import com.ishuyin.gecollsn.accueilBlock.model.AccueilModel;
import com.ishuyin.gecollsn.base.BaseFragment;
import com.ishuyin.gecollsn.base.EasyAdapter;
import com.ishuyin.gecollsn.base.ViewHolder;
import com.ishuyin.gecollsn.utils.DateUtil;
import com.ishuyin.gecollsn.utils.spUtil.SPMain;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author gecollsn
 * @create 5/16/2016
 * @company www.ishuyin.com
 */
public class SubscribeFragment extends BaseFragment {
    private final int DOWNLOAD = 0;
    private final int FAVORITE = 1;
    private final int LISTEN = 2;

    @BindView(R.id.main_personal_listView)
    ListView mListView;
    @BindView(R.id.inc_tab_coin)
    View download;
    @BindView(R.id.inc_tab_point)
    View favorite;
    @BindView(R.id.inc_tab_balance)
    View listen;
    @BindView(R.id.iv_user_logo)
    ImageView iv_user_logo;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_user_locate)
    TextView tv_user_locate;
    private TextView tv_recent_listen;
    private TextView tv_recent_download;
    private TextView tv_recent_favorite;
    private EasyAdapter<BookInfo> mAdapter;

    @Override
    protected int definedLayoutId() {
        return R.layout.fragment_central_subcribe;
    }

    @Override
    protected void doInitEverything() {
        doInitData();
        doInitViewsFromLayout();
        doInitDefaultEvent();
    }

    @Override
    public void doInitData() {
        if (!SPMain.isVirtualWritten()) {
            SPMain.setVirtualWritten(true);
            for (int i = 0; i < 10; i++) {
                BookInfo bookInfo = new BookInfo();
                bookInfo.setTitle("书籍" + i);
                bookInfo.setBookPlayer("GC" + i);
                bookInfo.setLatestUpdate(DateUtil.addDays(DateUtil.DATE_CLINE, i));
                AccueilModel.addDownloadBook(bookInfo);
                if (i < 8) AccueilModel.addFavoriteBook(bookInfo);
                if (i < 4) AccueilModel.addRecentBook(bookInfo);
            }
        }
    }

    @Override
    public void doInitListener() {

    }

    public void doInitDefaultEvent() {
        tv_user_name.setText("游客");
        tv_user_locate.setText("未知");
        tv_recent_listen.setText("最近收听");
        tv_recent_download.setText("我的下载");
        tv_recent_favorite.setText("我的收藏");
        mAdapter = new EasyAdapter<BookInfo>(R.layout.adapter_main_subscribe) {
            public void convert(ViewHolder vh, BookInfo bookInfo) {
                vh.getTextView(R.id.tv_bookName).setText(bookInfo.getTitle());
                vh.getTextView(R.id.tv_playerName).setText(mActivity.getString(R.string.voicePlayer) + bookInfo
                        .getBookPlayer());
                vh.getTextView(R.id.tv_latestUpdate).setText(mActivity.getString(R.string.lastedUpdate) + DateUtil
                        .date());
                if (vh.getCurrentPoz() == getCount() - 1) {
                    vh.getView(R.id.v_divider).setVisibility(View.INVISIBLE);
                } else {
                    vh.getView(R.id.v_divider).setVisibility(View.VISIBLE);
                }

            }

            @Override
            protected void initConvertView(final ViewHolder vh) {
                super.initConvertView(vh);
                vh.getView(R.id.tv_delete_bookInfo).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeData(vh.getCurrentPoz());
                    }
                });
            }
        };
        mListView.setAdapter(mAdapter);

        toggleTags(LISTEN);
    }

    public void doInitViewsFromLayout() {
        tv_recent_listen = (TextView) ((ViewGroup) listen).getChildAt(1);
        tv_recent_download = (TextView) ((ViewGroup) download).getChildAt(1);
        tv_recent_favorite = (TextView) ((ViewGroup) favorite).getChildAt(1);
    }

    private void toggleTags(int flag) {
        listen.setSelected(false);
        download.setSelected(false);
        favorite.setSelected(false);
        if (flag == DOWNLOAD) {
            download.setSelected(true);
            mAdapter.updateListView(AccueilModel.getDownloadBooks());
        } else if (flag == FAVORITE) {
            favorite.setSelected(true);
            mAdapter.updateListView(AccueilModel.getFavoriteBooks());
        } else if (flag == LISTEN) {
            listen.setSelected(true);
            mAdapter.updateListView(AccueilModel.getRecentBooks());
        }
    }

    @OnClick(R.id.inc_tab_coin)
    void onDownloadClicked() {
        toggleTags(DOWNLOAD);
    }

    @OnClick(R.id.inc_tab_point)
    void onFavoriteClicked() {
        toggleTags(FAVORITE);
    }

    @OnClick(R.id.inc_tab_balance)
    void onListenClicked() {
        toggleTags(LISTEN);
    }
}
