package com.example.hitesh.spotmytrain;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hitesh on 2/2/2018.
 */

public class OptionsAdapter extends BaseAdapter {

    private Context mContext;
    private List<Options> mOptions;

    public OptionsAdapter(Context mContext, List<Options> mOptions) {
        this.mContext = mContext;
        this.mOptions = mOptions;
    }

    @Override

    public int getCount() {
        return mOptions.size();
    }

    @Override
    public Object getItem(int i) {
        return mOptions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v=View.inflate(mContext,R.layout.main_list,null);
        TextView textView=(TextView)v.findViewById(R.id.textView);
        ImageView imageView=(ImageView) v.findViewById(R.id.imageView);
        textView.setText(mOptions.get(i).getDescription());
        imageView.setImageResource(mOptions.get(i).getImage());
        return v;
    }
}
