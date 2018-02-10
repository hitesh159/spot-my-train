package com.example.hitesh.spotmytrain;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hitesh on 2/8/2018.
 */

public class TrainInfoAdapter extends BaseAdapter {
    private Context mContext;
    List<TrainInfo> list;

    public TrainInfoAdapter(Context mContext, List<TrainInfo> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override

    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vi=View.inflate(mContext,R.layout.list_of_trains,null);
        TextView name=(TextView)vi.findViewById(R.id.trainname);
        TextView number=(TextView)vi.findViewById(R.id.trainnum);
        TextView support=(TextView)vi.findViewById(R.id.support);
        TextView arrival=(TextView)vi.findViewById(R.id.arrival);
        TextView departure=(TextView)vi.findViewById(R.id.departure);
        TextView source=(TextView)vi.findViewById(R.id.source);
        TextView destination=(TextView)vi.findViewById(R.id.destination);
        TextView runs=(TextView)vi.findViewById(R.id.runs);
        ImageView image=(ImageView)vi.findViewById(R.id.logo);
        image.setImageResource(R.drawable.logo);
        ShapeDrawable border = new ShapeDrawable(new RectShape());
        border.getPaint().setStyle(Paint.Style.STROKE);
        border.getPaint().setColor(Color.BLACK);
        image.setBackground(border);
        name.setText(list.get(i).getName());
        number.setText(list.get(i).getNumber());
        support.setText(list.get(i).getSupport());
        arrival.setText(list.get(i).getArrival());
        departure.setText(list.get(i).getDeparture());
        source.setText(list.get(i).getSource());
        destination.setText(list.get(i).getDestination());
        runs.setText(list.get(i).getRuns());
        return vi;
    }
}
