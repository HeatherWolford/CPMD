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
import android.widget.EditText;

public class DetailFragment extends Fragment {

    private final String TAG = "DetailFragment";

    private DetailListener listener;
    View rootView;

    public interface DetailListener{
        public Grocery getObject();
    }

    public DetailFragment() {
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        if(activity instanceof DetailListener){
            listener = (DetailListener) activity;
        }else {
            throw new IllegalArgumentException("Containing activity must implement DetailListener interface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String item = listener.getObject().getItem();
        int quantity = listener.getObject().getAmount();
        EditText itemEditText = (EditText) rootView.findViewById(R.id.item_name);
        itemEditText.setText(item);
        EditText qtyEditText = (EditText) rootView.findViewById(R.id.item_qty);
        qtyEditText.setText(String.valueOf(quantity));
    }
}