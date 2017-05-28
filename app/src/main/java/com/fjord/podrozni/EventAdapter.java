package com.fjord.podrozni;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by fjord on 27.03.2017.
 */

public class EventAdapter extends ArrayAdapter {
    List list = new ArrayList<>();
    public EventAdapter(@NonNull FragmentActivity context, @LayoutRes int resource) {
        super(context, resource);
    }



    public void add(Events object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row;
        row = convertView;
        EventHolder eventHolder;
        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout,parent,false);
            eventHolder = new EventHolder();
            eventHolder.tx_title = (TextView)row.findViewById(R.id.tx_Title);
            eventHolder.reminder = (ImageView)row.findViewById(R.id.reminder);
            eventHolder.tx_start = (TextView)row.findViewById(R.id.tx_start);
            eventHolder.tx_end = (TextView)row.findViewById(R.id.tx_end);
            row.setTag(eventHolder);
        }
        else{
            eventHolder = (EventHolder)row.getTag();
        }

        Events events = (Events) this.getItem(position);
        eventHolder.tx_title.setText(events.getTitle());
        eventHolder.tx_start.setText(events.getStart());
        eventHolder.tx_end.setText(events.getEnd());
        if(events.getRemind())
        eventHolder.reminder.setVisibility(View.VISIBLE);
        else
            eventHolder.reminder.setVisibility(View.INVISIBLE);


        return row;
    }

    static class EventHolder{

        TextView tx_title, tx_start, tx_end;
        ImageView reminder;
    }
}
