package com.example.amigo;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class Frag1Events extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState){
        View rootView=inflater.inflate(R.layout.tab1events,container,false);
        String[] events = {};
        ListView eventsList = (ListView) rootView.findViewById(R.id.events_list);
        CustomAdapter customAdapter = new CustomAdapter(getActivity(),events);
        eventsList.setAdapter(customAdapter);

        return rootView;

    }
}
