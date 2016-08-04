//Heather Wolford
//CPMD Week 1
//August 2016

package com.java2.heatherwolford.cpmdweek1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    public static final String TAG = "CustomAdapter: ";
    private  static final long ID_CONSTANT = 0x01000000;

    Context mContext;
    ArrayList<Grocery> mGroceries = new ArrayList<>();

    public CustomAdapter(Context context, ArrayList<Grocery> groceries){
        mContext = context;
        mGroceries = groceries;
    }

    @Override
    public int getCount() {
        mGroceries = FirebaseHelper.readFromFirebaseDatabase();
        return mGroceries.size();
    }

    @Override
    public Grocery getItem(int position) {
        return mGroceries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ID_CONSTANT + position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        }
        Grocery grocery = getItem(position);
        TextView itemView = (TextView) convertView.findViewById(R.id.item_name);
        itemView.setText(grocery.getItem());
        return convertView;
    }
}
