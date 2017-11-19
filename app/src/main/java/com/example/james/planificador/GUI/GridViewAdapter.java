package com.example.james.planificador.GUI;

/**
 * Created by James on 13/10/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.james.planificador.R;

import java.util.ArrayList;

/**
 * Created by James on 13/10/2017.
 */

public class GridViewAdapter extends BaseAdapter {
    private ArrayList<String> listCountry;
    private ArrayList<Integer> listFlag;
    private Activity activity;
    private DisplayMetrics metrics_;

    public GridViewAdapter(Activity activity, ArrayList<String> listCountry, ArrayList<Integer> listFlag, DisplayMetrics metrics) {
        super();
        this.listCountry = listCountry;
        this.listFlag = listFlag;
        this.activity = activity;
        this.metrics_ = metrics;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listCountry.size();
    }

    @Override
    public String getItem(int position) {
        // TODO Auto-generated method stub
        return listCountry.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public static class ViewHolder {
        public ImageView imgViewFlag;
        public TextView txtViewTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder view;
        LayoutInflater inflator = activity.getLayoutInflater();

        if (convertView == null) {
            view = new ViewHolder();
            convertView = inflator.inflate(R.layout.gridview_item, null);

            view.txtViewTitle = (TextView) convertView.findViewById(R.id.text);
            view.imgViewFlag = (ImageView) convertView.findViewById(R.id.image);

            convertView.setTag(view);
        } else {
            view = (ViewHolder) convertView.getTag();
        }
        Animation animation = null;
        //animation = new TranslateAnimation(metrics_.widthPixels / 2, 0, 0, 0);
        animation = AnimationUtils.loadAnimation(activity.getBaseContext(), R.anim.fab_slide_in_from_left);

        animation.setDuration(500);
        convertView.startAnimation(animation);

        view.txtViewTitle.setText(listCountry.get(position));
        view.imgViewFlag.setImageResource(listFlag.get(position));


        return convertView;
    }
}

