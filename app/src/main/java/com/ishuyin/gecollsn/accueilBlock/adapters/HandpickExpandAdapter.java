package com.ishuyin.gecollsn.accueilBlock.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.TextView;

import com.ishuyin.gecollsn.R;
import com.ishuyin.gecollsn.accueilBlock.AC;
import com.ishuyin.gecollsn.accueilBlock.activitys.HandpickMoreActivity;
import com.ishuyin.gecollsn.accueilBlock.domain.HandpickChild;
import com.ishuyin.gecollsn.accueilBlock.domain.HandpickGroup;
import com.ishuyin.gecollsn.base.EasyAdapter;
import com.ishuyin.gecollsn.base.ViewHolder;
import com.ishuyin.gecollsn.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gecollsn
 * @create 6/7/2016
 * @company www.ishuyin.com
 */
public class HandpickExpandAdapter extends BaseExpandableListAdapter {
    private final List<HandpickGroup> groupList = new ArrayList<>();
    private final List<HandpickChild> childList = new ArrayList<>();
    private ExpandableListView mEListView;

    public HandpickExpandAdapter(ExpandableListView listView) {
        this.mEListView = listView;
        this.mEListView.setOnGroupClickListener(null);
        this.mEListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                mEListView.expandGroup(groupPosition);
                return true;
            }
        });
    }

    public void setGroupData(List<HandpickGroup> data) {
        groupList.clear();
        groupList.addAll(data);
        notifyDataSetChanged();
    }

    public void setChildData(List<HandpickChild> data) {
        childList.clear();
        childList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new TextView(parent.getContext());
            ViewGroup.LayoutParams lp = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(55));
            convertView.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
            ((TextView) convertView).setGravity(Gravity.CENTER_VERTICAL);
            convertView.setPadding(DensityUtil.dp2px(13), 0, 0, 0);
            ((TextView) convertView).setCompoundDrawablePadding(DensityUtil.dp2px(13));
            ((TextView) convertView).setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            ((TextView) convertView).setTextColor(Color.parseColor("#AA000000"));
            convertView.setLayoutParams(lp);
        }

        final HandpickGroup groupItem = groupList.get(groupPosition);
        TextView tv = (TextView) convertView;
        tv.setText(groupItem.getTitle());
        //TODO Drawable from net
        tv.setCompoundDrawablesWithIntrinsicBounds(groupItem.getLogoId(), 0, 0, 0);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.size();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder vh = ViewHolder.getViewHolder(parent, convertView, R.layout.adapter_handpick_elv_child);
        if (convertView == null) {
            GridView gv_handpick_child = vh.getView(R.id.gv_handpick_child);
            EasyAdapter<HandpickChild> adapter = new EasyAdapter<HandpickChild>(R.layout.adapter_handpick_elv_child_grid) {
                public void convert(ViewHolder viewHolder, HandpickChild item) {

                }
            };
            gv_handpick_child.setAdapter(adapter);
            List<HandpickChild> list = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                list.add(new HandpickChild());
            }

            adapter.updateListView(list);

            vh.getView(R.id.tv_handpick_child_more).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String _TAG = AC.type.TAG_RECOMMEND;
                    switch (groupPosition) {
                        case 0: _TAG = AC.type.TAG_RECOMMEND; break;
                        case 1: _TAG = AC.type.TAG_HOT; break;
                        case 2: _TAG = AC.type.TAG_NEW; break;
                        case 3: _TAG = AC.type.TAG_FINISHED; break;
                    }
                    Intent intent = new Intent(mEListView.getContext(), HandpickMoreActivity.class);
                    intent.putExtra(AC.io.HANDPIC_MORE_TAG, _TAG);
                    mEListView.getContext().startActivity(intent);
                }
            });
        }

        return vh.getConvertView();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        for (int i = 0; i < groupList.size(); i++) {
            mEListView.expandGroup(i);
        }
    }
}
