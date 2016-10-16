package com.ishuyin.gecollsn.accueilBlock.activitys;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.ishuyin.gecollsn.R;
import com.ishuyin.gecollsn.accueilBlock.AC;
import com.ishuyin.gecollsn.accueilBlock.adapters.HandpickMoreAdapter;
import com.ishuyin.gecollsn.accueilBlock.domain.BookInfo;
import com.ishuyin.gecollsn.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author gecollsn
 * @create 6/12/2016
 * @company www.ishuyin.com
 */
public class HandpickMoreActivity extends BaseActivity {
    @BindView(R.id.lv_handpick_more)  ListView mListView;
    @BindView(R.id.tab_selection1)  TextView tv_recommend;
    @BindView(R.id.tab_selection2)  TextView tv_hot;
    @BindView(R.id.tab_selection3)  TextView tv_new;
    @BindView(R.id.tab_selection4)  TextView tv_finished;
    @BindView(R.id.action_search_et)  TextView et_action_search;
    public final static String TAG_RECOMMENT = "recommend";
    public final static String TAG_HOT = "hot";
    public final static String TAG_NEW = "new";
    public final static String TAG_FINISHED = "finished";
    private HandpickMoreAdapter mAdapter;

    @Override
    protected int definedLayoutId() {
        return R.layout.activity_hanpick_more;
    }

    @Override
    protected void doInitEverything() {
        doInitViewsListener();
    }

    @Override
    public void doInitViewsFromLayout() {

    }

    @Override
    public void doInitData() {

    }

    private void doInitViewsListener() {

    }

    public void doInitDefaultEvent() {
        tv_recommend.setText("推荐");
        tv_hot.setText("热播");
        tv_new.setText("新书");
        tv_finished.setText("完结");
        tv_finished.setVisibility(View.VISIBLE);
        findViewById(R.id.action_title).setVisibility(View.GONE);
        findViewById(R.id.action_searchBar).setVisibility(View.VISIBLE);
        et_action_search.setHint("作者/播音/关键词");
        et_action_search.setFocusable(false);

        final String _TAG = getIntent().getStringExtra(AC.io.HANDPIC_MORE_TAG);
        if (_TAG.equals(TAG_RECOMMENT)) recommentSelected();
        else if (_TAG.equals(TAG_HOT)) hotSelected();
        else if (_TAG.equals(TAG_NEW)) newSelected();
        else if (_TAG.equals(TAG_FINISHED)) finishedSelected();

        mAdapter = new HandpickMoreAdapter(R.layout.adapter_handpick_more_lv);
        mListView.setAdapter(mAdapter);

        List<BookInfo> books = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            books.add(new BookInfo());
        }
        mAdapter.updateListView(books);
    }

    @OnClick(R.id.tab_selection1)
    void recommentSelected() {
        toggleTag(TAG_RECOMMENT);
    }

    @OnClick(R.id.tab_selection2)
    void hotSelected() {
        toggleTag(TAG_HOT);
    }

    @OnClick(R.id.tab_selection3)
    void newSelected() {
        toggleTag(TAG_NEW);
    }

    @OnClick(R.id.tab_selection4)
    void finishedSelected() {
        toggleTag(TAG_FINISHED);
    }

    public void toggleTag(String tag) {
        tv_recommend.setSelected(false);
        tv_hot.setSelected(false);
        tv_new.setSelected(false);
        tv_finished.setSelected(false);
        et_action_search.setSelected(false);
        if (tag.equals(TAG_RECOMMENT)) tv_recommend.setSelected(true);
        else if (tag.equals(TAG_HOT)) tv_hot.setSelected(true);
        else if (tag.equals(TAG_NEW)) tv_new.setSelected(true);
        else if (tag.equals(TAG_FINISHED))tv_finished.setSelected(true);
    }

    public void clickBack(View v) {
        this.finish();
    }

    @Override
    public void doInitListener() {

    }
}
