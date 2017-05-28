package com.fjord.podrozni.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fjord.podrozni.EventAdapter;
import com.fjord.podrozni.Events;
import com.fjord.podrozni.Pop;
import com.fjord.podrozni.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;


/**
 * A simple {@link Fragment} subclass.
 */
public class Dzien3Fragment extends Fragment {

    String json_string;
    View view;

    public Dzien3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dzien3, container, false);


        ListView listView = (ListView) view.findViewById(R.id.listview3);
        final EventAdapter eventAdapter = new EventAdapter(getActivity(), R.layout.row_layout);
        listView.setAdapter(eventAdapter);
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(new File(getActivity().getCacheDir(), "json") + "cacheFile.srl")));
            json_string = (String) in.readObject();
            in.close();
            JSONObject jsonObject = new JSONObject(json_string);
            JSONArray jsonArray = jsonObject.getJSONArray("daythree");
            int count = 0;
            String title, image, info,info_short, start, end, map;
            while (count < jsonArray.length()) {
                JSONObject JO = jsonArray.getJSONObject(count);
                title = JO.getString("title");
                image = JO.getString("image");
                info = JO.getString("info");
                info_short = JO.getString("info_short");
                start = JO.getString("start");
                end = JO.getString("end");
                map = JO.getString("map");
                Events events = new Events(title,image,info,info_short, start, end, map);
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

                startActivity(intent);

            }
        } );
        return view;
    }
}
