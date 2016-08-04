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
import android.widget.TextView;

public class DetailFragment extends Fragment {

    private final String TAG = "DetailFragment";

    private DetailListener listener;

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
        final View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String item = listener.getObject().getItem();
        int quantity = listener.getObject().getAmount();
        TextView textView = (TextView) getView().findViewById(R.id.item);
        textView.setText(item);
        textView = (TextView) getView().findViewById(R.id.quantity);
        textView.setText(String.valueOf(quantity));
    }
}