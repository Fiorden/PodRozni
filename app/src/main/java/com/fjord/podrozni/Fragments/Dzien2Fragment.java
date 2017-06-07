package com.fjord.podrozni.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ActionMenuItemView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fjord.podrozni.DatabaseHandler;
import com.fjord.podrozni.EventAdapter;
import com.fjord.podrozni.Events;
import com.fjord.podrozni.Pop;
import com.fjord.podrozni.R;
import com.fjord.podrozni.Reminder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class Dzien2Fragment extends Fragment {

    // 03/06/2017
    long dayDate = 1488754800000l;

    String json_string;
    View view;
    EventAdapter eventAdapter;
    ListView listView;
    DatabaseHandler db;
    int currentPosition;


    public Dzien2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dzien2, container, false);


        listView = (ListView) view.findViewById(R.id.listview2);
        eventAdapter = new EventAdapter(getActivity(), R.layout.row_layout);
        listView.setAdapter(eventAdapter);
        db = new DatabaseHandler(getContext());
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(new File(getActivity().getCacheDir(), "json") + "cacheFile.srl")));
            json_string = (String) in.readObject();
            in.close();
            JSONObject jsonObject = new JSONObject(json_string);
            JSONArray jsonArray = jsonObject.getJSONArray("daytwo");
            int count = 0;
            String id, title, image, info,info_short, start, end, map;
            while (count < jsonArray.length()) {
                JSONObject JO = jsonArray.getJSONObject(count);
                id = JO.getString("id");
                title = JO.getString("title");
                image = JO.getString("image");
                info = JO.getString("info");
                info_short = JO.getString("info_short");
                start = JO.getString("start");
                end = JO.getString("end");
                map = JO.getString("map");
                Events events = new Events(id, title,image,info,info_short, start, end, map);
                for (Reminder r : db.getAllReminders()){
                    if (r.getId()==events.getId()){
                        events.setRemind(true);
                    }
                }
                eventAdapter.add(events);

                count++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(),Pop.class);
                intent.putExtra("Details",(Serializable) eventAdapter.getItem(position));
                currentPosition = position;

                startActivityForResult(intent,2);

            }
        } );

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_CANCELED){
        if(requestCode==2)
        {
            Events event = (Events) eventAdapter.getItem(currentPosition);
            int time = Integer.parseInt(data.getStringExtra("TIME"));

            if (time!=0) {
                event.setRemind(true);
                String[] timeString = event.getStart().split(":");
                int hour = Integer.parseInt ( timeString[0].trim() );
                int min = Integer.parseInt ( timeString[1].trim() );
                long timeMilis = hour*1000*60*60+min*1000*60-time;
                long eventTime = dayDate + timeMilis;
                Reminder reminder = new Reminder(event.getId(), eventTime);
                db.addReminder(reminder);
            }
            else {
                event.setRemind(false);
                Reminder reminder = new Reminder(event.getId(), 0);
                db.delReminder(reminder);
            }

            eventAdapter.notifyDataSetChanged();
        }
    }}

}
