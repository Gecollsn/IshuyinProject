package com.ishuyin.gecollsn.accueilBlock.activitys;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.ishuyin.gecollsn.R;
import com.ishuyin.gecollsn.base.BaseActivity;
import com.ishuyin.gecollsn.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * @author gecollsn
 * @create 5/25/2016
 * @company www.ishuyin.com
 */
public class SortPageActivity extends BaseActivity {
    @BindView(R.id.gv_sort_part1)
    GridView gv_part1;
    @BindView(R.id.gv_sort_part2)
    GridView gv_part2;
    @BindView(R.id.gv_sort_part3)
    GridView gv_part3;
    @BindView(R.id.action_title)
    TextView title;

    @Override
    protected int definedLayoutId() {
        return R.layout.activity_sort_page;
    }

    @Override
    protected void doInitEverything() {
        doInitDefaultEvent();
    }

    @Override
    public void doInitViewsFromLayout() {

    }

    @Override
    public void doInitData() {

    }

    @OnClick(R.id.action_back)
    void turnFinish() {
        finish();
    }

    @OnItemClick(R.id.gv_sort_part1)
    void gvPart1(AdapterView<?> parent, View view, int position, long id) {
        ToastUtil.show("click" + position);
    }

    @OnItemClick(R.id.gv_sort_part2)
    void gvPart2(AdapterView<?> parent, View view, int position, long id) {

    }

    @OnItemClick(R.id.gv_sort_part3)
    void gvPart3(AdapterView<?> parent, View view, int position, long id) {

    }

    public void doInitDefaultEvent() {
        final String[] part1 = getResources().getStringArray(R.array.main_sort_part1);
        final String[] part2 = getResources().getStringArray(R.array.main_sort_part2);
        final String[] part3 = getResources().getStringArray(R.array.main_sort_part3);

        ArrayAdapter<String> mAdapter1 = new ArrayAdapter<String>(this, R.layout.adapter_main_sort_grid);
        ArrayAdapter<String> mAdapter2 = new ArrayAdapter<String>(this, R.layout.adapter_main_sort_grid);
        ArrayAdapter<String> mAdapter3 = new ArrayAdapter<String>(this, R.layout.adapter_main_sort_grid);
        mAdapter1.addAll(part1);
        mAdapter2.addAll(part2);
        mAdapter3.addAll(part3);

        gv_part1.setAdapter(mAdapter1);
        gv_part2.setAdapter(mAdapter2);
        gv_part3.setAdapter(mAdapter3);

        title.setText("分类");
    }

    @Override
    public void doInitListener() {

    }
}
