package com.example.owner.hanieum_project.Activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.example.owner.hanieum_project.R;

/**
 * Created by Owner on 2016-09-14.
 */
public class Device_settings_3 extends Fragment {

    EditText On_editText;
    EditText editText2;
    Button Back;
    Button Set;
    private settings mSend = new settings();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable  Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.layout_device_settings_frag3,container,false);

        final ToggleButton aHand_toggleB = (ToggleButton) rootView.findViewById(R.id.aHand_toggleButton);
        final ToggleButton Auto_toggleB = (ToggleButton) rootView.findViewById(R.id.Auto_toggleButton);
        Back = (Button) rootView.findViewById(R.id.Back_button);
        Set = (Button) rootView.findViewById(R.id.button_Set);
        On_editText = (EditText) rootView.findViewById(R.id.on_editText);
        editText2 = (EditText) rootView.findViewById(R.id.editText2);

        aHand_toggleB.setChecked(false);
        Auto_toggleB.setChecked(false);

        aHand_toggleB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override // 수동 토글버튼 이벤트 리스너
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(aHand_toggleB.isChecked()){
                    String order_on = "1012";
                    send_data(order_on);
                }
                else{
                    String order_off = "2101";
                    send_data(order_off);
                }
            }
        });

        Auto_toggleB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    On_editText.setEnabled(true);
                    On_editText.setClickable(true);
                    editText2.setEnabled(true);
                    editText2.setClickable(true);
                } else {
                    On_editText.setEnabled(false);
                    On_editText.setClickable(false);
                    editText2.setEnabled(false);
                    editText2.setClickable(false);
                }
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings changeFrag = (settings) getActivity();
                changeFrag.onFragmentChanged(0);
            }
        });

        Set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String degree_data_send;
                String degree_data_send2;
                if((On_editText.length() > 0)&&(editText2.length() > 0)) {
                    degree_data_send = On_editText.getText().toString() + 3;
                    send_data(String.valueOf(degree_data_send));

                    SystemClock.sleep(500);

                    degree_data_send2 = editText2.getText().toString() + 3;
                    send_data(String.valueOf(degree_data_send2));

                }
                else if(On_editText.length() == 0){
                    degree_data_send = " " + 3;
                    send_data(degree_data_send);

                    SystemClock.sleep(500);

                    degree_data_send2 = " " + 3;
                    send_data(String.valueOf(degree_data_send2));
                }
            }
        });
        return rootView;
    }

    public void send_data(String data) {
        Log.e("값 보내지니", String.valueOf(data));
        mSend.send_data(data);
    }
}
