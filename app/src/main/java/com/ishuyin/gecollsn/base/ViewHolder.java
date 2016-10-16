package com.ishuyin.gecollsn.base;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class ViewHolder {
    private SparseArray<View> views;
    private View convertView;
    private int currentPoz;

    public ViewHolder(ViewGroup parent, int layoutId) {
        this.views = new SparseArray<View>();
        this.convertView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        this.convertView.setTag(this);
    }

    /**
     * 得到viewHolder
     *
     * @param parent
     * @param convertView
     * @param layoutId
     * @return
     */
    public static ViewHolder getViewHolder(ViewGroup parent, View convertView, int layoutId) {
        if (convertView == null) {
            return new ViewHolder(parent, layoutId);
        }
        return (ViewHolder) convertView.getTag();
    }

    /**
     * 得到convertView
     *
     * @return
     */
    public View getConvertView() {
        return convertView;
    }

    /**
     * 根据Id得到view
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 根据id得到TextView
     *
     * @return
     */
    public TextView getTextView(int viewId) {
        return getView(viewId);
    }

    /**
     * 根据id得到ImageView
     *
     * @return
     */
    public ImageView getImageView(int viewId) {
        return getView(viewId);
    }

    /**
     * 根据id得到Button
     *
     * @return
     */
    public Button getButton(int viewId) {
        return getView(viewId);
    }

    /**
     * 根据id得到RadioButton
     *
     * @return
     */
    public RadioButton getRadioButton(int viewId) {
        return getView(viewId);
    }

    /**
     * 根据id得到CheckBox
     *
     * @return
     */
    public CheckBox getCheckBox(int viewId) {
        return getView(viewId);
    }

    /**
     * 根据id得到ImageButton
     *
     * @return
     */
    public ImageButton getImageButton(int viewId) {
        return getView(viewId);
    }

    /**
     * 根据id得到ImageButton
     *
     * @return
     */
    public EditText getEditText(int viewId) {
        return getView(viewId);
    }

    /**
     * 根据id得到RelativeLayout
     *
     * @return
     */
    public RelativeLayout getRelativeLayout(int viewId) {
        return getView(viewId);
    }

    public int getCurrentPoz() {
        return currentPoz;
    }

    public void setCurrentPoz(int currentPoz) {
        this.currentPoz = currentPoz;
    }
}
