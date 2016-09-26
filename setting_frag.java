package com.example.owner.hanieum_project.Activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.owner.hanieum_project.R;
import com.example.owner.hanieum_project.data.ListData;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by user on 2016-09-16.
 */
public class setting_frag extends Fragment {
    private ListView mListView = null;
    private ListViewAdapter mAdapter = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.layout_settings_frag, container, false);

        mListView = (ListView) rootView.findViewById(R.id.mList);

        mAdapter = new ListViewAdapter(getActivity());
        mListView.setAdapter(mAdapter);

        mAdapter.addItem(getResources().getDrawable(R.drawable.blue_pan), "     선풍기");
        mAdapter.addItem(getResources().getDrawable(R.drawable.red_pan), "     온풍기");
        mAdapter.addItem(getResources().getDrawable(R.drawable.humidifier), "     가습기");
        mAdapter.addItem(getResources().getDrawable(R.drawable.dehumidifier), "     제습기");
        mAdapter.addItem(getResources().getDrawable(R.drawable.light), "     전구");
        mAdapter.addItem(getResources().getDrawable(R.drawable.window), "     창문");

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //ListData mData = mAdapter.mListData.get(position);
                //Toast.makeText(settings.this, mData.mTitle, Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0:
                        settings change1= (settings) getActivity();
                        change1.onFragmentChanged(1);
                        break;
                    case 1:
                        settings change2 = (settings) getActivity();
                        change2.onFragmentChanged(2);
                        break;
                    case 2:
                        settings change3 = (settings) getActivity();
                        change3.onFragmentChanged(3);
                        break;
                    case 3:
                        settings change4 = (settings) getActivity();
                        change4.onFragmentChanged(4);
                        break;
                    case 4:
                        settings change5 = (settings) getActivity();
                        change5.onFragmentChanged(5);
                        break;
                    case 5:
                        settings change6 = (settings) getActivity();
                        change6.onFragmentChanged(6);
                        break;
                }
            }
        });

        return rootView;
    }

    private class ViewHolder {
        public ImageView mIcon;
        public TextView mText;

    }

    private class ListViewAdapter extends BaseAdapter {
        private Context mContext = null;
        private ArrayList<ListData> mListData = new ArrayList<ListData>();

        public ListViewAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void addItem(Drawable icon, String mTitle){
            ListData addInfo = null;
            addInfo = new ListData();
            addInfo.mIcon = icon;
            addInfo.mTitle = mTitle;

            mListData.add(addInfo);
        }

        public void remove(int position){
            mListData.remove(position);
            dataChange();
        }

        public void sort(){
            Collections.sort(mListData, ListData.ALPHA_COMPARATOR);
            dataChange();
        }

        public void dataChange(){
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.listview_item, null);

                holder.mIcon = (ImageView) convertView.findViewById(R.id.mImage);
                holder.mText = (TextView) convertView.findViewById(R.id.mText);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            ListData mData = mListData.get(position);

            if (mData.mIcon != null) {
                holder.mIcon.setVisibility(View.VISIBLE);
                holder.mIcon.setImageDrawable(mData.mIcon);
            }else{
                holder.mIcon.setVisibility(View.GONE);
            }

            holder.mText.setText(mData.mTitle);

            return convertView;
        }
    }


}
