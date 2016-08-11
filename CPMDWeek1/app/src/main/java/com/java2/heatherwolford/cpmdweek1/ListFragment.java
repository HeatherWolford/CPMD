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
    private static ListView listView;

    public interface CustomListener{
        void viewItem(int position) throws IOException;
    }

    public static ListFragment newInstanceOf(ArrayList<Grocery> grocery ){
        ListFragment fragment = new ListFragment();

        Bundle args = new Bundle();
        String ARG = "ARG";
        args.putSerializable(ARG, grocery);
        fragment.setArguments(args);
        return  fragment;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_list, container, false);
        listView = (ListView) view.findViewById(R.id.list);
        return view;
    }

    //how we are going to interact with the view
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<Grocery> arrayList = FirebaseHelper.readFromFireBaseDatabase(getActivity().getApplicationContext());

        ListView strainListView = (ListView) getView().findViewById(R.id.list);
        CustomAdapter customAdapter = new CustomAdapter(getActivity(), arrayList);
        strainListView.setAdapter(customAdapter);

        strainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    listener.viewItem(position);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}