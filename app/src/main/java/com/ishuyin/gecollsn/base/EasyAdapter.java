package com.ishuyin.gecollsn.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 基础适配器
 *
 * @param <T>
 * @author soyoungboy
 */
public abstract class EasyAdapter<T> extends BaseAdapter {
    private int layoutId;
    private List<T> mData = new ArrayList<>();

    public EasyAdapter(int layoutId) {
        super();
        this.layoutId = layoutId;
    }

    /**
     * 往顶部添加数据
     *
     * @param list
     */
    public void add2Head(List<T> list) {
        mData.addAll(0, list);
        notifyDataSetChanged();
    }

    public void clearAll() {
        mData.clear();
        notifyDataSetChanged();
    }

    public List<T> getAllList() {
        return Collections.unmodifiableList(mData);
    }

    public void removeData(int poz){
        if (poz >= getCount()) return;
        mData.remove(poz);
        notifyDataSetChanged();
    }

    /**
     * 往底部添加数据
     *
     * @param list
     */
    public void add2Bottom(List<T> list) {
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public void add2Bottom(T t) {
        mData.add(t);
        notifyDataSetChanged();
    }

    /**
     * @param list
     */
    public void updateListView(List<T> list) {
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    /**
     * 实际显示View的方法，使用抽象方法强制调用者覆写！
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = ViewHolder.getViewHolder(parent, convertView, layoutId);
        vh.setCurrentPoz(position);
        if (convertView == null) initConvertView(vh);
        convert(vh, mData.get(position));
        return vh.getConvertView();

    }

    protected void initConvertView(ViewHolder vh) {
    }

    protected abstract void convert(ViewHolder vh, T t);

}
