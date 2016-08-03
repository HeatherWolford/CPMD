//Heather Wolford
//CPMD Week 1
//August 2016

package com.java2.heatherwolford.cpmdweek1;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

public class ListFragment extends Fragment {

    private final String TAG = "ListFragment";

    private CustomListener listener;

    public interface CustomListener{
        public void viewObject(int position) throws IOException;
        public ArrayList<Grocery> getListOfObjects() throws IOException;
    }

    public ListFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof CustomListener){
            listener = (CustomListener) activity;
        }else{
            throw new IllegalArgumentException("Application not connected to interface.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        return rootView;
    }

    //how we are going to interact with the view
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        System.out.println(TAG + "Entered the onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            System.out.println(TAG + "Entered the savedInstanceState != null");
        }
        ListView listView = (ListView) getView().findViewById(R.id.list);
        CustomAdapter customAdapter = null;
        try {
            customAdapter = new CustomAdapter(getActivity(), listener.getListOfObjects());
        } catch (IOException e) {
            e.printStackTrace();
        }
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    listener.viewObject(position);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println(TAG + "Leaving the onActivityCreated");
    }
}

